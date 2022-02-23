package com.tora.service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.tora.message.Message;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class PeerService {

    private final static String EVERYONE_STACK = "everyone";
    private final static String EXCHANGE_NAME = "tora";
    private final static String URI = "---";
    private final Set<String> connectedUsers = Collections.newSetFromMap(new ConcurrentHashMap<>());
    private final Set<String> requestedUsers = Collections.newSetFromMap(new ConcurrentHashMap<>());
    private final Set<String> receivedRequestUsers = Collections.newSetFromMap(new ConcurrentHashMap<>());
    private final Set<String> groupsConnected = Collections.newSetFromMap(new ConcurrentHashMap<>());
    private final Set<String> groupsInvited = Collections.newSetFromMap(new ConcurrentHashMap<>());

    private final Channel channel;
    private final ExecutorService executorService;
    ConnectionFactory factory;

    @SneakyThrows
    @Autowired
    private PeerService(ConnectionFactory factory) {
        this.factory = factory;
        executorService = Executors.newCachedThreadPool();
        factory.setUri(URI);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        connectedUsers.add(EVERYONE_STACK);
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        this.channel = channel;
    }

    public void sendMessage(final Message message) {
        if (message.getType().equals(Message.Type.MESSAGE)) {
            executorService.submit(() -> this.sendMessageInternal(message, message.getTo() + "-chat"));
        } else {
            executorService.submit(() -> this.sendMessageInternal(message, message.getTo() + "-util"));
        }

    }

    public void recvMessage(final String userName) {
        executorService.submit(() -> this.recvMessageInternal(userName));
        executorService.submit(() -> this.recvMessageInternal(EVERYONE_STACK));
        executorService.submit(() -> this.recvMessageUtil(userName + "-util"));
    }

    @SneakyThrows
    private synchronized void sendMessageInternal(final Message message, String queueName) {
        channel.basicPublish(EXCHANGE_NAME, queueName, null, message.toJson().getBytes(StandardCharsets.UTF_8));
        System.out.println(" [x] Sent to '" + queueName + "':'" + message + "'");
    }

    @SneakyThrows
    private synchronized void recvMessageInternal(final String userName) {
        final String queueName = channel.queueDeclare().getQueue();

        channel.queueBind(queueName, EXCHANGE_NAME, userName + "-chat");

        final DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            final String response = new String(delivery.getBody(), StandardCharsets.UTF_8);
            final Message message = Message.fromJson(response);
            //if connected
            if (connectedUsers.contains(message.getFrom()) || groupsConnected.contains(message.getTo())) {
                System.out.println(message.getFrom() + " says: " + message.getContent());
            }
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }

    @SneakyThrows
    private synchronized void recvMessageUtil(String myUser) {
        final String queueName = channel.queueDeclare().getQueue();

        channel.queueBind(queueName, EXCHANGE_NAME, myUser);

        final DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            final String response = new String(delivery.getBody(), StandardCharsets.UTF_8);
            final Message message = Message.fromJson(response);
            handleReceivedUtilMessage(message);
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }

    private void handleReceivedUtilMessage(Message utilMessage) {
        switch (utilMessage.getType()) {
            case ACK:
                handleReceivedAck(utilMessage.getFrom());
                break;
            case REQUEST:
                handleReceivedRequest(utilMessage.getFrom());
                break;
            case GROUP_REQUEST:
                handleReceivedGroupRequest(utilMessage.getFrom());
                break;
            case BYE:
                handleReceivedBye(utilMessage.getFrom());
                break;
        }
    }

    private void handleReceivedGroupRequest(String from) {
        groupsInvited.add(from);
        System.out.println("Received a group request: " + from);
    }

    private void handleReceivedBye(String from) {
        connectedUsers.remove(from);
        requestedUsers.remove(from);
        receivedRequestUsers.remove(from);
    }

    private void handleReceivedRequest(String from) {
        //if connected or already requested, ignore request
        if (!(connectedUsers.contains(from) || receivedRequestUsers.contains(from))) {
            receivedRequestUsers.add(from);
            System.out.println(from + " asked you to be friends!");
        }

    }

    private void handleReceivedAck(String from) {
        if (requestedUsers.remove(from)) {
            connectedUsers.add(from);
            receivedRequestUsers.remove(from);
            System.out.println(from + " accepted your friend request");
        }
    }

    public boolean handleSendAck(String toUser, String myUser) {
        if (receivedRequestUsers.remove(toUser)) {
            connectedUsers.add(toUser);
            Message utilMessage = Message.builder()
                    .from(myUser)
                    .to(toUser)
                    .type(Message.Type.ACK)
                    .build();
            sendMessage(utilMessage);
            return true;
        }

        return false;
    }

    public void handleSendRequest(String toUser, String myUser) {
        //if not requested, or connected
        if (!(requestedUsers.contains(toUser) || connectedUsers.contains(toUser))) {
            requestedUsers.add(toUser);
            Message utilMessage = Message.builder()
                    .from(myUser)
                    .to(toUser)
                    .type(Message.Type.REQUEST)
                    .build();
            sendMessage(utilMessage);
        }
    }

    public void handleBye(String myUser) {
        for (String toUser : connectedUsers) {
            Message utilMessage = Message.builder()
                    .from(myUser)
                    .to(toUser)
                    .type(Message.Type.BYE)
                    .build();
            sendMessage(utilMessage);
        }
    }

    public boolean handleSendMessage(String myUsername, String userToSend, String content) {
        if (connectedUsers.contains(userToSend) || groupsConnected.contains(userToSend)) {
            final Message message = Message.builder()
                    .from(myUsername)
                    .to(userToSend)
                    .type(Message.Type.MESSAGE)
                    .content(content).build();

            sendMessage(message);
            return true;
        }
        return false;
    }

    public void handleSendGroupAck(String groupID) {
        if (groupsConnected.contains(groupID)) {
            return;
        }

        if (groupsInvited.remove(groupID)) {
            groupsConnected.add(groupID);
            executorService.submit(() -> this.recvMessageInternal(groupID));
        }
    }

    public void handleSendGroupRequest(String groupID, String user) {
        if (!groupsConnected.contains(groupID)) {
            createGroup(groupID);
        }

        final Message message = Message.builder()
                .from(groupID)
                .to(user)
                .type(Message.Type.GROUP_REQUEST)
                .build();

        sendMessage(message);
    }

    private void createGroup(String groupID) {
        groupsConnected.add(groupID);
        executorService.submit(() -> this.recvMessageInternal(groupID));
    }
}


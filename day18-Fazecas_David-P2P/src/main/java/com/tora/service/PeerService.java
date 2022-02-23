package com.tora.service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.tora.model.Message;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class PeerService {
    private static final String EXCHANGE_NAME = "tora";
    private static final String SUFFIX_CHAT = "-chat";
    private static final String SUFFIX_UTIL = "-util";
    private final Channel channel;
    private final ExecutorService executorService;
    @Getter
    private final Set<String> allowedUsers = Collections.newSetFromMap(new ConcurrentHashMap<>());

    @Autowired
    public PeerService(final ConnectionFactory connectionFactory) {
        executorService = Executors.newFixedThreadPool(5);
        this.channel = connectionFactory.getConnectedChannel(EXCHANGE_NAME);
    }

    private static String getTypeForQueue(Message.Type type) {
        if (type.equals(Message.Type.MESSAGE)) {
            return SUFFIX_CHAT;
        } else {
            return SUFFIX_UTIL;
        }
    }

    public void startReceivers(final String userName) {
        executorService.submit(() -> this.recvMessageInternal(userName));
        executorService.submit(() -> this.recvMessageUtilInternal(userName));
    }

    public void sendMessage(final Message message) {
        if (allowedUsers.contains(message.getTo())) {
            executorService.submit(() -> this.sendMessageInternal(message));
        } else {
            System.out.println(message.getTo() + " is not in the friend list");
        }
    }

    public void handleByebye(Message message) {
        allowedUsers.forEach(user -> {
            message.setTo(user);
            executorService.submit(() -> this.sendMessageInternal(message));
        });
    }

    public void createGroup(final String groupName, final List<String> members) {
        members.forEach(member -> {
            Message message = Message.builder().to(member).from(groupName).type(Message.Type.GROUP_REQUEST).build();
            sendMessageUtil(message);
        });

    }

    public void sendMessageUtil(Message message) {
        if (message.getType().equals(Message.Type.ACK)) {
            allowedUsers.add(message.getTo());
        }
        if (message.getType().equals(Message.Type.BYE)) {
            allowedUsers.remove(message.getTo());
        }
        if (message.getType().equals(Message.Type.GROUP_ACK)) {
            allowedUsers.add(message.getTo());
            executorService.submit(() -> this.recvMessageInternal(message.getTo()));
        }
        executorService.submit(() -> this.sendMessageInternal(message));
    }

    @SneakyThrows
    private synchronized void sendMessageInternal(final Message message) {
        String queueName = message.getTo() + getTypeForQueue(message.getType());
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        channel.basicPublish(EXCHANGE_NAME, queueName, null, message.toJson().getBytes(StandardCharsets.UTF_8));
        // todo send status
        System.out.println(" [x] Sent to '" + queueName + "':'" + message + "'");
    }

    @SneakyThrows
    private synchronized void recvMessageInternal(final String userName) {
        final String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, userName + "-chat");

        final DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            final String response = new String(delivery.getBody(), StandardCharsets.UTF_8);
            final Message message = Message.fromJson(response);
            if (allowedUsers.contains(message.getFrom())) {
                System.out.println(" [x] Received '" +
                    delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
            } else if (allowedUsers.contains(message.getTo())) {
                System.out.println(" [x] Received from group " + message.getTo() + "':'" + message + "'");
            } else {
                System.out.println(message.getFrom() + " has tried to send a message, but is not allowed");
            }
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }

    @SneakyThrows
    private synchronized void recvMessageUtilInternal(final String userName) {
        final String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, userName + "-util");

        final DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            final String response = new String(delivery.getBody(), StandardCharsets.UTF_8);
            final Message message = Message.fromJson(response);
            if (message.getType().equals(Message.Type.REQUEST)) {
                System.out.println("You have a request from " + message.getFrom());
            }
            if (message.getType().equals(Message.Type.ACK)) {
                System.out.println(message.getFrom() + " has accepted your request");
                allowedUsers.add(message.getFrom());
            }
            if (message.getType().equals(Message.Type.BYE)) {
                System.out.println(message.getFrom() + " has closed the connection with you");
                allowedUsers.remove(message.getFrom());
            }
            if (message.getType().equals(Message.Type.GROUP_REQUEST)) {
                System.out.println("You have a request from a group " + message.getFrom());
//                allowedUsers.add(message.getFrom());
            }
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }
}


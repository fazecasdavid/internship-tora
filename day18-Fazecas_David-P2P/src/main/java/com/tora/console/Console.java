package com.tora.console;

import com.tora.model.Message;
import com.tora.service.PeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@ComponentScan("com.tora")
@Component
public class Console implements CommandLineRunner {
    private final Scanner scanner = new Scanner(System.in);
    private final PeerService peerService;
    private String userName;
    private Map<String, Runnable> commandsMap;

    @Autowired
    public Console(PeerService peerService) {
        this.peerService = peerService;
        initCommandsMap();
    }

    @Override
    public void run(String... args) {
        System.out.print("Enter your username: ");
        userName = scanner.nextLine();
        printMenu();
        peerService.startReceivers(userName);
        while (true) {
            String command = scanner.nextLine();
            if (commandsMap.containsKey(command)) {
                try {
                    commandsMap.get(command).run();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            } else {
                System.out.println("Wrong input");
            }
        }
    }

    private void initCommandsMap() {
        commandsMap = new HashMap<>();
        commandsMap.put("!hello", this::handleRequest);
        commandsMap.put("!send", this::handleSend);
        commandsMap.put("!byebye", this::handleByebye);
        commandsMap.put("!ack", this::handleAck);
        commandsMap.put("!ls", this::handleLs);
        commandsMap.put("!bye", this::handleBye);
        commandsMap.put("!group", this::handleGroup);
        commandsMap.put("!ack-g", this::handleGroupAck);
    }

    private void handleGroupAck() {
        System.out.print("Request group ack: ");
        String groupToAck = scanner.nextLine();
        Message message = Message.builder()
            .from(userName)
            .to(groupToAck)
            .type(Message.Type.GROUP_ACK).build();

        peerService.sendMessageUtil(message);
    }

    private void handleBye() {
        System.out.print("Request username to unfriend: ");
        String userToSend = scanner.nextLine();
        Message message = Message.builder()
            .from(userName)
            .to(userToSend)
            .type(Message.Type.BYE).build();

        peerService.sendMessageUtil(message);
    }

    private void handleLs() {
        System.out.println(peerService.getAllowedUsers());
    }


    private void handleGroup() {
        System.out.print("Enter group name: ");
        String groupName = scanner.nextLine();
        System.out.print("Enter members [ex: name1,name2]: ");
        List<String> members = Arrays.asList(scanner.nextLine().split(","));

        peerService.createGroup(groupName, members);
    }

    private void handleSend() {
        System.out.print("Enter user: ");
        String userToSend = scanner.nextLine();
        System.out.print("Enter content: ");
        String content = scanner.nextLine();

        final Message message = Message.builder()
            .from(userName)
            .to(userToSend)
            .type(Message.Type.MESSAGE)
            .content(content).build();

        peerService.sendMessage(message);
    }

    private void handleRequest() {
        System.out.print("Request username: ");
        String userToSend = scanner.nextLine();
        Message message = Message.builder()
            .from(userName)
            .to(userToSend)
            .type(Message.Type.REQUEST).build();

        peerService.sendMessageUtil(message);
    }

    private void handleAck() {
        System.out.print("Request username for ack: ");
        String userToAck = scanner.nextLine();
        Message message = Message.builder()
            .from(userName)
            .to(userToAck)
            .type(Message.Type.ACK).build();

        peerService.sendMessageUtil(message);
    }

    private void handleByebye() {
        Message message = Message.builder()
            .from(userName)
            .type(Message.Type.BYE).build();

        peerService.handleByebye(message);
        System.exit(0);
    }

    private void printMenu() {
        System.out.println("Options:");
        System.out.println(commandsMap.keySet());
    }
}

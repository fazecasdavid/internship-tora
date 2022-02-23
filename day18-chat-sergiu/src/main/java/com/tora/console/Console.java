package com.tora.console;

import com.tora.message.Message;
import com.tora.service.PeerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;
import java.util.Scanner;

@ComponentScan("com.tora")
public class Console implements CommandLineRunner {
    private final Scanner scanner = new Scanner(System.in);
    private final PeerService peerService;

    public Console(PeerService peerService) {
        this.peerService = peerService;
    }

    @Override
    public void run(String... args) {
        System.out.print("Enter your username: ");
        String userName = scanner.nextLine();
        printMenu();
        peerService.recvMessage(userName);
        while (true) {
            String command = scanner.nextLine();
            Message.Type type;
            try {
                type = Message.Type.valueOf(command);
            } catch (IllegalArgumentException e) {
                System.out.println("Unknown command");
                continue;
            }
            switch (type) {
                case ACK:
                    handleInputAck(userName);
                    break;
                case GROUP_ACK:
                    handleInputGroupAck();
                    break;
                case REQUEST:
                    handleInputRequest(userName);
                    break;
                case GROUP_REQUEST:
                    handleInputGroupRequest();
                    break;
                case MESSAGE:
                    handleInputMessage(userName);
                    break;
                case BYE:
                    peerService.handleBye(userName);
                    System.exit(0);
                    break;
                default:
                    System.out.println("Unknown command");
            }
        }
    }

    private void handleInputMessage(String userName) {
        System.out.print("Enter user: ");
        String toUser = scanner.nextLine();
        System.out.print("Enter content: ");
        String content = scanner.nextLine();
        if (peerService.handleSendMessage(userName, toUser, content)) {
            System.out.println("message sent");
        } else {
            System.out.println("you're not connected to this user!");
        }
    }

    private void handleInputGroupRequest() {
        System.out.println("Input group id:");
        String groupID = scanner.nextLine();
        System.out.println("Input user:");
        String user = scanner.nextLine();
        peerService.handleSendGroupRequest(groupID, user);
    }

    private void handleInputRequest(String userName) {
        System.out.println("input user send request:");
        peerService.handleSendRequest(scanner.nextLine(), userName);
    }

    private void handleInputGroupAck() {
        System.out.println("input group to ack response:");
        peerService.handleSendGroupAck(scanner.nextLine());
    }

    private void handleInputAck(String userName) {
        System.out.println("input user to ack response:");
        String userTo = scanner.nextLine();

        if (peerService.handleSendAck(userTo, userName)) {
            System.out.println("Connected to " + userTo);
        } else {
            System.out.println("Connection failed");
        }
    }


    private void printMenu() {
        System.out.println(Arrays.asList(Message.Type.values()));
    }

}

package com.tora.console;

import com.tora.domain.Order;
import com.tora.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@ComponentScan("com.tora")
@Component
public class Console implements CommandLineRunner {

    private final OrderService orderService;

    private final Scanner scanner;
    private Map<String, Runnable> commandsMap;

    @Autowired
    public Console(OrderService orderService) {
        this.scanner = new Scanner(System.in);
        this.orderService = orderService;
        initCommandsMap();
    }

    private void initCommandsMap() {
        commandsMap = new HashMap<>();
        commandsMap.put("add", this::handleAddOrder);
        commandsMap.put("delete", this::handleDeleteOrder);
        commandsMap.put("print", this::handlePrintAllOrders);
    }

    private void handleAddOrder() {
        System.out.print("id: ");
        long id = scanner.nextLong();
        System.out.print("price:");
        int price = scanner.nextInt();
        System.out.print("quantity:");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        Order order = Order.builder().id(id).price(price).quantity(quantity).build();
        orderService.addOrder(order);
    }

    private void handleDeleteOrder() {
        System.out.print("id: ");
        long id = scanner.nextLong();
        scanner.nextLine();

        orderService.deleteOrderById(id);
    }

    private void handlePrintAllOrders() {
        orderService.findAllOrders().forEach(System.out::println);
    }

    @Override
    public void run(String... args) {
        boolean running = true;
        while (running) {
            printMenu();
            System.out.print("Input the option: ");

            String option = scanner.nextLine();

            if (option.equals("exit")) {
                running = false;
            } else if (commandsMap.containsKey(option)) {
                try {
                    commandsMap.get(option).run();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            } else {
                System.out.println("Invalid option!");
            }
        }
    }

    private void printMenu() {
        System.out.println("Options:");
        System.out.println(commandsMap.keySet());
        System.out.println("\t\t-\texit");
    }
}

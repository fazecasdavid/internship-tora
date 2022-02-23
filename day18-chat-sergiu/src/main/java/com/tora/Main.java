package com.tora;

import com.tora.console.Console;
import com.tora.httpRequests.postRequests.HandleRequests;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
public class Main {


    public static void main(String[] args) {
        SpringApplication.run(Console.class, args);
        printServerStartAddress();
    }

    private static void printServerStartAddress() {
        InetAddress ip = null;
        try {
            ip = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        System.out.println("----------------------------\nCurrent IP address : " + ip.getHostAddress() + "\n----------------------------");
    }
}

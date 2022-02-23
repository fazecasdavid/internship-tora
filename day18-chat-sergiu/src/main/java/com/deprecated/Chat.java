package com.deprecated;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

public class Chat {

    public static final String QUEUE_NAME = "hello";
    public static final String uri = "---";

    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(5);
        pool.submit(sent);
        pool.submit(recv);
    }


    static Runnable sent = new Runnable() {
        @Override
        public void run() {

            ConnectionFactory factory = new ConnectionFactory();
            try {
                factory.setUri(uri);
            } catch (URISyntaxException | NoSuchAlgorithmException | KeyManagementException e) {
                e.printStackTrace();
            }
            try (Connection connection = factory.newConnection();
                 Channel channel = connection.createChannel()) {
                channel.queueDeclare(QUEUE_NAME, false, false, false, null);
                String message = "Hello World!";
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
                System.out.println(" [x] Sent '" + message + "'");
            } catch (IOException | TimeoutException e) {
                e.printStackTrace();
            }
        }
    };
    static Runnable recv = new Runnable() {
        @Override
        public void run() {
            ConnectionFactory factory = new ConnectionFactory();
            try {
                factory.setUri(uri);
            } catch (URISyntaxException | NoSuchAlgorithmException | KeyManagementException e) {
                e.printStackTrace();
            }
            try (
                    Connection connection = factory.newConnection();
                    Channel channel = connection.createChannel();) {

                channel.queueDeclare(QUEUE_NAME, false, false, false, null);
                System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

                DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                    String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                    System.out.println(" [x] Received '" + message + "'");
                };
                channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
                });
            } catch (IOException | TimeoutException e) {
                e.printStackTrace();
            }
        }
    };

}

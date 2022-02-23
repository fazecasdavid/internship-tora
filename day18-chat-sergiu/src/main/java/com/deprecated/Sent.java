package com.deprecated;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

import static com.deprecated.ChatExchange.EXCHANGE_NAME;
import static com.deprecated.ChatExchange.MY_ROUTING_KEY;
import static com.deprecated.ChatExchange.rabbit_uri;

public class Sent extends Thread {

    @Override
    public void run() {
        super.run();
        ConnectionFactory factory = new ConnectionFactory();
        try {
            factory.setUri(rabbit_uri);
        } catch (URISyntaxException | NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");

//                channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = String.format("Hello World Sergiu!  Exchange: %s bind: %s", EXCHANGE_NAME, MY_ROUTING_KEY);
            channel.basicPublish(EXCHANGE_NAME, MY_ROUTING_KEY, null, message.getBytes(StandardCharsets.UTF_8));

            System.out.println(" [x] Sent '" + message + "'");
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }

    }
}

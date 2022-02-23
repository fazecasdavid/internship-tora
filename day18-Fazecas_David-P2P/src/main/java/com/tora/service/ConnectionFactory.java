package com.tora.service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
public class ConnectionFactory {
    private static final String URI = "---";
    @SneakyThrows
    public Channel getConnectedChannel(final String EXCHANGE_NAME) {
        com.rabbitmq.client.ConnectionFactory factory = new com.rabbitmq.client.ConnectionFactory();
        factory.setUri(URI);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        return channel;
    }
}

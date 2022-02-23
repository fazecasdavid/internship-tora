package com.tora.configuration;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConnectionFactoryConfiguration {

    @Bean
    public ConnectionFactory connectionFactory() {
        return new ConnectionFactory();
    }
}

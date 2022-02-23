package com.deprecated;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatExchange {

    public static final String rabbit_uri = "---";
    public static final String EXCHANGE_NAME = "tora";
    public static final String MY_ROUTING_KEY = "sergiu";

    private static final String[] keys = {"bursuc", "dinu", "david", "marius", "mihai", "eduard", "stefa", "sergiu"};

    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(5);
        pool.submit(new Sent());
        pool.submit(new Recv());
    }



//    static Runnable sent = new Runnable() {
//        @Override
//        public void run() {
//
//            ConnectionFactory factory = new ConnectionFactory();
//            try {
//                factory.setUri(rabbit_uri);
//            } catch (URISyntaxException | NoSuchAlgorithmException | KeyManagementException e) {
//                e.printStackTrace();
//            }
//            try (Connection connection = factory.newConnection();
//                 Channel channel = connection.createChannel()) {
//                channel.exchangeDeclare(EXCHANGE_NAME, "direct");
//
////                channel.queueDeclare(QUEUE_NAME, false, false, false, null);
//                String message = String.format("Hello World Sergiu!  Exchange: %s bind: %s", EXCHANGE_NAME, MY_ROUTING_KEY);
//                channel.basicPublish(EXCHANGE_NAME, MY_ROUTING_KEY, null, message.getBytes(StandardCharsets.UTF_8));
//
//                System.out.println(" [x] Sent '" + message + "'");
//            } catch (IOException | TimeoutException e) {
//                e.printStackTrace();
//            }
//        }
//    };
//
//
//    static Runnable recv = new Runnable() {
//        @Override
//        public void run() {
//            ConnectionFactory factory = new ConnectionFactory();
//            factory.setHost("localhost");
//            try (
//                    Connection connection = factory.newConnection();
//                    Channel channel = connection.createChannel();) {
//
//                channel.exchangeDeclare(EXCHANGE_NAME, "direct");
//                String queueName = channel.queueDeclare().getQueue();
//
//                channel.queueBind(queueName, EXCHANGE_NAME, MY_ROUTING_KEY);
//
//                System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
//
//                DeliverCallback deliverCallback = (consumerTag, delivery) -> {
//                    String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
//                    System.out.println(" [x] Received '" +
//                            delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
//                };
//                channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
//                });
//            } catch (IOException | TimeoutException e) {
//                e.printStackTrace();
//            }
//        }
//    };
}

package com.tora.controller;

import com.tora.service.PeerService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/ws/friends")
@Component
public class WsServerFriends {
    private static Session session;

    @SneakyThrows
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        System.out.println("Successful connection");
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("Connection closure");
    }

    @SneakyThrows
    @OnMessage
    public void onMsg(String text) throws IOException {
        System.out.println("Received: " + text);
    }

    @SneakyThrows
    public static void sendMsg(String text) {
        session.getBasicRemote().sendText(text);
    }
}

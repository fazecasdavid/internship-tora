package com.tora.webSocket;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@Component
@ServerEndpoint("/messages/util")
public class WebSocketMessengerUtil {
    private static Session session;

    @OnOpen
    public void onOpen(Session session) {
        WebSocketMessengerUtil.session = session;
        System.out.println("Successful connection");
    }

    @OnClose
    public void onClose() {
        System.out.println("Connection closed");
    }

    @OnMessage
    public void onMsgChat(String text) {
        System.out.println("Received: " + text);
    }

    @SneakyThrows
    public static void sendMsg(String text) {
        session.getBasicRemote().sendText(text);
    }
}

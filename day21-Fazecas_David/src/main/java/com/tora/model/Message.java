package com.tora.model;

import com.google.gson.Gson;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Message {
    private final Type type;
    private final String from;
    private final String content;
    private String to;

    private static final Gson gson = new Gson();

    public static Message fromJson(String json) {
        return gson.fromJson(json, Message.class);
    }

    public String toJson() {
        return gson.toJson(this);
    }

    public enum Type {
        MESSAGE,
        REQUEST,
        ACK,
        GROUP_REQUEST,
        GROUP_ACK,
        BYE
    }
}

package com.example.whatsdown;

public class LastMessage {
    String id;
    String created;
    String content;

    public LastMessage(String id, String created, String content) {
        this.id = id;
        this.created = created;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public String getCreated() {
        return created;
    }

    public String getContent() {
        return content;
    }
}

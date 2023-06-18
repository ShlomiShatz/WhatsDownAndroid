package com.example.whatsdown;

public class Message {

    String id;
    CurrentUser sender;
    String content;
    String created;
    public Message(String id, CurrentUser sender, String content, String created) {
        this.id = id;
        this.sender = sender;
        this.content = content;
        this.created = created;
    }
    public CurrentUser getSender() { return sender; }
    public String getContent() { return content; }
    public String getCreated() { return created; }
}

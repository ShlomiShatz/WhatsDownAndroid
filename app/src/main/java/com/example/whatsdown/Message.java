package com.example.whatsdown;

public class Message {

    String id;
    String sender;
    String content;
    String created;
    public Message(String sender, String content, String created) {
        this.sender = sender;
        this.content = content;
        this.created = created;
    }
    public String getSender() { return sender; }
    public String getContent() { return content; }
    public String getCreated() { return created; }
}

package com.example.whatsdown;

public class Message {

    String sender;
    String content;
    String time;
    public Message(String sender, String content, String time) {
        this.sender = sender;
        this.content = content;
        this.time = time;
    }
    public String getSender() { return sender; }
    public String getContent() { return content; }
    public String getTime() { return time; }
}

package com.example.whatsdown.objects;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

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
    public String getCreated() {
        String outputFormat = "yyyy-MM-dd HH:mm:ss";
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            Date date = inputFormat.parse(created);
            SimpleDateFormat outputFormatter = new SimpleDateFormat(outputFormat);
            return outputFormatter.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return created;
    }
}

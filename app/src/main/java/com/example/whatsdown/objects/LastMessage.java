package com.example.whatsdown.objects;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    public String getContent() {
        return content;
    }
}

package com.example.whatsdown.objects;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.whatsdown.Dao.Converters;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@TypeConverters(Converters.class)
public class Message {
    @PrimaryKey()
    @NonNull
    private ChatOfUser messageOfUser;
    private String id;
    private String chatId;
    private CurrentUser sender;
    private String content;
    private String created;
    public Message(String id, CurrentUser sender, String content, String created, String chatId) {
        this.id = id;
        this.sender = sender;
        this.content = content;
        this.created = created;
        this.chatId = chatId;
    }

    @NonNull
    public ChatOfUser getMessageOfUser() {
        return messageOfUser;
    }

    public void setMessageOfUser(@NonNull ChatOfUser messageOfUser) {
        this.messageOfUser = messageOfUser;
    }

    public CurrentUser getSender() { return sender; }
    public String getContent() { return content; }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    @NonNull
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
}

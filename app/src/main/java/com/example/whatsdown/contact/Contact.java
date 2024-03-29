package com.example.whatsdown.contact;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.whatsdown.Dao.Converters;
import com.example.whatsdown.objects.ChatOfUser;
import com.example.whatsdown.objects.CurrentUser;
import com.example.whatsdown.objects.LastMessage;

@Entity
@TypeConverters(Converters.class)
public class Contact {
    @PrimaryKey()
    @NonNull
    private ChatOfUser chatOfUser;
    private String id;
    private CurrentUser user;
    private LastMessage lastMessage;

    public Contact(String id, CurrentUser user, LastMessage lastMessage) {
        this.id = id;
        this.user = user;
        this.lastMessage = lastMessage;
    }

    public String getId() {
        return id;
    }

    public CurrentUser getUser() {
        return user;
    }

    public LastMessage getLastMessage() {
        return lastMessage;
    }

    @NonNull
    public ChatOfUser getChatOfUser() {
        return chatOfUser;
    }

    public void setChatOfUser(@NonNull ChatOfUser chatOfUser) {
        this.chatOfUser = chatOfUser;
    }

}


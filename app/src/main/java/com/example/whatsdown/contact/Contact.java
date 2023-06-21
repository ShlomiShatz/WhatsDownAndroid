package com.example.whatsdown.contact;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.whatsdown.objects.CurrentUser;
import com.example.whatsdown.objects.LastMessage;

//@Entity
public class Contact {
//    @PrimaryKey()
    String id;
    CurrentUser user;
    LastMessage lastMessage;
//    String name;
//    String whenLastMessage;
//    int img;
//    String lastMessage;
//    public Contact(String name, String whenPosted, int profileImage, String lastMessage) {
//        this.name = name;
//        this.whenLastMessage = whenPosted;
//        this.img = profileImage;
//        this.lastMessage = lastMessage;
//    }

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

    //    public String getName() { return name; }
//    public String getWhenLastMessage() { return whenLastMessage; }
//    public int getProfileImage() { return img; }
//    public String getLastMessage() { return lastMessage; }
}


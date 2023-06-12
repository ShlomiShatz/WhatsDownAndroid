package com.example.whatsdown;

public class Contact {
    String name;
    String whenLastMessage;
    int img;
    String lastMessage;
    public Contact(String name, String whenPosted, int profileImage, String lastMessage) {
        this.name = name;
        this.whenLastMessage = whenPosted;
        this.img = profileImage;
        this.lastMessage = lastMessage;
    }
    public String getName() { return name; }
    public String getWhenLastMessage() { return whenLastMessage; }
    public int getProfileImage() { return img; }
    public String getLastMessage() { return lastMessage; }
}


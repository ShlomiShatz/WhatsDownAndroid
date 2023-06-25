package com.example.whatsdown.objects;

public class ChatOfUser {
    String user;
    String id;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ChatOfUser(String user, String id) {
        this.user = user;
        this.id = id;
    }
}


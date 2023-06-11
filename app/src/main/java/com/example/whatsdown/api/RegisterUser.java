package com.example.whatsdown.api;

public class RegisterUser {
    String username;
    String password;
    String displayName;
    String profilePic;

    public RegisterUser(String username, String password, String displayName, String profilePic) {
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.profilePic = profilePic;
    }
}

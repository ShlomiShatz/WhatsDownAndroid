package com.example.whatsdown.chat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class ChatUser {
    private String id;
    private String username;
    private String displayName;
    private String profilePic;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Bitmap getProfilePic() {
        String result = "";
        switch (profilePic.charAt(11)) {
            case 'p':
            case 'g': result = profilePic.subSequence(22, profilePic.length()).toString();
                break;
            case 'j': result = profilePic.subSequence(23, profilePic.length()).toString();
                break;
        }
        byte[] decodedString = Base64.decode(result, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public ChatUser(String id, String username, String displayName, String profilePic) {
        this.id = id;
        this.username = username;
        this.displayName = displayName;
        this.profilePic = profilePic;
    }
}

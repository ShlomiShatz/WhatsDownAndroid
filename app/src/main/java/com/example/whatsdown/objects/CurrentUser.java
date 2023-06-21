package com.example.whatsdown.objects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;


public class CurrentUser implements Serializable {

    String username;
    String displayName;
    String profilePic;

    public CurrentUser(String username, String displayName, String profilePic) {
        this.username = username;
        this.displayName = displayName;
        this.profilePic = profilePic;
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
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
}

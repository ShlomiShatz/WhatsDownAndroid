package com.example.whatsdown.Dao;

import androidx.room.TypeConverter;

import com.example.whatsdown.objects.ChatOfUser;
import com.example.whatsdown.objects.CurrentUser;
import com.example.whatsdown.objects.LastMessage;
import com.google.gson.Gson;

public class Converters {
    @TypeConverter
    public String fromUserToString(CurrentUser currentUser){
        return new Gson().toJson(currentUser);
    }
    @TypeConverter
    public CurrentUser fromStringToUser(String stringUser){
        return new Gson().fromJson(stringUser, CurrentUser.class);
    }
    @TypeConverter
    public String fromLastMessageToString(LastMessage lstMsg){
        return new Gson().toJson(lstMsg);
    }
    @TypeConverter
    public LastMessage fromStringToLastMessage(String stringLastMsg){
        return new Gson().fromJson(stringLastMsg, LastMessage.class);
    }

    @TypeConverter
    public String fromChatOfUserToString(ChatOfUser chatOfUser){
        return new Gson().toJson(chatOfUser);
    }
    @TypeConverter
    public ChatOfUser fromStringToChatOfUser(String stringChatOfUser){
        return new Gson().fromJson(stringChatOfUser, ChatOfUser.class);
    }

}

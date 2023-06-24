package com.example.whatsdown.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.whatsdown.objects.CurrentUser;

public class ChatViewModel extends ViewModel {
    private final MutableLiveData<String> chatId = new MutableLiveData<String>();
    private MutableLiveData<String> tokenLiveData = new MutableLiveData<String>();
    static String chatIdString;
    static private CurrentUser currentUser;

    public static String getChatIdString() {
        return chatIdString;
    }

    public void setChatId(String id){
        chatIdString = id;
        chatId.setValue((id));
    }

    public LiveData<String> getChatId(){
        return chatId;
    }

    public void setToken(String token) {
        tokenLiveData.setValue(token);
    }

    public LiveData<String> getToken() {
        return tokenLiveData;
    }

    public static void setCurrentUser(CurrentUser currentUser1) {
        currentUser = currentUser1;
    }

    public static CurrentUser getCurrentUser() {
        return currentUser;
    }
}

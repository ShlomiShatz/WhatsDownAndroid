package com.example.whatsdown;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ChatViewModel extends ViewModel {
    private final MutableLiveData<String> chatId = new MutableLiveData<String>();
    private MutableLiveData<String> tokenLiveData = new MutableLiveData<String>();
    static String chatIdString;

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
}

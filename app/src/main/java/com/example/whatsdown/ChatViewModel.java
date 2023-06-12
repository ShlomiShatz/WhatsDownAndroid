package com.example.whatsdown;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ChatViewModel extends ViewModel {
    private final MutableLiveData<String> chatId = new MutableLiveData<String>();

    public void setChatId(String id){
        chatId.setValue((id));
    }

    public LiveData<String> getChatId(){
        return chatId;
    }
}

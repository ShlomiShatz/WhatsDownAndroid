package com.example.whatsdown.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.whatsdown.Contact;
import com.example.whatsdown.Message;
import com.example.whatsdown.repositories.ContactRepository;
import com.example.whatsdown.repositories.MessageRepository;

import java.util.List;

public class MessageViewModel extends ViewModel {

    private MessageRepository messageRepository;
    private LiveData<List<Message>> messages;

    public MessageViewModel(){
        messageRepository = new MessageRepository();
        messages = messageRepository.getAll();
    }

    public LiveData<List<Message>> get() {
        return messages;
    }

    public void add(Message message){
        messageRepository.add(message);
    }

    public void delete(Message message){
        messageRepository.delete(message);
    }
    public void reload() {
        messageRepository.reload();
    }
}
package com.example.whatsdown.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.whatsdown.contact.Contact;
import com.example.whatsdown.repositories.ContactRepository;

import java.util.List;

public class ContactViewModel extends ViewModel {
    private ContactRepository idsChatRepository;
    private LiveData<List<Contact>> contacts;
    private String token;

    public ContactViewModel(){
//        this.token = token;
        idsChatRepository = new ContactRepository();
        contacts = idsChatRepository.getAll();
    }

    public void setToken(String token) {
        this.token = token;
        idsChatRepository.setToken(token);
    }

    public LiveData<List<Contact>> get() {return contacts;}

    public void add(String username) {idsChatRepository.add(username);}

    public void delete(Contact contact) {idsChatRepository.delete(contact);}

    public void reload() {idsChatRepository.reload();}
}

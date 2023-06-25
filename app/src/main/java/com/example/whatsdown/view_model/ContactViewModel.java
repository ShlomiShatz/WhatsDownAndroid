package com.example.whatsdown.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.whatsdown.contact.Contact;
import com.example.whatsdown.repositories.ContactRepository;

import java.util.List;

public class ContactViewModel extends ViewModel {

    public interface AddContactCallback {
        void onContactAdded(boolean success);
    }
    private static ContactRepository idsChatRepository;
    private LiveData<List<Contact>> contacts;
    private String token;

    public ContactViewModel(){
        idsChatRepository = new ContactRepository();
        contacts = idsChatRepository.getAll();
    }

    public static ContactRepository getIdsChatRepository() {
        return idsChatRepository;
    }

    public void setToken(String token) {
        this.token = token;
        idsChatRepository.setToken(token);
    }

    public LiveData<List<Contact>> get() {return contacts;}

    public void add(String username, AddContactCallback callback){

        idsChatRepository.add(username, callback);
    }

    public void deleteFirebaseToken() {
        idsChatRepository.deleteFirebaseToken();
    }

    public void delete(Contact contact) {
        idsChatRepository.delete(contact);
    }

    public void reload() {
        idsChatRepository.reload();
    }
    public void deleteAll() {
        idsChatRepository.deleteAll();
    }
}

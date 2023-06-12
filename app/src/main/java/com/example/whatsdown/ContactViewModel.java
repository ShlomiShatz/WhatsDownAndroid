package com.example.whatsdown;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.whatsdown.repositories.ContactRepository;

import java.util.List;

public class ContactViewModel extends ViewModel {
    private ContactRepository idsChatRepository;
    private LiveData<List<Contact>> contacts;

    public ContactViewModel(){
        idsChatRepository = new ContactRepository();
        contacts = idsChatRepository.getAll();
    }

    public LiveData<List<Contact>> get() {return contacts;}

    public void add(Contact contact) {idsChatRepository.add(contact);}

    public void delete(Contact contact) {idsChatRepository.delete(contact);}

    public void reload() {idsChatRepository.reload();}
}

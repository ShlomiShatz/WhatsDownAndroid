package com.example.whatsdown.repositories;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.whatsdown.Dao.ContactDao;
import com.example.whatsdown.Dao.LocalDatabase;
import com.example.whatsdown.Dao.MessageDao;
import com.example.whatsdown.api.ContactAPI;
import com.example.whatsdown.contact.Contact;

import com.example.whatsdown.api.PostCallback;

import com.example.whatsdown.view_model.ChatViewModel;
import com.example.whatsdown.view_model.ContactViewModel;

import java.util.ArrayList;

import java.util.List;

public class ContactRepository {
    private ContactListData contactListData;
    private ContactDao contactDao;
    private MessageDao messageDao;
    private ContactAPI contactAPI;
    public ContactRepository(){
        LocalDatabase db = LocalDatabase.getInstance();
        contactDao = db.contactDao();
        messageDao = db.messageDao();
        contactListData = new ContactListData();
        contactAPI = new ContactAPI(contactListData, contactDao, messageDao);
    }
    class ContactListData extends MutableLiveData<List<Contact>> {
        public ContactListData() {
            super();
            new Thread(()->{
                getContactsFromRoom();
                contactAPI.get();
            }).start();
        }

        @Override
        protected void onActive() {
            super.onActive();
            contactAPI.get();
        }

        protected void getContactsFromRoom(){
            List<Contact> lst = contactDao.index();
            List<Contact> newLst = new ArrayList<>();
            for (Contact contact:lst) {
                if (contact.getChatOfUser().getUser().equals(ChatViewModel.getLoginUser().getUsername())){
                    newLst.add(contact);
                }
            }
            postValue(newLst);
        }
    }

    public LiveData<List<Contact>> getAll(){
        return contactListData;
    }

    public void deleteFirebaseToken() {
        contactAPI.deleteFirebase();
    }

    public void add(String username, ContactViewModel.AddContactCallback callback) {
        contactAPI.add(username, new PostCallback() {
            @Override
            public void onPostComplete(boolean registered) {
                if (registered) {
                    callback.onContactAdded(true);
                } else {
                    callback.onContactAdded(false);
                }
            }
        });
    }


    public void delete(final Contact contact){
        contactAPI.delete(contact.getId());
    }
    public void deleteAll(){
        contactAPI.deleteAll();
    }

    public void reload(){
         contactAPI.get();
    }




}

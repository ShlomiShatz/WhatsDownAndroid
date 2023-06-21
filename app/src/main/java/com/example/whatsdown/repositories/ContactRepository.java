package com.example.whatsdown.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.whatsdown.Dao.ContactDao;
import com.example.whatsdown.Dao.LocalDatabase;
import com.example.whatsdown.Dao.MessageDao;
import com.example.whatsdown.api.ContactAPI;
import com.example.whatsdown.contact.Contact;
import com.example.whatsdown.api.LoginAPI;
import com.example.whatsdown.api.PostCallback;
import com.example.whatsdown.view_model.ContactViewModel;

import java.util.LinkedList;
import java.util.List;

public class ContactRepository {
    private ContactListData contactListData;
    private ContactDao contactDao;
    private MessageDao messageDao;
    private List<Contact> list;
    private ContactListData contactListDataListData;
    private ContactAPI contactAPI;
    private String token;

    public ContactRepository(){
        LocalDatabase db = LocalDatabase.getInstance();
        contactDao = db.contactDao();
        messageDao = db.messageDao();
        token = LoginAPI.getToken();
        contactListData = new ContactListData();
        contactAPI = new ContactAPI(contactListData, contactDao, messageDao);
    }

    public void setToken(String token) {
        this.token = token;
    }


    class ContactListData extends MutableLiveData<List<Contact>> {
        public ContactListData() {
            //super();
            super();
            setValue(new LinkedList<>());
        }

        @Override
        protected void onActive() {
            super.onActive();

            /*new Thread(()->{
                contactListData.postValue(contactDao.get());
            }).start();
             */
            contactAPI.get();
        }
    }


    public LiveData<List<Contact>> getAll(){
        return contactListData;
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

    public void reload(){
         contactAPI.get();
    }




}

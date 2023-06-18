package com.example.whatsdown.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.whatsdown.Contact;
import com.example.whatsdown.api.ChatsAPI;
import com.example.whatsdown.api.LoginAPI;
import com.example.whatsdown.api.PostCallback;

import java.util.LinkedList;
import java.util.List;

public class ContactRepository {
    private ContactListData contactListData;
    //    private ContactDao contactDao;
    private List<Contact> list;
    private ChatsAPI chatsAPI;
    private String token;


    public ContactRepository(){
        //LocalDatabase db = LocalDatabase.getInstance();
        //contactDao = db.contactDao();
        chatsAPI = new ChatsAPI();
        token = LoginAPI.getToken();
        contactListData = new ContactListData();
    }

    public void setToken(String token) {
        this.token = token;
    }


    class ContactListData extends MutableLiveData<List<Contact>> {
        public ContactListData() {
            //super();
            chatsAPI.get(token, new PostCallback() {
                @Override
                public void onPostComplete(boolean registered) {
                    if (registered) {
                        if (chatsAPI.getList().size() > 0) {
                            list = chatsAPI.getList();
                            setValue(list);
                        }
                    } else {
                        setValue(new LinkedList<>());
                    }
                }
            });
            setValue(list);
        }

        public void setListValue(List<Contact> value) {
            setValue(value);
        }

        @Override
        protected void onActive() {
            super.onActive();

            /*new Thread(()->{
                contactListData.contactValue(contactDao.get());
            }).start();
             */
        }
    }


    public LiveData<List<Contact>> getAll(){
        return contactListData;
    }

    public void add(String username){
        chatsAPI.add(token, username, new PostCallback() {
            @Override
            public void onPostComplete(boolean registered) {
                if (registered) {
                    reload();
                } else {
                    //error
                }
            }
        });


    }

    public void delete(final Contact contact){
        chatsAPI.delete(token,contact.getId(),new PostCallback() {
            @Override
            public void onPostComplete(boolean registered) {
                if (registered) {
                    reload();
                } else {
                    //error
                }
            }
        });
    }

    public void reload(){
         chatsAPI.get(token,new PostCallback() {
             @Override
             public void onPostComplete(boolean registered) {
                 if (chatsAPI.getList().size() > 0) {
                     list = chatsAPI.getList();
                 } else {
                    list = new LinkedList<>();
                 }
                 contactListData.setListValue(list);
             }

         });

    }




}

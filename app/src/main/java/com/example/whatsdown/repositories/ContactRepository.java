package com.example.whatsdown.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.whatsdown.Contact;
import com.example.whatsdown.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ContactRepository {
    private ContactListData contactListData;
    //private ContactDao contactDao;
    //private ContactAPI contactAPI;


    public ContactRepository(){
        //LocalDatabase db = LocalDatabase.getInstance();
        //contactDao = db.contactDao();
        contactListData = new ContactListData();
        //contactAPI = new ContactAPI(contactListData, contactDao);
    }



    class ContactListData extends MutableLiveData<List<Contact>> {
        public ContactListData() {
            //super();

            //need to delete
            List<Contact> contacts = new LinkedList<>();
            contacts.add(new Contact("aaaa", "15:41", R.drawable.penguin, "hii"));
            contacts.add(new Contact("aaaa", "15:41", R.drawable.penguin, "hii"));
            contacts.add(new Contact("aaaa", "15:41", R.drawable.penguin, "hii"));
            contacts.add(new Contact("aaaa", "15:41", R.drawable.penguin, "hii"));
            contacts.add(new Contact("aaaa", "15:41", R.drawable.penguin, "hii"));
            setValue(contacts);
            //until here

            //setValue(new LinkedList<>());
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

        public void add(final Contact contact){
            //contactAPI.add(contact);
         }

         public void delete(final Contact contact){
             //contactAPI.delete(contact);
         }

         public void reload(){
             //contactAPI.get();
         }




}

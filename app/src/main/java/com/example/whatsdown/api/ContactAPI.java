package com.example.whatsdown.api;

import androidx.lifecycle.MutableLiveData;

import com.example.whatsdown.Dao.ContactDao;
import com.example.whatsdown.Dao.MessageDao;
import com.example.whatsdown.contact.Contact;
import com.example.whatsdown.objects.ChatOfUser;
import com.example.whatsdown.objects.FirebaseToken;
import com.example.whatsdown.objects.Username;
import com.example.whatsdown.view_model.ChatViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactAPI {

    Retrofit retrofit;
    WebServiceAPI webServiceAPI;

    ContactDao contactDao;
    MessageDao messageDao;
    MutableLiveData<List<Contact>> listContact;

    public ContactAPI(MutableLiveData<List<Contact>> listContacts, ContactDao cDao, MessageDao mDao) {
        this.listContact = listContacts;
        this.contactDao = cDao;
        this.messageDao = mDao;
        retrofit = new Retrofit.Builder()
                .baseUrl(ServerPath.getPath())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void get() {
        Call<List<Contact>> call = webServiceAPI.getChatList(LoginAPI.getToken());
        call.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                if(response.code() == 200) {
                    List<Contact> list = response.body();
                    for (Contact contact: list) {
                        ChatOfUser chatOfUser = new ChatOfUser(ChatViewModel.getLoginUser().getUsername(), contact.getId());
                        contact.setChatOfUser(chatOfUser);
                    }
                    listContact.postValue(list);
                    new Thread(()->{
                        contactDao.insertListReplace(list);
                    }).start();
                } else {
                    listContact.postValue(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
                //what to do
            }
        });
    }
    public void add(String username, PostCallback callback) {
        Username userNameObj = new Username(username);
        Call<Contact> call = webServiceAPI.addContact(LoginAPI.getToken(), userNameObj);
        call.enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response) {
                if(response.code() == 200) {
                    Contact contact = response.body();
                    ChatOfUser chatOfUser = new ChatOfUser(ChatViewModel.getLoginUser().getUsername(),contact.getId());
                    contact.setChatOfUser(chatOfUser);
                    List<Contact> contacts = listContact.getValue();
                    contacts.add(contact);
                    listContact.postValue(contacts);
                    new Thread(()->{
                        contactDao.insertReplace(contact);
                    }).start();

                    callback.onPostComplete(true);
                } else {
                    callback.onPostComplete(false);
                }
            }

            @Override
            public void onFailure(Call<Contact> call, Throwable t) {
                t.printStackTrace();
                callback.onPostComplete(false);
            }
        });
    }

    public void delete(String chatId){
        if (chatId != null) {
            Call<Void> call = webServiceAPI.deleteContact(chatId, LoginAPI.getToken());
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(response.code() == 200) {
                        new Thread(()->{
                            contactDao.deleteById(chatId);
                        }).start();
                        new Thread(()->{
                            messageDao.deleteByChatId(chatId);
                        }).start();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }

    public void deleteFirebase() {
        if (ChatViewModel.getLoginUser().getUsername() != null) {
            Call<Void> call = webServiceAPI.sendFirebaseToken(ChatViewModel.getLoginUser().getUsername(), LoginAPI.getToken(), new FirebaseToken(""));

            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {

                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }

    public void deleteAll() {
        new Thread(()->{
            contactDao.deleteAll();
        }).start();
    }
}

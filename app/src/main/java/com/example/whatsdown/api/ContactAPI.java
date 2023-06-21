package com.example.whatsdown.api;

import androidx.lifecycle.MutableLiveData;

import com.example.whatsdown.Dao.ContactDao;
import com.example.whatsdown.Dao.MessageDao;
import com.example.whatsdown.contact.Contact;
import com.example.whatsdown.objects.Message;
import com.example.whatsdown.objects.Username;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactAPI {

    Retrofit retrofit;
    WebServiceAPI webServiceAPI;

    ContactDao contactDao;
    MutableLiveData<List<Contact>> listContact;

    public ContactAPI(MutableLiveData<List<Contact>> listContacts, ContactDao cDao) {
        // FOR DEBUGGING*****************************************************
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        // TILL HERE*************************************************************
        this.listContact = listContacts;
        this.contactDao = cDao;
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)// FOR DEBUGGING********************************************************
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void get() {
        Call<List<Contact>> call = webServiceAPI.getChatList(LoginAPI.getToken());
        call.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                if(response.code() == 200) {
                    listContact.postValue(response.body());
                    contactDao.insertListReplace(response.body());
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
    public void add( String username, PostCallback callback) {
        Username userNameObj = new Username(username);
        Call<Contact> call = webServiceAPI.addContact(LoginAPI.getToken(), userNameObj);
        call.enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response) {
                if(response.code() == 200) {
                    Contact contact = response.body();
                    List<Contact> contacts = listContact.getValue();
                    contacts.add(contact);
                    listContact.postValue(contacts);
                    contactDao.insertReplace(contact);
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
        Call<Void> call = webServiceAPI.deleteContact(chatId, LoginAPI.getToken());

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 200) {
                    contactDao.deleteById(chatId);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}

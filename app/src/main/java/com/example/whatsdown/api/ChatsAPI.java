package com.example.whatsdown.api;

import com.example.whatsdown.Contact;
import com.example.whatsdown.CurrentUser;
import com.example.whatsdown.repositories.ContactRepository;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatsAPI {
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;
    List<Contact> list;

    public List<Contact> getList() {
        return list;
    }

    public ChatsAPI() {
        // FOR DEBUGGING*****************************************************
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        // TILL HERE*************************************************************

        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.19:5000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)// FOR DEBUGGING********************************************************
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void get(String tokenToSend, PostCallback callback) {
        Call<List<Contact>> call = webServiceAPI.getChatList(tokenToSend);
        call.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                if(response.code() == 200) {
                    list = response.body();
                    callback.onPostComplete(true);
                } else {
                    callback.onPostComplete(false);
                }
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
                t.printStackTrace();
                callback.onPostComplete(false);
            }
        });
    }

    public void add(String tokenToSend, String username, PostCallback callback) {
        Call<Void> call = webServiceAPI.addContact(tokenToSend, username);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 200) {
                    callback.onPostComplete(true);
                } else {
                    callback.onPostComplete(false);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
                callback.onPostComplete(false);
            }
        });
    }
}

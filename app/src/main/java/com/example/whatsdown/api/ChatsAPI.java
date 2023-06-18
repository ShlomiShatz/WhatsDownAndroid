package com.example.whatsdown.api;

import com.example.whatsdown.Contact;
import com.example.whatsdown.CurrentUser;
import com.example.whatsdown.Message;
import com.example.whatsdown.Msg;
import com.example.whatsdown.objects.Username;
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
    List<Message> messageList;

    public List<Contact> getList() {
        return list;
    }

    public List<Message> getMessageList() {
        return messageList;
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
                .baseUrl("http://10.0.2.2:5000/api/")
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
        Username userNameObj = new Username(username);
        Call<Void> call = webServiceAPI.addContact(tokenToSend, userNameObj);
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

    public void getMessages(String id, String tokenToSend, PostCallback callback) {
        Call<List<Message>> call = webServiceAPI.getMessages(id, tokenToSend);
        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if(response.code() == 200) {
                    messageList = response.body();
                    callback.onPostComplete(true);
                } else {
                    callback.onPostComplete(false);
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                t.printStackTrace();
                callback.onPostComplete(false);
            }
        });
    }

    public void sendMessage(String id, String tokenToSend, Msg msg, PostCallback callback) {
        Call<Void> call = webServiceAPI.sendMessage(id, tokenToSend, msg);
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

    public void delete(String tokenToSend, String chatId, PostCallback callback ){
        Call<Void> call = webServiceAPI.deleteContact(chatId, tokenToSend);

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

package com.example.whatsdown.api;

import androidx.lifecycle.MutableLiveData;

import com.example.whatsdown.Dao.ContactDao;
import com.example.whatsdown.Dao.MessageDao;
import com.example.whatsdown.objects.ChatOfUser;
import com.example.whatsdown.objects.LastMessage;
import com.example.whatsdown.objects.Message;
import com.example.whatsdown.objects.Msg;
import com.example.whatsdown.view_model.ChatViewModel;
import com.google.gson.Gson;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatsAPI {
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;
    MessageDao messageDao;
    ContactDao contactDao;
    MutableLiveData<List<Message>> listMessages;

    public ChatsAPI(MutableLiveData<List<Message>> listMessage, MessageDao mDao, ContactDao cDao) {
        this.listMessages = listMessage;
        this.messageDao = mDao;
        this.contactDao = cDao;
        retrofit = new Retrofit.Builder()
                .baseUrl(ServerPath.getPath())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void getMessages() {
        if (ChatViewModel.getChatIdString() != null) {
            Call<List<Message>> call = webServiceAPI.getMessages(ChatViewModel.getChatIdString(), LoginAPI.getToken());
            call.enqueue(new Callback<List<Message>>() {
                @Override
                public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                    if (response.code() == 200) {
                        List<Message> lst = response.body();
                        for (Message msg : lst) {
                            ChatOfUser chatOfUser = new ChatOfUser(ChatViewModel.getLoginUser().getUsername(),msg.getId());
                            msg.setChatId(ChatViewModel.getChatIdString());
                            msg.setMessageOfUser(chatOfUser);
                        }
                        listMessages.postValue(lst);
                        new Thread(() -> {
                            messageDao.insertListReplace(lst);
                        }).start();
                    }
                }

                @Override
                public void onFailure(Call<List<Message>> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }

    public void sendMessage(Msg msg) {
        if (ChatViewModel.getChatIdString() != null) {
            Call<Message> call = webServiceAPI.sendMessage(ChatViewModel.getChatIdString(), LoginAPI.getToken(), msg);
            call.enqueue(new Callback<Message>() {
                @Override
                public void onResponse(Call<Message> call, Response<Message> response) {
                    if(response.code() == 200) {
                        Message newMsg = response.body();
                        newMsg.setChatId(ChatViewModel.getChatIdString());
                        LastMessage lstMsg = new LastMessage(newMsg.getId(), newMsg.getCreated(), newMsg.getContent());
                        String stringLstMsg = new Gson().toJson(lstMsg);
                        List<Message> lstMessages = listMessages.getValue();
                        ChatOfUser chatOfUser = new ChatOfUser( ChatViewModel.getLoginUser().getUsername(),newMsg.getId());
                        newMsg.setMessageOfUser(chatOfUser);
                        lstMessages.add(newMsg);
                        listMessages.postValue(lstMessages);
                        new Thread(()->{
                            messageDao.insert(newMsg);
                        }).start();
                        new Thread(()->{
                            contactDao.updateLastMessage(stringLstMsg, ChatViewModel.getChatIdString());
                        }).start();
                    }
                }

                @Override
                public void onFailure(Call<Message> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }

    public void deleteAll() {
        new Thread(()->{
            messageDao.deleteAll();
        }).start();
    }
}
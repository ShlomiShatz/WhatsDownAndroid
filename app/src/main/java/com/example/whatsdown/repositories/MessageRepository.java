package com.example.whatsdown.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.whatsdown.Dao.ContactDao;
import com.example.whatsdown.Dao.LocalDatabase;
import com.example.whatsdown.Dao.MessageDao;
import com.example.whatsdown.objects.Message;
import com.example.whatsdown.objects.Msg;
import com.example.whatsdown.api.ChatsAPI;
import com.example.whatsdown.view_model.ChatViewModel;

import java.util.LinkedList;
import java.util.List;

public class MessageRepository {

    private MessageListData messageListData;
    private MessageDao messageDao;
    private ContactDao contactDao;
    private ChatsAPI chatsAPI;

    public MessageRepository(){
        LocalDatabase db = LocalDatabase.getInstance();
        messageDao = db.messageDao();
        contactDao = db.contactDao();
        messageListData = new MessageListData();
        chatsAPI = new ChatsAPI(messageListData, messageDao, contactDao);
    }



    class MessageListData extends MutableLiveData<List<Message>> {
        public MessageListData() {
            super();
            setValue(new LinkedList<>());
        }

        @Override
        protected void onActive() {
            super.onActive();

            new Thread(()->{
                messageListData.postValue(messageDao.get(ChatViewModel.getChatIdString()));
            }).start();

            chatsAPI.getMessages();
        }
    }

    public LiveData<List<Message>> getAll(){
        return messageListData;
    }

    public void add(final String message){
        chatsAPI.sendMessage(new Msg(message));
    }

    public void delete(final Message message){
        //ChatsAPI.delete(message);
    }

    public void reload(){
        chatsAPI.getMessages();
    }
}

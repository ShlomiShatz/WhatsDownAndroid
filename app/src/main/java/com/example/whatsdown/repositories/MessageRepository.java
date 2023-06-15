package com.example.whatsdown.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.whatsdown.Contact;
import com.example.whatsdown.Message;
import com.example.whatsdown.R;

import java.util.LinkedList;
import java.util.List;

public class MessageRepository {

    private MessageRepository.MessageListData messageListData;
    //private ContactDao contactDao;
    //private ChatsAPI ChatsAPI;


    public MessageRepository(){
        //LocalDatabase db = LocalDatabase.getInstance();
        //messageDao = db.messageDao();
        messageListData = new MessageRepository.MessageListData();
        //messageAPI = new MessageAPI(messageListData, messageDao);
    }



    class MessageListData extends MutableLiveData<List<Message>> {
        public MessageListData() {
            //super();

            //need to delete
            List<Message> messages = new LinkedList<>();

            messages.add(new Message("aaaa","hii", "15:41"));
            messages.add(new Message("aaaa","hii", "15:41"));
            messages.add(new Message("aaaa","hii", "15:41"));
            messages.add(new Message("aaaa","hii", "15:41"));
            messages.add(new Message("aaaa","hii", "15:41"));
            messages.add(new Message("aaaa","hii", "15:41"));
            messages.add(new Message("aaaa","hii", "15:41"));
            messages.add(new Message("aaaa","hii", "15:41"));
            setValue(messages);
            //until here

            //setValue(new LinkedList<>());
        }

        @Override
        protected void onActive() {
            super.onActive();

            /*new Thread(()->{
                messageListData.messageValue(messageDao.get());
            }).start();
             */
        }
    }

    public LiveData<List<Message>> getAll(){
        return messageListData;
    }

    public void add(final Message message){
        //ChatsAPI.add(message);
    }

    public void delete(final Message message){
        //ChatsAPI.delete(message);
    }

    public void reload(){
        //messageAPI.get();
    }
}

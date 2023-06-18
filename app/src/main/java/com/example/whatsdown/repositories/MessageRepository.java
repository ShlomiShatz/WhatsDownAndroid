package com.example.whatsdown.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.whatsdown.ChatViewModel;
import com.example.whatsdown.Message;
import com.example.whatsdown.Msg;
import com.example.whatsdown.api.ChatsAPI;
import com.example.whatsdown.api.LoginAPI;
import com.example.whatsdown.api.PostCallback;

import java.util.LinkedList;
import java.util.List;

public class MessageRepository {

    private MessageRepository.MessageListData messageListData;
    //private ContactDao contactDao;
    private ChatsAPI chatsAPI;
    List<Message> messages;


    public MessageRepository(){
        //LocalDatabase db = LocalDatabase.getInstance();
        //messageDao = db.messageDao();
        messageListData = new MessageRepository.MessageListData();
        //messageAPI = new MessageAPI(messageListData, messageDao);
    }



    class MessageListData extends MutableLiveData<List<Message>> {
        public MessageListData() {
            //super();
            messages = new LinkedList<>();
            chatsAPI = new ChatsAPI();
            chatsAPI.getMessages(ChatViewModel.getChatIdString(), LoginAPI.getToken(), new PostCallback() {
                @Override
                public void onPostComplete(boolean registered) {
                    if (registered) {
                        messages = chatsAPI.getMessageList();
                        setValue(messages);
                    } else {
                        //Error
                    }
                }
            });


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
        chatsAPI.sendMessage(ChatViewModel.getChatIdString(), LoginAPI.getToken(), new Msg(message.getContent()), new PostCallback() {
            @Override
            public void onPostComplete(boolean registered) {
                if (registered) {

                } else {
                    //Error

                }
            }
        });
    }

    public void delete(final Message message){
        //ChatsAPI.delete(message);
    }

    public void reload(){
        chatsAPI.getMessages(ChatViewModel.getChatIdString(), LoginAPI.getToken(), new PostCallback() {
            @Override
            public void onPostComplete(boolean registered) {
                if (registered) {
                    messages = chatsAPI.getMessageList();
                } else {
                    //Error

                }
            }
        });
    }
}

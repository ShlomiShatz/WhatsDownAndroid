package com.example.whatsdown.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.whatsdown.Dao.AppDB;
import com.example.whatsdown.Dao.MessageDao;
import com.example.whatsdown.view_model.ChatViewModel;
import com.example.whatsdown.objects.Message;
import com.example.whatsdown.objects.Msg;
import com.example.whatsdown.api.ChatsAPI;
import com.example.whatsdown.api.LoginAPI;
import com.example.whatsdown.api.PostCallback;

import java.util.LinkedList;
import java.util.List;

public class MessageRepository {

    private MessageRepository.MessageListData messageListData;
    private MessageDao messageDao;
    private ChatsAPI chatsAPI;
    List<Message> messages;


    public MessageRepository(){
        //AppDB db = AppDB.getInstance();
       // messageDao = db.messageDao();
        messageListData = new MessageRepository.MessageListData();
        //chatsAPI = new ChatAPI(messageListData, messageDao);
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
                        //Error - logout
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
                    //Error - unregistered
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
                    //Error - logout

                }
            }
        });
    }
}

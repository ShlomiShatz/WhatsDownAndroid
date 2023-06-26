package com.example.whatsdown.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.whatsdown.Dao.ContactDao;
import com.example.whatsdown.Dao.LocalDatabase;
import com.example.whatsdown.Dao.MessageDao;
import com.example.whatsdown.login.MainActivity;
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
        LocalDatabase db = LocalDatabase.getInstance(MainActivity.getMainActivity());
        messageDao = db.messageDao();
        contactDao = db.contactDao();
        messageListData = new MessageListData();
        chatsAPI = new ChatsAPI(messageListData, messageDao, contactDao);
        messageListData.setList();
    }

    class MessageListData extends MutableLiveData<List<Message>> {
        public MessageListData() {
            super();
            postValue(new LinkedList<Message>());
        }

        public void setList(){
            new Thread(()->{
                List<Message> lst = messageDao.get(ChatViewModel.getChatIdString());
                List<Message> newLst = new LinkedList<>();
                for (Message messageElement: lst) {
                    if (messageElement.getMessageOfUser().getUser().equals(ChatViewModel.getLoginUser().getUsername())) {
                        newLst.add(messageElement);
                    }
                }
                messageListData.postValue(newLst);
                chatsAPI.getMessages();
            }).start();
        }

        @Override
        protected void onActive() {
            super.onActive();
            new Thread(()->{
                List<Message> lst = messageDao.get(ChatViewModel.getChatIdString());
                messageListData.postValue(lst);
                List<Message> newLst = new LinkedList<>();
                for (Message messageElement: lst) {
                    if (messageElement.getMessageOfUser().getUser().equals(ChatViewModel.getLoginUser().getUsername())) {
                        newLst.add(messageElement);
                    }
                }
                messageListData.postValue(newLst);
            }).start();
        }
    }

    public LiveData<List<Message>> getAll(){
        return messageListData;
    }

    public void add(final String message){
        chatsAPI.sendMessage(new Msg(message));
    }

    public void deleteAll(){
        chatsAPI.deleteAll();
    }

    public void reload(){
        chatsAPI.getMessages();
    }
}

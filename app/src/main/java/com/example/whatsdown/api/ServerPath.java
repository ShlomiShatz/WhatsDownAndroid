package com.example.whatsdown.api;

import com.example.whatsdown.Dao.ContactDao;
import com.example.whatsdown.Dao.LocalDatabase;
import com.example.whatsdown.Dao.MessageDao;
import com.example.whatsdown.chat.ChatViewActivity;
import com.example.whatsdown.repositories.ContactRepository;
import com.example.whatsdown.repositories.MessageRepository;
import com.example.whatsdown.view_model.ChatViewModel;
import com.example.whatsdown.view_model.ContactViewModel;

public class ServerPath {
    private static String path = "http://192.168.197.91:5000/";

    private ServerPath(){
        super();
    }

    public static String getPath() {
        return path;
    }

    public static void setPath(String path) {
        if (!ServerPath.path.equals(path)){
            if (ContactViewModel.getIdsChatRepository() != null) {
                ContactViewModel.getIdsChatRepository().deleteAll();
            }
            if (ChatViewActivity.getMessageViewModel() != null) {
                ChatViewActivity.getMessageViewModel().deleteAll();
            }
        }
        ServerPath.path = path;
    }
}

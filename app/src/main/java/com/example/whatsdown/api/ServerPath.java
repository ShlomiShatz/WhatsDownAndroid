package com.example.whatsdown.api;

import com.example.whatsdown.Dao.ContactDao;
import com.example.whatsdown.Dao.LocalDatabase;
import com.example.whatsdown.Dao.MessageDao;
import com.example.whatsdown.repositories.ContactRepository;
import com.example.whatsdown.repositories.MessageRepository;

public class ServerPath {
    private static String path = "http://10.0.2.2:5000/";

    private ServerPath(){
        super();
    }

    public static String getPath() {
        return path;
    }

    public static void setPath(String path) {
        if (!ServerPath.path.equals(path)){
//            ContactRepository contactRepository = new ContactRepository();
//            MessageRepository messageRepository = new MessageRepository();
//            contactRepository.deleteAll();
//            messageRepository.deleteAll();
        }
        ServerPath.path = path;
    }
}

//package com.example.whatsdown.Dao;
//
//import android.content.Context;
//
//import com.example.whatsdown.contact.Contact;
//import com.example.whatsdown.objects.Message;
//
//
//import androidx.room.Database;
//import androidx.room.Room;
//import androidx.room.RoomDatabase;
//
//@Database(entities = {Contact.class, Message.class}, version = 1)
//public abstract class AppDB extends  RoomDatabase{
//
//    private static AppDB instance;
//    public abstract ContactDao contactDao();
//    public abstract MessageDao messageDao();
//    private static Context context;
//
//    public static synchronized AppDB getInstance(){
//        if (instance == null){
//            instance = Room.databaseBuilder(context.getApplicationContext(), AppDB.class, "LocalDB").build();
//        }
//        return instance;
//    }
//    public AppDB(Context context1){
//        context = context1;
//    }
//
//}

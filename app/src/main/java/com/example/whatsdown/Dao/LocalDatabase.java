package com.example.whatsdown.Dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.whatsdown.contact.Contact;
import com.example.whatsdown.objects.Message;


@Database(entities = {Contact.class, Message.class}, version = 2)
public abstract class LocalDatabase extends RoomDatabase {

    private static LocalDatabase instance;
    public abstract ContactDao contactDao();
    public abstract MessageDao messageDao();
    private static Context context;

    public static synchronized LocalDatabase getInstance(){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), LocalDatabase.class, "LocalDB")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public static void setContext(Context context1){
        context = context1;
    }
}
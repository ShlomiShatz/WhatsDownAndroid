package com.example.whatsdown.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.whatsdown.contact.Contact;
import com.example.whatsdown.objects.Message;

import java.util.List;

@Dao
public interface MessageDao {

    @Query("SELECT * FROM message WHERE chatId = :id")
    List<Message> get(String id);

    @Insert
    void insert(Message... messages);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertListReplace(List<Message> messages);

    @Update
    void update(Message... messages);

    @Delete
    void delete(Message... messages);

    @Query("DELETE FROM message WHERE chatId = :id")
    void deleteByChatId(String id);

    @Query("DELETE FROM message")
    void deleteAll();

}

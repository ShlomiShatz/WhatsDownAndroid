package com.example.whatsdown.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.whatsdown.contact.Contact;
import com.example.whatsdown.objects.LastMessage;
import com.example.whatsdown.objects.Message;

import java.util.List;

@Dao
public interface ContactDao {

    @Query("SELECT * FROM contact")
    List<Contact> index();

    @Query("SELECT * FROM contact WHERE id = :id")
    List<Contact> get(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertReplace(Contact... contacts);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertListReplace(List<Contact> contacts);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertIgnore(Contact... contacts);

    @Update
    void update(Contact... contacts);

    @Delete
    void delete(Contact... contacts);

    @Query("DELETE FROM contact WHERE id = :id")
    void deleteById(String id);

    @Query("UPDATE contact SET lastMessage = :msg WHERE id = :id")
    void updateLastMessage(String msg, String id);

    @Query("DELETE FROM contact")
    void deleteAll();
}

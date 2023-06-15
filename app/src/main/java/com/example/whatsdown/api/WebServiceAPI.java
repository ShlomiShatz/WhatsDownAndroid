package com.example.whatsdown.api;

import com.example.whatsdown.Contact;
import com.example.whatsdown.CurrentUser;
import com.example.whatsdown.UserDits;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebServiceAPI {
    @POST("Tokens")
    Call<String> getToken(@Body UserDits userDits);

    @GET("Users/{username}")
    Call<CurrentUser> getCurrentUser(@Path("username") String username, @Header("Authorization") String token);

    @GET("Chats")
    Call<List<Contact>> getChatList(@Header("Authorization") String token);

    @POST("Chats")
    Call<Void> addContact(@Header("Authorization") String token, @Body String username);

    @POST("Users")
    Call<Void> register(@Body RegisterUser registerUser);

    @DELETE("Chats/{id}")
    Call<Void> deleteContact(@Path("id") int id, @Header("Authorization") String token);

    @GET("Chats/{id}/Messages")
    Call<List<Contact>> getMessages(@Path("id") int id, @Header("Authorization") String token);

    @POST("Chats/{id}/Messages")
    Call<List<Contact>> sendMessage(@Path("id") int id, @Header("Authorization") String token, @Body String msg);
}

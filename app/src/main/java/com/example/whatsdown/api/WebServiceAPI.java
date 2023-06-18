package com.example.whatsdown.api;

import com.example.whatsdown.Contact;
import com.example.whatsdown.CurrentUser;
import com.example.whatsdown.Message;
import com.example.whatsdown.Msg;
import com.example.whatsdown.UserDits;
import com.example.whatsdown.objects.Username;

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
    Call<Void> addContact(@Header("Authorization") String token, @Body Username username);

    @POST("Users")
    Call<Void> register(@Body RegisterUser registerUser);

    @DELETE("Chats/{id}")
    Call<Void> deleteContact(@Path("id") String id, @Header("Authorization") String token);

    @GET("Chats/{id}/Messages")
    Call<List<Message>> getMessages(@Path("id") String id, @Header("Authorization") String token);

    @POST("Chats/{id}/Messages")
    Call<Void> sendMessage(@Path("id") String id, @Header("Authorization") String token, @Body Msg msg);
}

package com.example.whatsdown.api;

import com.example.whatsdown.contact.Contact;
import com.example.whatsdown.objects.CurrentUser;
import com.example.whatsdown.objects.FirebaseToken;
import com.example.whatsdown.objects.Message;
import com.example.whatsdown.objects.Msg;
import com.example.whatsdown.objects.UserDits;
import com.example.whatsdown.objects.Username;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebServiceAPI {
    @POST("api/Tokens")
    Call<String> getToken(@Body UserDits userDits);

    @GET("api/Users/{username}")
    Call<CurrentUser> getCurrentUser(@Path("username") String username, @Header("Authorization") String token);

    @GET("api/Chats")
    Call<List<Contact>> getChatList(@Header("Authorization") String token);

    @POST("api/Chats")
    Call<Contact> addContact(@Header("Authorization") String token, @Body Username username);

    @POST("api/Users")
    Call<Void> register(@Body RegisterUser registerUser);

    @DELETE("api/Chats/{id}")
    Call<Void> deleteContact(@Path("id") String id, @Header("Authorization") String token);

    @GET("api/Chats/{id}/Messages")
    Call<List<Message>> getMessages(@Path("id") String id, @Header("Authorization") String token);

    @POST("api/Tokens/{username}")
    Call<Void> sendFirebaseToken(@Path("username") String username, @Header("Authorization") String token, @Body FirebaseToken firebaseToken);

    @POST("api/Chats/{id}/Messages")
    Call<Message> sendMessage(@Path("id") String id, @Header("Authorization") String token, @Body Msg msg);
}

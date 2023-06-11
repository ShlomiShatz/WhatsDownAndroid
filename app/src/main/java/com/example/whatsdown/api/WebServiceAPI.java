package com.example.whatsdown.api;

import com.example.whatsdown.CurrentUser;
import com.example.whatsdown.UserDits;

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

    @DELETE("posts/{id}")
    Call<Void> deletePost(@Path("id") int id);
}

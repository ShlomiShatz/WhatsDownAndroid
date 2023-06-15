package com.example.whatsdown.api;

import android.util.Log;

import com.example.whatsdown.CurrentUser;
import com.example.whatsdown.UserDits;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginAPI {
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;
    static String token;
    CurrentUser curUser;

    public static String getToken() {
        return token;
    }

    public CurrentUser getCurUser() {
        return curUser;
    }

    public LoginAPI() {
        // FOR DEBUGGING*****************************************************
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        // TILL HERE*************************************************************

        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.19:5000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)// FOR DEBUGGING********************************************************
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void post(UserDits userDits, PostCallback callback) {
        Call<String> call = webServiceAPI.getToken(userDits);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.code() == 200) {
                    token = "bearer " + response.body();
                    callback.onPostComplete(true);
                } else {
                    callback.onPostComplete(false);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                callback.onPostComplete(false);
            }
        });
    }

    public void get(String username, String tokenToSend, PostCallback callback) {
        Call<CurrentUser> call = webServiceAPI.getCurrentUser(username, tokenToSend);
        call.enqueue(new Callback<CurrentUser>() {
            @Override
            public void onResponse(Call<CurrentUser> call, Response<CurrentUser> response) {
                if(response.code() == 200) {
                    curUser = response.body();
                    callback.onPostComplete(true);
                } else {
                    callback.onPostComplete(false);
                }
            }

            @Override
            public void onFailure(Call<CurrentUser> call, Throwable t) {
                t.printStackTrace();
                callback.onPostComplete(false);
            }
        });
    }
}

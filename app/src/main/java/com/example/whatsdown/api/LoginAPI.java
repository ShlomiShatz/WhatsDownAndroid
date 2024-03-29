package com.example.whatsdown.api;

import com.example.whatsdown.objects.CurrentUser;
import com.example.whatsdown.objects.FirebaseToken;
import com.example.whatsdown.objects.UserDits;

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
        retrofit = new Retrofit.Builder()
                .baseUrl(ServerPath.getPath())
                .addConverterFactory(GsonConverterFactory.create())
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
        if (username != null) {
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

    public void sentFirebaseToken(String username, FirebaseToken firebaseToken, String userToken, PostCallback callback) {
        if (username != null) {
            Call<Void> call = webServiceAPI.sendFirebaseToken(username, userToken, firebaseToken);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(response.code() == 200) {
                        callback.onPostComplete(true);
                    } else {
                        callback.onPostComplete(false);
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    t.printStackTrace();
                    callback.onPostComplete(false);
                }
            });
        }
    }
}

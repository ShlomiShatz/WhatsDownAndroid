package com.example.whatsdown.api;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterAPI {
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;

    public RegisterAPI() {
        retrofit = new Retrofit.Builder()
                .baseUrl(ServerPath.getPath())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void post(RegisterUser registerUser, PostCallback callback) {
        Call<Void> call = webServiceAPI.register(registerUser);
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

package com.example.whatsdown.login;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whatsdown.chat.ChatActivity;
import com.example.whatsdown.objects.CurrentUser;
import com.example.whatsdown.R;
import com.example.whatsdown.objects.FirebaseToken;
import com.example.whatsdown.objects.UserDits;
import com.example.whatsdown.api.LoginAPI;
import com.example.whatsdown.api.PostCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.messaging.FirebaseMessaging;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.login_username);
        password = findViewById(R.id.login_password);

        TextView tvRegistration = findViewById(R.id.login_tvRegistration);
        tvRegistration.setOnClickListener(v -> {
            Intent i = new Intent(this, RegisterActivity.class);
            startActivity(i);
        });
    }

    private boolean validateUsername() {
        String value = username.getEditText().getText().toString();
        if (value.isEmpty()) {
            username.setError("Please insert username.");
            return false;
        } else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String value = password.getEditText().getText().toString();
        if (value.isEmpty()) {
            password.setError("Please insert password.");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    private void goToChats(CurrentUser user, String token) {
        Intent i = new Intent(this, ChatActivity.class);
        i.putExtra("CurrentUser", user);
        i.putExtra("Token", token);
        startActivity(i);
    }

    public void onSubmitLogin(View view) {
        boolean usr = validateUsername();
        boolean pwd = validatePassword();
        if (usr && pwd) {
            UserDits userDits = new UserDits(username.getEditText().getText().toString(), password.getEditText().getText().toString());
            LoginAPI lApi = new LoginAPI();
            lApi.post(userDits, new PostCallback() {
                @Override
                public void onPostComplete(boolean registered) {
                    if (registered) {
                        runOnUiThread(() -> {
                            username.setError(null);
                            username.setErrorEnabled(false);
                            password.setError(null);
                            password.setErrorEnabled(false);
                            String token = lApi.getToken();
                            lApi.get(userDits.getUsername(), token, new PostCallback() {
                                @Override
                                public void onPostComplete(boolean registered) {
                                    if (registered) {
                                        runOnUiThread(() -> {
                                            CurrentUser curUser = lApi.getCurUser();
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                                getAndSendFirebaseToken(lApi, userDits.getUsername(), token);
                                            }
                                            goToChats(curUser, token);
                                        });
                                    } else {
                                        runOnUiThread(() -> {
                                            password.setError(null);
                                            password.setErrorEnabled(false);
                                            password.getEditText().setText("");
                                            username.setError("Error occurred.");
                                        });
                                    }
                                }
                            });
                        });
                    } else {
                        runOnUiThread(() -> {
                            password.setError(null);
                            password.setErrorEnabled(false);
                            password.getEditText().setText("");
                            username.setError("Wrong username/password.");
                        });
                    }
                }
            });
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public void getAndSendFirebaseToken(LoginAPI lApi, String username, String userToken) {
        if (ActivityCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
            String[] perms = {Manifest.permission.POST_NOTIFICATIONS};
            ActivityCompat.requestPermissions(LoginActivity.this, perms, 1);
        }
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }

                        FirebaseToken firebaseToken = new FirebaseToken(task.getResult());
                        lApi.sentFirebaseToken(username, firebaseToken, userToken, new PostCallback() {
                            @Override
                            public void onPostComplete(boolean registered) {
                                if (!registered) {
                                    Toast.makeText(LoginActivity.this, "Notification service failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
    }
}
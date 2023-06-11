package com.example.whatsdown;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.whatsdown.api.LoginAPI;
import com.example.whatsdown.api.PostCallback;
import com.google.android.material.textfield.TextInputLayout;

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
                            String token = "bearer " + lApi.getToken();
                            lApi.get(userDits.getUsername(), token, new PostCallback() {
                                @Override
                                public void onPostComplete(boolean registered) {
                                    if (registered) {
                                        runOnUiThread(() -> {
                                            CurrentUser curUser = lApi.getCurUser();
                                            //Go to chats
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
}
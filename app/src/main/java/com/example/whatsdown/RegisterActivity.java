package com.example.whatsdown;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView tvGotoLogin = findViewById(R.id.register_tvLogin);
        tvGotoLogin.setOnClickListener(v -> {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
        });
    }
}
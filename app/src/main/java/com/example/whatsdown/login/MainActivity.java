package com.example.whatsdown.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.whatsdown.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnStart = findViewById(R.id.main_button);
        btnStart.setOnClickListener(v -> {
            Intent i = new Intent(this, LoginActivity.class);

            startActivity(i);
        });
    }
}
package com.example.whatsdown.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.whatsdown.Dao.LocalDatabase;
import com.example.whatsdown.R;
import com.example.whatsdown.api.ServerPath;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LocalDatabase.setContext(this);

        SharedPreferences sharedPreferences;
        sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);
        boolean nightMode = sharedPreferences.getBoolean("night", false);
        if (!nightMode){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        String serverPath = sharedPreferences.getString("path", "http://10.0.2.2:5000/");
        ServerPath.setPath(serverPath);

        Button btnStart = findViewById(R.id.main_button);
        btnStart.setOnClickListener(v -> {
            Intent i = new Intent(this, LoginActivity.class);

            startActivity(i);
        });
    }
}
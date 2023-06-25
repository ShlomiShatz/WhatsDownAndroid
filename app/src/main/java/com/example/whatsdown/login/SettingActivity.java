package com.example.whatsdown.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.whatsdown.R;
import com.example.whatsdown.api.ServerPath;
import com.google.android.material.textfield.TextInputLayout;


public class SettingActivity extends AppCompatActivity {
    boolean nightMode;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Switch switchMode = (Switch)findViewById(R.id.switch1);
        sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);
        nightMode = sharedPreferences.getBoolean("night", false);
        if (nightMode){
            switchMode.setChecked(true);
        }
        ((TextInputLayout)findViewById(R.id.portServerSetting)).getEditText().setText(ServerPath.getPath());

        Button exit = (Button)findViewById(R.id.exitSetting);
        exit.setOnClickListener(v -> finish());

        Button save = (Button)findViewById(R.id.saveSetting);
        save.setOnClickListener(v -> {
            EditText boxPort = ((TextInputLayout)findViewById(R.id.portServerSetting)).getEditText();
            String port = boxPort.getText().toString();
            String prevPath = ServerPath.getPath();
            if (!port.isEmpty()){
                ServerPath.setPath(port);
            }
            nightMode = switchMode.isChecked();
            if (!nightMode){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                sharedPreferences.edit().putBoolean("night", false).apply();
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                sharedPreferences.edit().putBoolean("night", true).apply();
            }
            Toast.makeText(this, "Settings saved!", Toast.LENGTH_SHORT).show();
        });
    }
}
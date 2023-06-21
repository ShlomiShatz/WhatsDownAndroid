package com.example.whatsdown;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.google.android.material.textfield.TextInputLayout;


public class SettingActivity extends AppCompatActivity {
    String port;
    boolean mode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Button exit = (Button)findViewById(R.id.exitSetting);
        exit.setOnClickListener(v -> finish());

        Button save = (Button)findViewById(R.id.saveSetting);
        save.setOnClickListener(v -> {

            EditText boxPort = ((TextInputLayout)findViewById(R.id.portServerSetting)).getEditText();
            port = boxPort.getText().toString();
            Switch switchMode = (Switch)findViewById(R.id.switch1);
            mode = switchMode.isChecked();
        });
    }
}
package com.example.whatsdown;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

public class AddContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        Button exit = findViewById(R.id.exitAddContact);
        exit.setOnClickListener(v -> finish());

        Button save = findViewById(R.id.saveContact);
        save.setOnClickListener(v -> {

        });
    }
}
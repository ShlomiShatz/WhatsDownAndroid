package com.example.whatsdown;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.example.whatsdown.repositories.ContactRepository;

public class AddContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        ContactViewModel contactViewModel = new ContactViewModel();
        Button exit = findViewById(R.id.exitAddContact);
        exit.setOnClickListener(v -> finish());

        Button save = findViewById(R.id.saveContact);
        save.setOnClickListener(v -> {
            String userName = ((TextView)findViewById(R.id.userName_addContact)).getText().toString();
            if (!userName.isEmpty()){
                contactViewModel.add(userName);
            } else{

            }
        });
    }
}
package com.example.whatsdown.contact;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

import com.example.whatsdown.R;
import com.example.whatsdown.view_model.ContactViewModel;
import com.google.android.material.textfield.TextInputLayout;


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
            TextInputLayout text = findViewById(R.id.userName_addContact);
            String userName = text.getEditText().getText().toString();
            if (!userName.isEmpty()) {
                contactViewModel.add(userName, new ContactViewModel.AddContactCallback() {
                    @Override
                    public void onContactAdded(boolean success) {
                        runOnUiThread(() -> {
                            if (success) {
                                Toast.makeText(AddContactActivity.this, "User added successfully!", Toast.LENGTH_LONG).show();
                                text.setError(null);
                            } else {
                                text.setError("Invalid username.");
                            }
                        });
                    }
                });
            } else {
                text.setError("Please enter username.");
            }
        });
        }
    }
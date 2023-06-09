package com.example.whatsdown;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
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

        ImageView ivUploadedImage = findViewById(R.id.register_ivImageUploaded);
        Button btnUploadImage = findViewById(R.id.register_btnUploadImage);

        btnUploadImage.setOnClickListener(v -> {
            Intent iUpload = new Intent(Intent.ACTION_PICK);
            iUpload.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        });
    }
}
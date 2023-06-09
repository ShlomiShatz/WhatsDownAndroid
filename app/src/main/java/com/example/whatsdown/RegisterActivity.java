package com.example.whatsdown;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {

    private final int GALLERY_REQ_CODE = 1000;
    TextInputLayout username, password, verifyPassword, displayName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.register_etUsername);
        password = findViewById(R.id.register_etPassword);
        verifyPassword = findViewById(R.id.register_etVerifyPassword);
        displayName = findViewById(R.id.register_etDisplayName);


        TextView tvGotoLogin = findViewById(R.id.register_tvLogin);
        tvGotoLogin.setOnClickListener(v -> {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
        });

        Button btnUploadImage = findViewById(R.id.register_btnUploadImage);

        btnUploadImage.setOnClickListener(v -> {
            Intent iUpload = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(iUpload, 3);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            ImageView ivUploadedImage = findViewById(R.id.register_ivImageUploaded);
            ivUploadedImage.setImageURI(selectedImage);
        }
    }



    private boolean validateUsername() {
        String regExp = "^[A-Za-z0-9]*$";
        String value = username.getEditText().getText().toString();
        if (value.isEmpty()) {
            username.setError("Please insert username.");
            return false;
        } else if(!value.matches(regExp)) {
            username.setError("Username must contain letters and numbers only.");
            return false;
        } else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String regExp = "^(?=.*?[A-Za-z])(?=.*?[0-9])(?=.*?[!@#$.]).{8,}$";
        String value = password.getEditText().getText().toString();
        if (value.isEmpty()) {
            password.setError("Please insert password.");
            return false;
        } else if(!value.matches(regExp)) {
            password.setError("Password must be 8 characters or more, contain lower case letters, upper case letters, numbers and at least one of these symbols: .!@#$");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePasswordVerify() {
        String value = verifyPassword.getEditText().getText().toString();
        if (value.isEmpty()) {
            verifyPassword.setError("Please insert the password again.");
            return false;
        } else if(!value.equals(password.getEditText().getText().toString())) {
            verifyPassword.setError("Passwords must match.");
            return false;
        } else {
            verifyPassword.setError(null);
            verifyPassword.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateDisplayName() {
        String value = displayName.getEditText().getText().toString();
        if (value.isEmpty()) {
            displayName.setError("Please insert display name.");
            return false;
        } else {
            displayName.setError(null);
            displayName.setErrorEnabled(false);
            return true;
        }
    }

    public void onSubmitRegister(View view) {
        boolean usr = validateUsername();
        boolean pwd = validatePassword();
        boolean pwdvrf = validatePasswordVerify();
        boolean dsp = validateDisplayName();
        if (!usr || !pwd || !pwdvrf || !dsp) return;
    }
}
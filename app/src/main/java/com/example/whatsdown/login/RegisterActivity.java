package com.example.whatsdown.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whatsdown.R;
import com.example.whatsdown.api.RegisterAPI;
import com.example.whatsdown.api.RegisterUser;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;

public class RegisterActivity extends AppCompatActivity {

    TextInputLayout username, password, verifyPassword, displayName;
    ImageView profilePic;
    MaterialButton btnUpload;
    Uri selectedImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.register_etUsername);
        password = findViewById(R.id.register_etPassword);
        verifyPassword = findViewById(R.id.register_etVerifyPassword);
        displayName = findViewById(R.id.register_etDisplayName);
        profilePic = findViewById(R.id.register_ivImageUploaded);
        btnUpload = findViewById(R.id.register_btnUploadImage);

        TextView tvGotoLogin = findViewById(R.id.register_tvLogin);
        tvGotoLogin.setOnClickListener(v -> moveToLogin());

        btnUpload.setOnClickListener(v -> {
            Intent iUpload = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(iUpload, 3);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            selectedImage = data.getData();
            profilePic.setImageURI(selectedImage);
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

    private boolean validateImageUploaded() {
        if(profilePic.getDrawable() == null) {
            btnUpload.setError("Please upload profile picture.");
            Toast toast = Toast.makeText(this, "Please upload profile picture.", Toast.LENGTH_LONG);
            toast.show();
            return false;
        } else {
            btnUpload.setError(null);
            return true;
        }
    }

    public String getImageFormatFromUri(Context context, Uri imageUri) {
        ContentResolver contentResolver = context.getContentResolver();
        String mimeType = contentResolver.getType(imageUri);

        if (mimeType != null && mimeType.startsWith("image/")) {
            String fileExtension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);
            if (fileExtension != null) {
                return fileExtension.toLowerCase();
            }
        }
        return null;
    }

    private String convertImageToBase64() {
        Drawable drawable = profilePic.getDrawable();
        byte[] byteArray = null;
        String type = "";
        Bitmap bitmap;
        Uri imageUri = Uri.parse(selectedImage.toString());
        String fileExtension = getImageFormatFromUri(this, imageUri);

        switch (fileExtension) {
            case "png": {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byteArray = byteArrayOutputStream.toByteArray();
                type = "data:image/png;base64,";
                break;
            }
            case "jpg":
            case "jpeg": {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byteArray = byteArrayOutputStream.toByteArray();
                type = "data:image/jpeg;base64,";
                break;
            }
            case "gif":
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawable.draw(canvas);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                byteArray = outputStream.toByteArray();
                type = "data:image/gif;base64,";
                try {
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                btnUpload.setError("The file needs to be an image (formats: jpeg/jpg/png/gif)");
                Toast toast = Toast.makeText(this,
                        "The file needs to be an image (formats: jpeg/jpg/png/gif)",
                        Toast.LENGTH_LONG);
                toast.show();
                break;
        }
        if (byteArray != null) {
            String encoding = Base64.encodeToString(byteArray, Base64.DEFAULT);
            encoding = type + encoding;
            return encoding;
        }
        return null;
    }

    private void moveToLogin() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

    public void onSubmitRegister(View view) {
        boolean usr = validateUsername();
        boolean pwd = validatePassword();
        boolean pwdvrf = validatePasswordVerify();
        boolean dsp = validateDisplayName();
        boolean img = validateImageUploaded();
        if (usr && pwd && pwdvrf && dsp && img) {
            String image64 = convertImageToBase64();
            if (image64 == null) {
                return;
            }
            RegisterUser registerUser = new RegisterUser(username.getEditText().getText().toString(),
                    password.getEditText().getText().toString(),
                    displayName.getEditText().getText().toString(), image64);
            RegisterAPI rApi = new RegisterAPI();
            rApi.post(registerUser, registered -> {
                if (registered) {
                    runOnUiThread(() -> {
                        username.setError(null);
                        username.setErrorEnabled(false);
                        password.setError(null);
                        password.setErrorEnabled(false);
                        verifyPassword.setError(null);
                        verifyPassword.setErrorEnabled(false);
                        displayName.setError(null);
                        displayName.setErrorEnabled(false);
                        Toast toast = Toast.makeText(this,
                                "Register successful!",
                                Toast.LENGTH_LONG);
                        toast.show();
                        moveToLogin();
                    });
                } else {
                    runOnUiThread(() -> {
                        password.setError(null);
                        password.setErrorEnabled(false);
                        password.getEditText().setText("");
                        verifyPassword.setError(null);
                        verifyPassword.setErrorEnabled(false);
                        verifyPassword.getEditText().setText("");
                        username.setError("Username already taken.");
                    });
                }
            });
        }
    }
}
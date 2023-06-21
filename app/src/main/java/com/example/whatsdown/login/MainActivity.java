package com.example.whatsdown.login;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.whatsdown.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            String[] perms = {Manifest.permission.POST_NOTIFICATIONS};
            ActivityCompat.requestPermissions(MainActivity.this, perms, 1);
        }

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        //d3b26vjQfPc:APA91bH8l9t8Akak4zQZ3LBsG_yyHr0mltBsaYv1SHdTgwaFiHNgCZttKGW11XPB98-teHgBuX-phCMNoe7P44ShhO9_8Z_lbklGIPY-s7gaEchVQUhETM-CO1BLiFuBg6-HHyzHoIhd
                    }
                });

        Button btnStart = findViewById(R.id.main_button);
        btnStart.setOnClickListener(v -> {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
        });

    }
}
package com.example.whatsdown;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.Manifest;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.whatsdown.chat.ChatViewActivity;
import com.example.whatsdown.contact.ContactListActivity;

import com.example.whatsdown.view_model.ChatViewModel;
import com.example.whatsdown.view_model.ContactViewModel;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class PushNotifications extends FirebaseMessagingService {
    public PushNotifications() {
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        if (remoteMessage.getNotification() != null) {
            String messageReceived = remoteMessage.getNotification().getBody();
            char type = messageReceived.charAt(0);
            switch (type) {
                case 'm':
                case 'a':
                    messageReceived = messageReceived.substring(1);
                    createNotificationChannel();
                    Intent intent = new Intent(this, ContactListActivity.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
                            .setSmallIcon(R.mipmap.ic_launcher_foreground)
                            .setContentTitle(remoteMessage.getNotification().getTitle())
                            .setContentText(messageReceived)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true);

                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                            != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Received message. Please allow notifications", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                    notificationManager.notify(1, builder.build());
                    if (ContactViewModel.getIdsChatRepository() != null) {
                        ContactViewModel.getIdsChatRepository().reload();
                    }
                    if (ChatViewActivity.getMessageViewModel() != null && ChatViewModel.getChatIdString() != null) {
                        ChatViewActivity.getMessageViewModel().reload();
                    }
                    break;
                case 'd':
                    if (ContactViewModel.getIdsChatRepository() != null) {
                        ContactViewModel.getIdsChatRepository().reload();
                    }
                    if (ChatViewActivity.getMessageViewModel() != null && ChatViewModel.getChatIdString() != null) {
                        ChatViewActivity.getMessageViewModel().reload();
                    }
                    break;
            }
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("1", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


}
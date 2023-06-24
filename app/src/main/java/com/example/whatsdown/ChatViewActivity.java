package com.example.whatsdown;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.whatsdown.adapters.MessagesListAdapter;
import com.example.whatsdown.objects.CurrentUser;
import com.example.whatsdown.view_model.ChatViewModel;
import com.example.whatsdown.view_model.MessageViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class ChatViewActivity extends AppCompatActivity {
    private ChatViewModel viewModel;
    private MessageViewModel messageViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_view);
        viewModel = new ViewModelProvider(this).get(ChatViewModel.class);
        ImageButton back = findViewById(R.id.back_activity);
        back.setOnClickListener(v -> {
            viewModel.setChatId("");
            finish();
        });
        RecyclerView listMessages = findViewById(R.id.allMsg_activity);
        final MessagesListAdapter messagesListAdapter = new MessagesListAdapter(this,viewModel.getCurrentUser().getDisplayName());
        listMessages.setAdapter(messagesListAdapter);
        listMessages.setLayoutManager(new LinearLayoutManager(this));

        messageViewModel = new ViewModelProvider(this).get(MessageViewModel.class);
        messageViewModel.get().observe(this, messages -> {
            messagesListAdapter.setMessages(messages);
            scrollDown(messagesListAdapter);
        });
        ImageView img = findViewById(R.id.imgUser_chatView_activity);
        img.setImageBitmap(viewModel.getCurrentUser().getProfilePic());
        TextView name = findViewById(R.id.username_chatView_activity);
        name.setText(viewModel.getCurrentUser().getDisplayName());
        String chatId = viewModel.getChatId().getValue();

        ImageButton send = findViewById(R.id.send_activity);
        send.setOnClickListener(v -> {
            scrollDown(messagesListAdapter);
            TextInputEditText input = findViewById(R.id.userMsgInput_activity);
            String msg = "";
            if (input != null){
                msg = input.getText().toString().trim();
                messageViewModel.add(msg);
            }
            if (!msg.isEmpty()){
                input.setText("");
            }

        });

    }
    private void scrollDown(MessagesListAdapter messagesListAdapter ){
        RecyclerView recyclerView = findViewById(R.id.allMsg_activity); // Replace with your RecyclerView ID
        recyclerView.post(() -> recyclerView.scrollToPosition(messagesListAdapter.getItemCount() - 1));
    }
}
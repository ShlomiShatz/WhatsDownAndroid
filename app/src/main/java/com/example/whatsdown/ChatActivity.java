package com.example.whatsdown;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

public class ChatActivity extends AppCompatActivity {

    private ChatViewModel viewModel;
    private CurrentUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        currentUser = (CurrentUser) i.getSerializableExtra("CurrentUser");
        setContentView(R.layout.activity_chat);
        viewModel = new ViewModelProvider(this).get(ChatViewModel.class);
        viewModel.getChatId().observe(this, chat -> {
            if (chat == ""){
                changeFragment(new ContactListFragment(currentUser));
            } else {
                changeFragment(new ChatViewFragment());
            }
        });
        changeFragment(new ContactListFragment(currentUser));
//        changeFragment(new ChatViewFragment());
    }

    private void changeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainFrame , fragment);
        fragmentTransaction.commit();

    }
}
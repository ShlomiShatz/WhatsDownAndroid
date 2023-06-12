package com.example.whatsdown;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

public class ChatActivity extends AppCompatActivity {

    private ChatViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        viewModel = new ViewModelProvider(this).get(ChatViewModel.class);
        viewModel.getChatId().observe(this, chat -> {
            if (chat == null){
                changeFragment(new ContactListFragment());
            } else {
                changeFragment(new ChatViewFragment());
            }
        });
        changeFragment(new ContactListFragment());
    }

    private void changeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainFrame , fragment);
        fragmentTransaction.commit();

    }
}
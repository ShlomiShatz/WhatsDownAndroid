package com.example.whatsdown.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.example.whatsdown.Dao.LocalDatabase;
import com.example.whatsdown.contact.ContactListFragment;
import com.example.whatsdown.view_model.ContactViewModel;
import com.example.whatsdown.objects.CurrentUser;
import com.example.whatsdown.R;
import com.example.whatsdown.view_model.ChatViewModel;

public class ChatActivity extends AppCompatActivity {

    private ChatViewModel viewModel;
    private static CurrentUser currentUser;
    private String token;
    private ContactViewModel contactViewModel;


    public static CurrentUser getCurrentUser() {
        return currentUser;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalDatabase.setContext(this);
        Intent i = getIntent();
        currentUser = (CurrentUser) i.getSerializableExtra("CurrentUser");
        token = i.getStringExtra("Token");
        setContentView(R.layout.activity_chat);
        viewModel = new ViewModelProvider(this).get(ChatViewModel.class);
        viewModel.setToken(token);

        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        contactViewModel.setToken(token);

        viewModel.getChatId().observe(this, chat -> {
            if (chat.equals("")){
                changeFragment(new ContactListFragment(currentUser, token));
            } else {
                changeFragment(new ChatViewFragment());
            }
        });
        changeFragment(new ContactListFragment(currentUser, token));
//        changeFragment(new ChatViewFragment());
    }

    private void changeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainFrame , fragment);
        fragmentTransaction.commit();

    }
}
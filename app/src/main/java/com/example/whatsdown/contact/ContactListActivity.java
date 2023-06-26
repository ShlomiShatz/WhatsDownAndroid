package com.example.whatsdown.contact;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.whatsdown.R;
import com.example.whatsdown.login.SettingActivity;
import com.example.whatsdown.adapters.ContactsListAdapter;
import com.example.whatsdown.api.ServerPath;
import com.example.whatsdown.chat.ChatViewActivity;
import com.example.whatsdown.objects.CurrentUser;
import com.example.whatsdown.view_model.ChatViewModel;
import com.example.whatsdown.view_model.ContactViewModel;

public class ContactListActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    private CurrentUser currentUser;
    private ChatViewModel viewModel;
    private ContactViewModel contactViewModel;
    private String token;

    private String pathServer = ServerPath.getPath();

    private ContactsListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        Intent i = getIntent();
        currentUser = ChatViewModel.getLoginUser();
        token = i.getStringExtra("Token");
        TextView tv = findViewById(R.id.contact_list_username_activity);
        tv.setText(currentUser.getDisplayName());
        ImageView im = findViewById(R.id.contact_list_image_activity);
        im.setImageBitmap(currentUser.getProfilePic());
        viewModel = new ViewModelProvider(this).get(ChatViewModel.class);
        viewModel.setToken(token);
        RecyclerView listContacts = findViewById(R.id.contacts_activity);
        adapter = new ContactsListAdapter(this);
        adapter.setOnItemClickListener(position -> {
            viewModel.setChatId(adapter.getContacts().get(position).getId());
            viewModel.setCurrentUser(adapter.getContacts().get(position).getUser());
            Intent intent = new Intent(this, ChatViewActivity.class);
            startActivity(intent);
        });

        ImageButton btnMenu = findViewById(R.id.menu_contact_list_activity);
        btnMenu.setOnClickListener(v -> {
            PopupMenu menu = new PopupMenu(this, v);
            menu.setOnMenuItemClickListener(this);
            menu.inflate(R.menu.user_menu);
            menu.show();

        });
        listContacts.setAdapter(adapter);
        listContacts.setLayoutManager(new LinearLayoutManager(this));
        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        contactViewModel.get().observe(this , contacts -> adapter.setContacts(contacts));

        SwipeRefreshLayout refreshLayout = findViewById(R.id.refreshLayout_activity);
        refreshLayout.setOnRefreshListener(()->{
            contactViewModel.reload();
         });

    }
    @Override
    public void onResume() {
        super.onResume();
        if (!pathServer.equals(ServerPath.getPath())){
            // add deletions?
            finish();
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item){
        int itemId = item.getItemId();
        if (itemId == R.id.addContact_menu) {
            Intent i = new Intent(this, AddContactActivity.class);
            startActivity(i);
            return true;
        } else if (itemId == R.id.logout_menu) {
            contactViewModel.deleteFirebaseToken();
            SharedPreferences sharedPreferences;
            sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);
            sharedPreferences.edit().putString("usernameLogin", "").apply();
            this.finish();
            return true;
        } else if (itemId == R.id.setting_menu) {
            pathServer = ServerPath.getPath();
            Intent i = new Intent(this, SettingActivity.class);
            startActivity(i);
            return true;
        } else {
            return false;
        }
    }
}
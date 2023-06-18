package com.example.whatsdown;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import android.widget.ImageButton;

import android.widget.PopupMenu;

import android.widget.ImageView;
import android.widget.TextView;

import com.example.whatsdown.adapters.ConstactsListAdapter;



public class ContactListFragment extends Fragment implements PopupMenu.OnMenuItemClickListener {

    View view;
    CurrentUser currentUser;
    private ChatViewModel viewModel;
    private ContactViewModel contactViewModel;
    private String token;

    private ConstactsListAdapter adapter;
    public ContactListFragment(CurrentUser currentUser, String token) {
        this.currentUser = currentUser;
        this.token = token;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_contact_list, container, false);
        TextView tv = view.findViewById(R.id.contact_list_username);
        tv.setText(currentUser.getDisplayName());
        ImageView im = view.findViewById(R.id.contact_list_image);
        im.setImageBitmap(currentUser.getProfilePic());
        viewModel = new ViewModelProvider(requireActivity()).get(ChatViewModel.class);
        viewModel.setToken(token);
        RecyclerView listContacts = view.findViewById(R.id.contacts);
        adapter = new ConstactsListAdapter(this);
        adapter.setOnItemClickListener(position -> {
            viewModel.setChatId(adapter.getContacts().get(position).getId());
            viewModel.setCurrentUser(adapter.getContacts().get(position).getUser());
        });



        ImageButton btnMenu = view.findViewById(R.id.menu_contact_list);
        btnMenu.setOnClickListener(v -> {
            PopupMenu menu = new PopupMenu(this.getContext(), v);
            menu.setOnMenuItemClickListener(this);
            menu.inflate(R.menu.user_menu);
            menu.show();

        });
        listContacts.setAdapter(adapter);
        listContacts.setLayoutManager(new LinearLayoutManager(this.getContext()));
        contactViewModel = new ViewModelProvider(requireActivity()).get(ContactViewModel.class);
        contactViewModel.get().observe(getViewLifecycleOwner() , contacts -> adapter.setContacts(contacts));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        contactViewModel.reload();
        contactViewModel.get().observe(getViewLifecycleOwner() , contacts -> adapter.setContacts(contacts));
    }

    @Override
    public boolean onMenuItemClick(MenuItem item){
        int itemId = item.getItemId();
        if (itemId == R.id.addContact_menu) {
            Intent i = new Intent(requireActivity(), AddContactActivity.class);
            startActivity(i);
            return true;
        } else if (itemId == R.id.logout_menu) {
            requireActivity().finish();
            return true;
        } else if (itemId == R.id.setting_menu) {
            Intent i = new Intent(requireActivity(), SettingActivity.class);
            startActivity(i);
            return true;
        } else {
            return false;
        }
    }

}
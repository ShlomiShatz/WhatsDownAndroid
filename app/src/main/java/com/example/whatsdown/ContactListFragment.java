package com.example.whatsdown;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.whatsdown.adapters.ConstactsListAdapter;

public class ContactListFragment extends Fragment {
    View view;
    CurrentUser currentUser;
    private ChatViewModel viewModel;
    private ContactViewModel contactViewModel;
    private String token;

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
        final ConstactsListAdapter adapter = new ConstactsListAdapter(this);
        adapter.setOnItemClickListener(new ConstactsListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                viewModel.setChatId(adapter.getContacts().get(position).getId());
            }
        });
        listContacts.setAdapter(adapter);
        listContacts.setLayoutManager(new LinearLayoutManager(this.getContext()));
        contactViewModel = new ViewModelProvider(requireActivity()).get(ContactViewModel.class);
        contactViewModel.get().observe(getViewLifecycleOwner() , contacts -> adapter.setContacts(contacts));

        return view;
    }

}
package com.example.whatsdown;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.whatsdown.adapters.ConstactsListAdapter;

import java.util.ArrayList;
import java.util.List;


public class ContactListFragment extends Fragment {
    View view;
    private ChatViewModel viewModel;
    private ContactViewModel contactViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_contact_list, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(ChatViewModel.class);
        RecyclerView listContacts = view.findViewById(R.id.contacts);
        final ConstactsListAdapter adapter = new ConstactsListAdapter(this);
        adapter.setOnItemClickListener(new ConstactsListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                viewModel.setChatId("55555");
            }
        });
        listContacts.setAdapter(adapter);
        listContacts.setLayoutManager(new LinearLayoutManager(this.getContext()));
        contactViewModel = new ViewModelProvider(requireActivity()).get(ContactViewModel.class);
        contactViewModel.get().observe(getViewLifecycleOwner() , contacts -> adapter.setContacts(contacts));





        return view;
    }

}
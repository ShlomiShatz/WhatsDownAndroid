package com.example.whatsdown;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.whatsdown.adapters.ConstactsListAdapter;
import com.example.whatsdown.adapters.MessagesListAdapter;
import com.example.whatsdown.view_model.MessageViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ChatViewFragment extends Fragment {

    View view;
    private ChatViewModel viewModel;
    private MessageViewModel messageViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chat_view, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(ChatViewModel.class);
        ImageButton back = (ImageButton)view.findViewById(R.id.back);
        back.setOnClickListener(v -> viewModel.setChatId(""));
        RecyclerView listMessages = view.findViewById(R.id.allMsg);
        final MessagesListAdapter messagesListAdapter = new MessagesListAdapter(this);
        listMessages.setAdapter(messagesListAdapter);
        listMessages.setLayoutManager(new LinearLayoutManager(this.getContext()));

        messageViewModel = new ViewModelProvider(requireActivity()).get(MessageViewModel.class);
        messageViewModel.get().observe(getViewLifecycleOwner() , messages -> messagesListAdapter.setMessages(messages));

        String chatId = viewModel.getChatId().toString();

        //get all massages

        //put in the right place in the fragment

        ImageButton send = (ImageButton)view.findViewById(R.id.send);
        send.setOnClickListener(v -> {
            TextInputEditText input = view.findViewById(R.id.userMsgInput);
            String msg = "";
            if (input != null){
                msg = input.getText().toString().trim();
            }
            if (!msg.isEmpty()){
                input.setText("");
            }

        });

        return view;

    }
}
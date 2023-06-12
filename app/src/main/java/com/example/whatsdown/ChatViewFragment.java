package com.example.whatsdown;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

public class ChatViewFragment extends Fragment {

    View view;
    private ChatViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_chat_view, container, false);
        viewModel = new ViewModelProvider(this).get(ChatViewModel.class);
        Button back = view.findViewById(R.id.back);
        back.setOnClickListener(v -> viewModel.setChatId(null));

        String chatId = viewModel.getChatId().toString();

        //get all massages

        //put in the right place in the fragment

        Button send = view.findViewById(R.id.send);
        send.setOnClickListener(v -> {
            TextInputLayout input = view.findViewById(R.id.userMsgInput);
            String msg = "";
            if (input.getEditText() != null){
                msg = input.getEditText().getText().toString().trim();
            }
            if (!msg.isEmpty()){
                input.getEditText().setText("");
            }
            //add massage to the chat
        });

        return view;

    }
}
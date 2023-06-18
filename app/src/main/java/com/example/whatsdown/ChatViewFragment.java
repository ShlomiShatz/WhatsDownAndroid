package com.example.whatsdown;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whatsdown.adapters.MessagesListAdapter;
import com.example.whatsdown.api.ChatsAPI;
import com.example.whatsdown.api.LoginAPI;
import com.example.whatsdown.api.PostCallback;
import com.example.whatsdown.view_model.MessageViewModel;
import com.google.android.material.textfield.TextInputEditText;


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
        final MessagesListAdapter messagesListAdapter = new MessagesListAdapter(this,viewModel.getCurrentUser().getDisplayName());
        listMessages.setAdapter(messagesListAdapter);
        listMessages.setLayoutManager(new LinearLayoutManager(this.getContext()));

        messageViewModel = new ViewModelProvider(requireActivity()).get(MessageViewModel.class);
        messageViewModel.get().observe(getViewLifecycleOwner(), messages -> {
            messagesListAdapter.setMessages(messages);
            scrollDown(view, messagesListAdapter);
        });
        ImageView img = view.findViewById(R.id.imgUser_chatView);
        img.setImageBitmap(viewModel.getCurrentUser().getProfilePic());
        TextView name = view.findViewById(R.id.username_chatView);
        name.setText(viewModel.getCurrentUser().getDisplayName());
        String chatId = viewModel.getChatId().getValue();

        ImageButton send = (ImageButton)view.findViewById(R.id.send);
        send.setOnClickListener(v -> {
            scrollDown(view,messagesListAdapter);
            TextInputEditText input = view.findViewById(R.id.userMsgInput);
            String msg = "";
            if (input != null){
                msg = input.getText().toString().trim();
                Msg msgSend = new Msg(msg);
                ChatsAPI api = new ChatsAPI();
                api.sendMessage(chatId, LoginAPI.getToken(),msgSend,new PostCallback() {
                    @Override
                    public void onPostComplete(boolean registered) {
                        if (registered) {
                            messageViewModel.get().observe(getViewLifecycleOwner(), messages -> {
                                messagesListAdapter.setMessages(messages);
                                scrollDown(view, messagesListAdapter);
                            });
                        } else {
                            Toast.makeText(view.getContext(), "error occur",Toast.LENGTH_SHORT).show();
                        }
                    }

                });
            }
            if (!msg.isEmpty()){
                input.setText("");
            }

        });

        return view;

    }

    private void scrollDown(View view,MessagesListAdapter messagesListAdapter ){
        RecyclerView recyclerView = view.findViewById(R.id.allMsg); // Replace with your RecyclerView ID
        recyclerView.post(() -> recyclerView.scrollToPosition(messagesListAdapter.getItemCount()-1));
    }
}
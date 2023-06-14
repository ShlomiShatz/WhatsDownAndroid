package com.example.whatsdown.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsdown.ChatViewFragment;
import com.example.whatsdown.ChatViewModel;
import com.example.whatsdown.ContactListFragment;
import com.example.whatsdown.Message;
import com.example.whatsdown.R;

import java.util.List;

public class MessagesListAdapter extends RecyclerView.Adapter<MessagesListAdapter.MessageViewHolder>{


    class MessageViewHolder extends  RecyclerView.ViewHolder {
        private final TextView sender;
        private final TextView content;
        private final TextView time;

        private MessageViewHolder(View itemView){
            super(itemView);
            sender = itemView.findViewById(R.id.sender);
            content = itemView.findViewById(R.id.content);
            time = itemView.findViewById(R.id.time);
        }

    }
    private final LayoutInflater mInflater;
    private List<Message> messages;

    public MessagesListAdapter(ChatViewFragment context) {
        mInflater = LayoutInflater.from(context.getContext());
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = mInflater.inflate(R.layout.message_box_layout, parent, false);
        return new MessageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position){
        if (messages != null){
            final Message current = messages.get(position);
            holder.sender.setText(current.getSender());
            holder.content.setText(current.getContent());
            holder.time.setText(current.getTime());
        }

    }
    public void setMessages(List<Message> listMessages){
        messages = listMessages;
        notifyDataSetChanged();
    }

    public List<Message> getMessages() {
        return messages;
    }
    @Override
    public int getItemCount() {
        if (messages != null)
            return messages.size();
        else return 0;
    }

}

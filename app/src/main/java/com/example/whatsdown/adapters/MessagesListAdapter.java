package com.example.whatsdown.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsdown.chat.ChatViewFragment;
import com.example.whatsdown.objects.Message;
import com.example.whatsdown.R;

import java.util.List;

public class MessagesListAdapter extends RecyclerView.Adapter {


    class ReceiveViewHolder extends RecyclerView.ViewHolder {
        private final TextView sender;
        private final TextView content;
        private final TextView time;

        private ReceiveViewHolder(View itemView) {
            super(itemView);
            sender = itemView.findViewById(R.id.sender);
            content = itemView.findViewById(R.id.content);
            time = itemView.findViewById(R.id.time);
        }
    }

    class SenderViewHolder extends RecyclerView.ViewHolder {
        private final TextView sender;
        private final TextView content;
        private final TextView time;

        private SenderViewHolder(View itemView) {
            super(itemView);
            sender = itemView.findViewById(R.id.sender_right);
            content = itemView.findViewById(R.id.content_right);
            time = itemView.findViewById(R.id.time_right);
        }
    }

    private final LayoutInflater mInflater;
    private List<Message> messages;

    private String sendUser;

    int sender_type = 1;
    int receiver_type = 2;

    public MessagesListAdapter(ChatViewFragment context, String sender) {
        mInflater = LayoutInflater.from(context.getContext());
        sendUser = sender;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (viewType == receiver_type) {
            itemView = mInflater.inflate(R.layout.message_box_layout, parent, false);
            return new ReceiveViewHolder(itemView);
        } else {
            itemView = mInflater.inflate(R.layout.message_box_layout_right, parent, false);
            return new SenderViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getClass() == SenderViewHolder.class) {
            if (messages != null) {
                final Message current = messages.get(position);
                ((SenderViewHolder) holder).sender.setText(current.getSender().getDisplayName());
                ((SenderViewHolder) holder).content.setText(current.getContent());
                ((SenderViewHolder) holder).time.setText(current.getCreated());
            }
        }

        if (holder.getClass() == ReceiveViewHolder.class) {
            if (messages != null) {
                final Message current = messages.get(position);
                ((ReceiveViewHolder) holder).sender.setText(current.getSender().getDisplayName());
                ((ReceiveViewHolder) holder).content.setText(current.getContent());
                ((ReceiveViewHolder) holder).time.setText(current.getCreated());
            }
        }
    }

    public void setMessages(List<Message> listMessages) {
        messages = listMessages;
        notifyDataSetChanged();
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setSendUser(String sendUser) {
        this.sendUser = sendUser;
    }

    @Override
    public int getItemCount() {
        if (messages != null)
            return messages.size();
        else return 0;
    }

    public int getItemViewType(int position) {
        if (messages.get(position).getSender().getDisplayName().equals(sendUser)) {
            return sender_type;
        } else {
            return receiver_type;
        }
    }
}

package com.example.whatsdown.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsdown.Contact;
import com.example.whatsdown.ContactListFragment;
import com.example.whatsdown.R;

import java.util.List;

public class ConstactsListAdapter extends RecyclerView.Adapter<ConstactsListAdapter.ContactViewHolder> {

    class ContactViewHolder extends  RecyclerView.ViewHolder {
        private final TextView contactName;
        private final TextView lastMsg;
        private final TextView time;
        private final ImageView contactImg;

        private  ContactViewHolder(View itemView){
            super(itemView);
            contactName = itemView.findViewById(R.id.nameContact);
            lastMsg = itemView.findViewById(R.id.lastMessage);
            time = itemView.findViewById(R.id.time);
            contactImg = itemView.findViewById(R.id.imageContact);
        }
    }

    private final LayoutInflater mInflater;
    private List<Contact> contacts;

    public ConstactsListAdapter(ContactListFragment context) {
        mInflater = LayoutInflater.from(context.getContext());
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = mInflater.inflate(R.layout.contact_box_layout, parent, false);
        return new ContactViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position){
        if (contacts != null){
            final Contact current = contacts.get(position);
            holder.contactName.setText(current.getName());
            holder.lastMsg.setText(current.getLastMessage());
            holder.time.setText(current.getWhenLastMessage());
            holder.contactImg.setImageResource(current.getProfileImage());
        }
    }

    public void setContacts(List<Contact> listContacts){
        contacts = listContacts;
        notifyDataSetChanged();
    }

    public List<Contact> getContacts() {
        return contacts;
    }
    @Override
    public int getItemCount() {
        if (contacts != null)
            return contacts.size();
        else return 0;
    }
}

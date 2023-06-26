package com.example.whatsdown.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.example.whatsdown.contact.Contact;
import com.example.whatsdown.R;
import com.example.whatsdown.repositories.ContactRepository;
import java.util.List;

public class ContactsListAdapter extends RecyclerView.Adapter<ContactsListAdapter.ContactViewHolder> {
    //can i use this
    private OnItemClickListener onItemClickListener;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
    class ContactViewHolder extends  RecyclerView.ViewHolder {
        private final TextView contactName;
        private final TextView lastMsg;
        private final TextView time;
        private final ImageView contactImg;


        private ContactViewHolder(View itemView){
            super(itemView);
            contactName = itemView.findViewById(R.id.nameContact);
            lastMsg = itemView.findViewById(R.id.lastMessage);
            time = itemView.findViewById(R.id.time);
            contactImg = itemView.findViewById(R.id.imageContact);
            itemView.setOnClickListener(view -> {
                if (onItemClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onItemClickListener.onItemClick(position);
                    }
                }
            });

            itemView.setOnLongClickListener(v -> {
                int position = getAdapterPosition();
                contactRepository.delete(contacts.get(position));
                contacts.remove(position);
                notifyDataSetChanged();
                return true;
            });
        }
    }

    private final LayoutInflater mInflater;
    private List<Contact> contacts;

    ContactRepository contactRepository = new ContactRepository();

    public ContactsListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
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

            holder.contactName.setText(current.getUser().getDisplayName());
            if (current.getLastMessage() == null){
                holder.lastMsg.setText("");
                holder.time.setText("");
            }else {
                holder.lastMsg.setText(current.getLastMessage().getContent());
                holder.time.setText(current.getLastMessage().getCreated());
            }
            holder.contactImg.setImageBitmap(current.getUser().getProfilePic());
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

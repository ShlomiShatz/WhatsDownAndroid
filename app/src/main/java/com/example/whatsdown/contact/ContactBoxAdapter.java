package com.example.whatsdown.contact;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.whatsdown.R;

import java.util.List;

public class ContactBoxAdapter extends BaseAdapter {
    List<Contact> contacts;
    private class ViewHolder{
        TextView name;
        TextView when;
        ImageView img;
        TextView lstMsg;
    }
    public ContactBoxAdapter(List<Contact> posts) {
        this.contacts = posts;
    }
    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.contact_box_layout, parent, false);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.name = convertView.findViewById(R.id.nameContact);
            viewHolder.when = convertView.findViewById(R.id.time);
            viewHolder.img = convertView.findViewById(R.id.imageContact);
            viewHolder.lstMsg = convertView.findViewById(R.id.lastMessage);
            convertView.setTag(viewHolder);
        }

        Contact p = contacts.get(position);
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.name.setText(p.getUser().getDisplayName());
        viewHolder.when.setText(p.getLastMessage().getCreated());
        viewHolder.img.setImageBitmap(p.getUser().getProfilePic());
        viewHolder.lstMsg.setText(p.getLastMessage().getContent());


        return convertView;
    }
}

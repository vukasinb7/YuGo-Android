package com.example.uberapp.gui.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.uberapp.R;
import com.example.uberapp.core.model.Message;
import com.example.uberapp.core.tools.MessageMockup;

public class MessageAdapter extends BaseAdapter {

    public Activity activity;
    public MessageAdapter(Activity activity){
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return MessageMockup.getMessages().size();
    }

    @Override
    public Object getItem(int i) {
        return MessageMockup.getMessages().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Message msg = MessageMockup.getMessages().get(i);
        View v = view;

        if(view == null){
            v = LayoutInflater.from(activity).inflate(R.layout.message_list_item, null);
        }

        //ImageView icon = (ImageView) v.findViewById(R.id.imageViewVehicle);
        TextView senderName = (TextView) v.findViewById(R.id.senderName);
        TextView messageContent = (TextView) v.findViewById(R.id.messageContent);
        TextView messageTime = (TextView) v.findViewById(R.id.messageTime);

        //icon.setImageResource(msgs.getIcon());
        senderName.setText(msg.getSender().getName().toString());
        messageContent.setText(msg.getText());
            messageTime.setText(msg.getSendDateTime().toString());

        return v;
    }
}

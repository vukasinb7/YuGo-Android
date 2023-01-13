package com.example.uberapp.gui.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.uberapp.R;
import com.example.uberapp.core.model.Message;
import com.example.uberapp.core.model.MessageType;
import com.example.uberapp.core.tools.MessageMockup;
import com.example.uberapp.gui.activities.UserChatChannel;
import com.google.android.material.imageview.ShapeableImageView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class InboxMessageAdapter extends BaseAdapter {
    public InboxMessageAdapter(Activity context) {
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
    /*public Activity activity;
    public InboxMessageAdapter(Activity activity){
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return MessageMockup.getConversations().size();
    }

    @Override
    public Object getItem(int i) {
        return MessageMockup.getConversations().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Message msg = MessageMockup.getConversations().get(i);
        View v = view;

        if(view == null){
            v = LayoutInflater.from(activity).inflate(R.layout.list_item_message_preview, null);
        }

        CardView messageCardView = v.findViewById(R.id.messageCardView);
        ShapeableImageView icon = v.findViewById(R.id.messageSenderIcon);
        TextView senderName = v.findViewById(R.id.senderName);
        TextView messageContent = v.findViewById(R.id.messageContent);
        TextView messageTime = v.findViewById(R.id.messageTime);

        String msgText = msg.getText();
        if (msg.getText().length() > 40){
            msgText = msgText.substring(0, 40) + "...";
        }

        LocalDateTime dateTime = msg.getSendDateTime();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        if (dateTime.toLocalDate().isEqual(LocalDateTime.now().toLocalDate())){
            formatter = DateTimeFormatter.ofPattern("hh:mm");
        }

        icon.setImageResource(msg.getSender().getProfilePicture());
        senderName.setText(String.format("%s %s", msg.getSender().getName(), msg.getSender().getLastName()));
        messageContent.setText(msgText);
        messageTime.setText(msg.getSendDateTime().format(formatter));

        if (msg.getMessageType() == MessageType.Assistance){
            messageCardView.setCardBackgroundColor(Color.parseColor("#E3963E"));
        }
        else if (msg.getMessageType() == MessageType.Panic){
            messageCardView.setCardBackgroundColor(Color.parseColor("#AA4A44"));
        }

        messageCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.startActivity(new Intent(activity, UserChatChannel.class));
            }
        });

        return v;
    }*/
}

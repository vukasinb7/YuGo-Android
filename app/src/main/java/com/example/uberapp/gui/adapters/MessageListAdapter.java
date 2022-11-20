package com.example.uberapp.gui.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.uberapp.R;
import com.example.uberapp.core.model.Message;
import com.example.uberapp.core.tools.UserMockup;
import com.google.android.material.imageview.ShapeableImageView;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter {
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    private Context context;
    private List<Message> messageList;

    public MessageListAdapter(Context context, List<Message> messageList) {
        this.context = context;
        this.messageList = messageList;

        //change when login is implemented
        TextView sender = (TextView) ((Activity) context).findViewById(R.id.senderName);
        ShapeableImageView profilePic = (ShapeableImageView) ((Activity) context).findViewById(R.id.profilePic);
        sender.setText(String.format("%s %s", UserMockup.getUsers().get(1).getName(), UserMockup.getUsers().get(1).getLastName()));
        profilePic.setBackgroundResource(UserMockup.getUsers().get(1).getProfilePicture());
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Message message = (Message) messageList.get(position);

        //change when login is implemented
        if (message.getSender().getId().equals("001")) {
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_message_me, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_message_other, parent, false);
            return new ReceivedMessageHolder(view);
        }

        return null;
    }

   @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message message = (Message) messageList.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
        }
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, dateText;

        SentMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.textChatMessageMe);
            timeText = (TextView) itemView.findViewById(R.id.textChatTimestampMe);
            dateText = (TextView) itemView.findViewById(R.id.textChatDateMe);
        }

        void bind(Message message) {
            messageText.setText(message.getText());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            timeText.setText(message.getSendDateTime().format(formatter));
            formatter = DateTimeFormatter.ofPattern("dd.MMM.yyyy");
            dateText.setText(message.getSendDateTime().format(formatter));
        }
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, dateText;

        ReceivedMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.textChatMessageOther);
            timeText = (TextView) itemView.findViewById(R.id.textChatTimestampOther);
            dateText = (TextView) itemView.findViewById(R.id.textChatDateOther);
        }

        void bind(Message message) {
            messageText.setText(message.getText());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            timeText.setText(message.getSendDateTime().format(formatter));
            formatter = DateTimeFormatter.ofPattern("dd.MMM.yyyy");
            dateText.setText(message.getSendDateTime().format(formatter));
        }
    }
}
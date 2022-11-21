package com.example.uberapp.gui.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.uberapp.R;
import com.example.uberapp.core.model.Message;
import com.example.uberapp.core.model.MessageType;
import com.example.uberapp.core.model.User;
import com.example.uberapp.core.tools.DriverHistoryMockup;
import com.example.uberapp.core.tools.MessageMockup;
import com.example.uberapp.core.tools.UserMockup;
import com.google.android.material.imageview.ShapeableImageView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter {
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    private User conversationWith;
    private Context context;
    private List<Message> messageList;

    public MessageListAdapter(Context context, User user, List<Message> messageList) {
        this.context = context;
        this.messageList = messageList;
        conversationWith = user;

        //change when login is implemented
        TextView sender = (TextView) ((Activity) context).findViewById(R.id.senderName);
        ShapeableImageView profilePic = (ShapeableImageView) ((Activity) context).findViewById(R.id.profilePic);
        sender.setText(String.format("%s %s", conversationWith.getName(), conversationWith.getLastName()));
        profilePic.setBackgroundResource(conversationWith.getProfilePicture());

        Button sendBtn = (Button) ((Activity) context).findViewById(R.id.buttonSend);
        sendBtn.setOnClickListener(view -> {
            TextView text = (TextView) ((Activity) context).findViewById(R.id.editChatMessage);
            Message newMsg = new Message(Integer.toString(MessageMockup.getIdCounter()), text.getText().toString(),
                    LocalDateTime.now(), UserMockup.getLoggedUser(), conversationWith,
                    MessageType.Drive, DriverHistoryMockup.getRides().get(1));
            MessageMockup.IncreaseIdCounter();
            MessageMockup.addMessage(newMsg);
            text.setText("");

            int conversationSize = MessageMockup.getConversationMessages().size();
            notifyItemRangeInserted(conversationSize-1, conversationSize);
            RecyclerView rv = (RecyclerView) ((Activity) context).findViewById(R.id.recyclerViewChat);
            rv.smoothScrollToPosition(conversationSize-1);
        });
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Message message = (Message) messageList.get(position);

        if (message.getSender().getId().equals(UserMockup.getLoggedUser().getId())) {
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
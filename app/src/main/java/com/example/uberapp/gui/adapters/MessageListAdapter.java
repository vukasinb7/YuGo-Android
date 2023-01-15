package com.example.uberapp.gui.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.uberapp.R;
import com.example.uberapp.core.auth.TokenManager;
import com.example.uberapp.core.dto.AllMessagesDTO;
import com.example.uberapp.core.dto.MessageDTO;
import com.example.uberapp.core.dto.MessageSendDTO;
import com.example.uberapp.core.dto.UserDetailedDTO;
import com.example.uberapp.core.services.APIClient;
import com.example.uberapp.core.services.ImageService;
import com.example.uberapp.core.services.UserService;
import com.example.uberapp.core.tools.MessageMockup;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageListAdapter extends RecyclerView.Adapter {
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private UserDetailedDTO conversationWith;
    private Context context;
    private List<MessageDTO> messageList;
    private ImageService imageService = APIClient.getClient().create(ImageService.class);
    private UserService userService = APIClient.getClient().create(UserService.class);
    public MessageListAdapter(Context context, UserDetailedDTO user, List<MessageDTO> messageList) {
        this.context = context;
        this.messageList = messageList;
        this.conversationWith = user;

        //change when login is implemented
        TextView sender = (TextView) ((Activity) context).findViewById(R.id.senderName);
        ShapeableImageView profilePic = (ShapeableImageView) ((Activity) context).findViewById(R.id.profilePic);
        sender.setText(String.format("%s %s", conversationWith.getName(), conversationWith.getSurname()));

        Call<ResponseBody> profilePictureCall = imageService.getProfilePicture(user.getProfilePicture());
        profilePictureCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    byte[] bytes = new byte[0];
                    bytes = response.body().bytes();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    profilePic.setImageBitmap(bitmap);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

        ImageButton backBtn = (ImageButton) ((Activity) context).findViewById(R.id.buttonBack);
        ImageButton sendBtn = (ImageButton) ((Activity) context).findViewById(R.id.buttonSend);

        backBtn.setOnClickListener(view -> {
            ((Activity) context).finish();
        });

        sendBtn.setOnClickListener(view -> {
            TextView text = (TextView) ((Activity) context).findViewById(R.id.editChatMessage);
            MessageSendDTO newMsg = new MessageSendDTO(messageList.get(0).getType(), text.getText().toString(),
                    messageList.get(0).getRideId());
            text.setText("");

            Call<MessageDTO> sendMessageCall = userService.sendMessageToUser(user.getId(), newMsg);
            sendMessageCall.enqueue(new Callback<MessageDTO>() {
                @Override
                public void onResponse(Call<MessageDTO> call, Response<MessageDTO> response) {
                    MessageDTO messageDTO = response.body();
                    messageList.add(messageDTO);
                }

                @Override
                public void onFailure(Call<MessageDTO> call, Throwable t) {

                }
            });

            int conversationSize = messageList.size();
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
        MessageDTO message = (MessageDTO) messageList.get(position);

        if (message.getSenderId().equals(TokenManager.getUserId())) {
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
        MessageDTO message = (MessageDTO) messageList.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message, position);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message, position);
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

        void bind(MessageDTO message, int position) {
            messageText.setText(message.getMessage());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            timeText.setText(message.getTimeOfSending().format(formatter));

            if (position-1 >= 0 && messageList.get(position-1).getTimeOfSending().toLocalDate().isEqual(
                    messageList.get(position).getTimeOfSending().toLocalDate())){
                dateText.setVisibility(View.GONE);
            }
            else{
                formatter = DateTimeFormatter.ofPattern("dd.MMM.yyyy");
                dateText.setText(message.getTimeOfSending().format(formatter));
            }
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

        void bind(MessageDTO message, int position) {
            messageText.setText(message.getMessage());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            timeText.setText(message.getTimeOfSending().format(formatter));

            if (position-1 >= 0 && messageList.get(position-1).getTimeOfSending().toLocalDate().isEqual(
                    messageList.get(position).getTimeOfSending().toLocalDate())){
                dateText.setVisibility(View.GONE);
            }
            else{
                formatter = DateTimeFormatter.ofPattern("dd.MMM.yyyy");
                dateText.setText(message.getTimeOfSending().format(formatter));
            }
        }
    }
}
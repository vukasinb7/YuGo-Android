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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uberapp.R;
import com.example.uberapp.core.LocalSettings;
import com.example.uberapp.core.auth.TokenManager;
import com.example.uberapp.core.dto.MessageDTO;
import com.example.uberapp.core.dto.MessageSendDTO;
import com.example.uberapp.core.dto.UserDetailedDTO;
import com.example.uberapp.core.services.APIClient;
import com.example.uberapp.core.services.ImageService;
import com.example.uberapp.core.services.UserService;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.gson.Gson;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;

public class MessageListAdapter extends RecyclerView.Adapter {
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private final UserDetailedDTO conversationWith;
    private final Context context;
    private List<MessageDTO> messages;
    private Integer rideId;
    public CompositeDisposable disposables;
    TextView sender;
    ShapeableImageView profilePic;
    ImageButton backBtn;
    ImageButton sendBtn;
    private final ImageService imageService = APIClient.getClient().create(ImageService.class);
    private final UserService userService = APIClient.getClient().create(UserService.class);
    StompClient mStompClient;
    public MessageListAdapter(Context context, UserDetailedDTO user, Integer rideId, List<MessageDTO> messages) {
        this.disposables = new CompositeDisposable();

        this.context = context;
        this.messages = messages;
        this.conversationWith = user;
        this.rideId = rideId;

        profilePic = ((Activity) context).findViewById(R.id.profilePic);
        backBtn = ((Activity) context).findViewById(R.id.buttonBack);
        sendBtn = ((Activity) context).findViewById(R.id.buttonSend);
        sender = ((Activity) context).findViewById(R.id.senderName);
        sender.setText(String.format("%s %s", conversationWith.getName(), conversationWith.getSurname()));

        this.setupProfilePicture();
        this.setupButtons();
        this.setupWebSocket();
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        MessageDTO message = messages.get(position);

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
        MessageDTO message = messages.get(position);

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

            messageText = itemView.findViewById(R.id.textChatMessageMe);
            timeText = itemView.findViewById(R.id.textChatTimestampMe);
            dateText = itemView.findViewById(R.id.textChatDateMe);
        }

        void bind(MessageDTO message, int position) {
            messageText.setText(message.getMessage());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            timeText.setText(message.getTimeOfSending().format(formatter));

            if (position-1 >= 0 && messages.get(position-1).getTimeOfSending().toLocalDate().isEqual(
                    messages.get(position).getTimeOfSending().toLocalDate())){
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

            messageText = itemView.findViewById(R.id.textChatMessageOther);
            timeText = itemView.findViewById(R.id.textChatTimestampOther);
            dateText = itemView.findViewById(R.id.textChatDateOther);
        }

        void bind(MessageDTO message, int position) {
            messageText.setText(message.getMessage());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            timeText.setText(message.getTimeOfSending().format(formatter));

            if (position-1 >= 0 && messages.get(position-1).getTimeOfSending().toLocalDate().isEqual(
                    messages.get(position).getTimeOfSending().toLocalDate())){
                dateText.setVisibility(View.GONE);
            }
            else{
                formatter = DateTimeFormatter.ofPattern("dd.MMM.yyyy");
                dateText.setText(message.getTimeOfSending().format(formatter));
            }
        }
    }

    public void setupProfilePicture(){
        Call<ResponseBody> profilePictureCall = imageService.getProfilePicture(conversationWith.getProfilePicture());
        profilePictureCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    byte[] bytes = response.body().bytes();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    profilePic.setImageBitmap(bitmap);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Toast.makeText(context, "Oops, something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void setupButtons(){
        backBtn.setOnClickListener(view -> {
            ((Activity) context).finish();
        });

        sendBtn.setOnClickListener(view -> {
            TextView text = ((Activity) context).findViewById(R.id.editChatMessage);
            if (text.getText().toString().equals("")){
                return;
            }
            MessageSendDTO newMsg = new MessageSendDTO("RIDE", text.getText().toString(),
                    rideId);
            text.setText("");

            Call<MessageDTO> sendMessageCall = userService.sendMessageToUser(conversationWith.getId(), newMsg);
            sendMessageCall.enqueue(new Callback<>() {
                @Override
                public void onResponse(@NonNull Call<MessageDTO> call, @NonNull Response<MessageDTO> response) {
                    MessageDTO messageDTO = response.body();
                    messages.add(messageDTO);

                    int conversationSize = messages.size();
                    notifyItemRangeInserted(conversationSize - 1, conversationSize);
                    RecyclerView rv = ((Activity) context).findViewById(R.id.recyclerViewChat);
                    rv.smoothScrollToPosition(conversationSize - 1);
                }

                @Override
                public void onFailure(@NonNull Call<MessageDTO> call, @NonNull Throwable t) {
                    Toast.makeText(context, "Oops, something went wrong!", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    public void setupWebSocket(){
        mStompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "ws://" + LocalSettings.localIP + ":9000/api/socket/websocket");

        Disposable webSocket = mStompClient.topic("/message-topic/"+ TokenManager.getUserId()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(topicMessage -> {
                    Gson gson = new Gson();
                    MessageDTO messageDTO = gson.fromJson(topicMessage.getPayload(), MessageDTO.class);
                    if (messageDTO.getSenderId().equals(conversationWith.getId())){
                        messages.add(messageDTO);

                        int conversationSize = messages.size();
                        notifyItemRangeInserted(conversationSize - 1, conversationSize);
                        RecyclerView rv = ((Activity) context).findViewById(R.id.recyclerViewChat);
                        rv.smoothScrollToPosition(conversationSize - 1);
                    }

                }, throwable -> System.out.println(throwable.getMessage()));

        disposables.add(webSocket);
        mStompClient.connect();
    }

    public void dispose(){
        this.disposables.dispose();
        mStompClient.disconnect();
    }
}
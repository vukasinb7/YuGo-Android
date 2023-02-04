package com.example.uberapp.gui.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.example.uberapp.R;
import com.example.uberapp.core.auth.TokenManager;
import com.example.uberapp.core.dto.AllMessagesDTO;
import com.example.uberapp.core.dto.MessageDTO;
import com.example.uberapp.core.dto.UserDetailedDTO;
import com.example.uberapp.core.model.MessageType;
import com.example.uberapp.core.services.APIClient;
import com.example.uberapp.core.services.DriverService;
import com.example.uberapp.core.services.ImageService;
import com.example.uberapp.core.services.UserService;
import com.example.uberapp.core.tools.MessageMockup;
import com.example.uberapp.gui.activities.UserChatChannel;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InboxMessageAdapter extends BaseAdapter {
    public Activity activity;
    private final List<MessageDTO> conversations;
    private final UserService userService = APIClient.getClient().create(UserService.class);
    private final ImageService imageService = APIClient.getClient().create(ImageService.class);
    public InboxMessageAdapter(Activity activity, List<MessageDTO> conversations){
        this.activity = activity;
        this.conversations = conversations;
    }

    @Override
    public int getCount() {
        return conversations.size();
    }

    @Override
    public Object getItem(int i) {
        return conversations.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MessageDTO msg = conversations.get(i);
        View v = view;

        if(view == null){
            v = LayoutInflater.from(activity).inflate(R.layout.list_item_message_preview, null);
        }

        CardView messageCardView = v.findViewById(R.id.messageCardView);
        ShapeableImageView icon = v.findViewById(R.id.messageSenderIcon);
        TextView senderName = v.findViewById(R.id.senderName);
        TextView messageContent = v.findViewById(R.id.messageContent);
        TextView messageTime = v.findViewById(R.id.messageTime);

        String msgText = msg.getMessage();
        if (msg.getMessage().length() > 40){
            msgText = msgText.substring(0, 40) + "...";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        if (msg.getTimeOfSending().toLocalDate().isEqual(LocalDateTime.now().toLocalDate())){
            formatter = DateTimeFormatter.ofPattern("hh:mm");
        }

        Call<UserDetailedDTO> userCall = userService.getUser(msg.getSenderId());
        userCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<UserDetailedDTO> call, @NonNull Response<UserDetailedDTO> response) {
                if (response.code() == 200) {
                    UserDetailedDTO user = response.body();
                    senderName.setText(String.format("%s %s", user.getName(), user.getSurname()));

                    Call<ResponseBody> profilePictureCall = imageService.getProfilePicture(user.getProfilePicture());
                    profilePictureCall.enqueue(new Callback<>() {
                        @Override
                        public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                            try {
                                byte[] bytes = response.body().bytes();
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                icon.setImageBitmap(bitmap);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                            Toast.makeText(activity, "Oops, something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserDetailedDTO> call, @NonNull Throwable t) {
                Toast.makeText(activity, "Oops, something went wrong", Toast.LENGTH_SHORT).show();
            }
        });


        messageContent.setText(msgText);
        messageTime.setText(msg.getTimeOfSending().format(formatter));

        if (Objects.equals(msg.getType(), "ASSISTANCE")){
            messageCardView.setCardBackgroundColor(Color.parseColor("#E3963E"));
        }
        else if (Objects.equals(msg.getType(), "PANIC")){
            messageCardView.setCardBackgroundColor(Color.parseColor("#AA4A44"));
        }

        messageCardView.setOnClickListener(view1 -> {
            Intent intent = new Intent(activity, UserChatChannel.class);
            Bundle bundle = new Bundle();
            bundle.putInt("senderId", msg.getSenderId());
            bundle.putInt("rideId", msg.getRideId());
            intent.putExtras(bundle);
            activity.startActivity(intent);
        });

        return v;
    }
}

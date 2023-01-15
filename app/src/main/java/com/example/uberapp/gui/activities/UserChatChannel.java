package com.example.uberapp.gui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;

import com.example.uberapp.R;
import com.example.uberapp.core.dto.AllMessagesDTO;
import com.example.uberapp.core.dto.MessageDTO;
import com.example.uberapp.core.dto.UserDetailedDTO;
import com.example.uberapp.core.services.APIClient;
import com.example.uberapp.core.services.UserService;
import com.example.uberapp.core.tools.MessageMockup;
import com.example.uberapp.core.tools.UserMockup;
import com.example.uberapp.gui.adapters.MessageListAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserChatChannel extends AppCompatActivity {
    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;
    private final UserService userService = APIClient.getClient().create(UserService.class);;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chat_channel);

        Bundle b = getIntent().getExtras();
        Integer senderId = (int) b.get("senderId");
        Integer rideId = (int) b.get("rideId");
        Activity activity = this;

        Call<AllMessagesDTO> conversationCall = userService.getUsersConversation(senderId);
        conversationCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<AllMessagesDTO> call, Response<AllMessagesDTO> response) {
                if (response.code() == 200) {
                    AllMessagesDTO allMessagesDTO = response.body();
                    List<MessageDTO> messages = allMessagesDTO.getMessages();

                    Call<UserDetailedDTO> userCall = userService.getUser(senderId);
                    userCall.enqueue(new Callback<>() {
                        @Override
                        public void onResponse(Call<UserDetailedDTO> call, Response<UserDetailedDTO> response) {
                            if (response.code() == 200) {
                                UserDetailedDTO user = response.body();

                                mMessageRecycler = (RecyclerView) findViewById(R.id.recyclerViewChat);
                                mMessageAdapter = new MessageListAdapter(activity, user, rideId, messages);
                                LinearLayoutManager llm = new LinearLayoutManager(activity);
                                llm.setStackFromEnd(true);
                                mMessageRecycler.setLayoutManager(llm);
                                mMessageRecycler.setAdapter(mMessageAdapter);
                            }
                        }

                        @Override
                        public void onFailure(Call<UserDetailedDTO> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<AllMessagesDTO> call, Throwable t) {

            }
        });
    }
}
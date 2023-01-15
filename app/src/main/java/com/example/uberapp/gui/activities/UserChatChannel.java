package com.example.uberapp.gui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.uberapp.R;
import com.example.uberapp.core.tools.MessageMockup;
import com.example.uberapp.core.tools.UserMockup;
import com.example.uberapp.gui.adapters.MessageListAdapter;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserChatChannel extends AppCompatActivity {
    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;
    private List<MessageDTO> messages;
    private int senderId;
    UserService userService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chat_channel);

        userService = APIClient.getClient().create(UserService.class);
        Bundle b = getIntent().getExtras();
        messages = ((AllMessagesDTO) b.get("messages")).getMessages();
        senderId = (int) b.get("senderId");
        Activity activity = this;

        Call<UserDetailedDTO> userCall = userService.getUser(senderId);
        userCall.enqueue(new Callback<UserDetailedDTO>() {
            @Override
            public void onResponse(Call<UserDetailedDTO> call, Response<UserDetailedDTO> response) {
                if (response.code() == 200){
                    UserDetailedDTO user = response.body();

                    mMessageRecycler = (RecyclerView) findViewById(R.id.recyclerViewChat);
                    mMessageAdapter = new MessageListAdapter(activity, user, messages);
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
package com.example.uberapp.gui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.uberapp.R;
import com.example.uberapp.core.tools.MessageMockup;
import com.example.uberapp.core.tools.UserMockup;
import com.example.uberapp.gui.adapters.MessageListAdapter;

public class UserChatChannel extends AppCompatActivity {
    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chat_channel);

        mMessageRecycler = (RecyclerView) findViewById(R.id.recyclerViewChat);
        mMessageAdapter = new MessageListAdapter(this, UserMockup.getUsers().get(1), MessageMockup.getConversationMessages());
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        mMessageRecycler.setLayoutManager(llm);
        mMessageRecycler.setAdapter(mMessageAdapter);
    }


}
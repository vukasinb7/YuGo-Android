package com.example.uberapp.gui.fragments.inbox;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.uberapp.R;
import com.example.uberapp.core.auth.TokenManager;
import com.example.uberapp.core.dto.AllMessagesDTO;
import com.example.uberapp.core.dto.MessageDTO;
import com.example.uberapp.core.services.APIClient;
import com.example.uberapp.core.services.UserService;
import com.example.uberapp.gui.adapters.InboxMessageAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserMessagesFragment extends Fragment {
    private UserService userService;
    public UserMessagesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userService = APIClient.getClient().create(UserService.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_messages, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadConversations(getView());
    }

    public void loadConversations(View view){
        Call<AllMessagesDTO> allMessagesCall = userService.getUserMessages(TokenManager.getUserId());
        allMessagesCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<AllMessagesDTO> call, @NonNull Response<AllMessagesDTO> response) {
                if (response.code() == 200){
                    AllMessagesDTO allMessagesDTO = response.body();
                    List<MessageDTO> messages = allMessagesDTO.getMessages();

                    ListView listView = (ListView) view.findViewById(R.id.listViewMessages);
                    InboxMessageAdapter adapter = new InboxMessageAdapter((Activity) getContext(), messages);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<AllMessagesDTO> call, @NonNull Throwable t) {
                System.out.println("ASD");
            }
        });
    }
}
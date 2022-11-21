package com.example.uberapp.gui.fragments.inbox;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.uberapp.R;
import com.example.uberapp.gui.adapters.InboxMessageAdapter;

public class UserMessagesFragment extends Fragment {

    public UserMessagesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_messages, container, false);
        ListView listView = (ListView) view.findViewById(R.id.listViewMessages);
        InboxMessageAdapter adapter = new InboxMessageAdapter((Activity) getContext());
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ListView listView = (ListView) getView().findViewById(R.id.listViewMessages);
        InboxMessageAdapter adapter = new InboxMessageAdapter((Activity) getContext());
        listView.setAdapter(adapter);
    }
}
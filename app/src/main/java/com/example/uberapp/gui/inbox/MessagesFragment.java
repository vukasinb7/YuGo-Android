package com.example.uberapp.gui.inbox;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.uberapp.R;
import com.example.uberapp.gui.adapters.MessageAdapter;
import com.example.uberapp.gui.adapters.VehicleTypeAdapter;

public class MessagesFragment extends Fragment {

    public MessagesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messages, container, false);
        ListView listView = (ListView) view.findViewById(R.id.listViewMessages);
        MessageAdapter adapter = new MessageAdapter((Activity) getContext());
        listView.setAdapter(adapter);
        return view;
    }
}
package com.example.uberapp.gui.history;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.uberapp.R;
import com.example.uberapp.gui.adapters.DriverHistoryAdapter;
import com.example.uberapp.gui.adapters.PassengerHistoryAdapter;

public class PassengerHistoryFragment extends Fragment {

    public PassengerHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_passenger_history, container, false);
        ListView listView = (ListView) v.findViewById(R.id.passengerHistoryList);
        PassengerHistoryAdapter adapter = new PassengerHistoryAdapter((Activity) getContext());
        listView.setAdapter(adapter);
        // Inflate the layout for this fragment
        return v;
    }
}
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

public class DriverHistoryFragment extends Fragment {
    public DriverHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_driver_history, container, false);
        ListView listView = (ListView) v.findViewById(R.id.driverHistoryList);
        DriverHistoryAdapter adapter = new DriverHistoryAdapter((Activity) getContext());
        listView.setAdapter(adapter);
        // Inflate the layout for this fragment
        return v;
    }
}
package com.example.uberapp.gui.fragments.history;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.uberapp.R;
import com.example.uberapp.gui.adapters.DriverHistoryAdapter;

public class UserHistoryFragment extends Fragment {
    public UserHistoryFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_user_history, container, false);
        ListView listView = view.findViewById(R.id.driverHistoryList);
        DriverHistoryAdapter adapter = new DriverHistoryAdapter((Activity) getContext());
        listView.setAdapter(adapter);
        // Inflate the layout for this fragment
        return view;
    }
}
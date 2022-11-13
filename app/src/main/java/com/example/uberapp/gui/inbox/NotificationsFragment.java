package com.example.uberapp.gui.inbox;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uberapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NotificationsFragment extends Fragment {

    public NotificationsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        View notification = view.findViewById(R.id.notification);

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewRideDialog nrd=new NewRideDialog(getActivity());
                nrd.show();
            }
        });
        return view;
    }
}
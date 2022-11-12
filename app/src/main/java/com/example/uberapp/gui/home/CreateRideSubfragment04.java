package com.example.uberapp.gui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uberapp.R;

public class CreateRideSubfragment04 extends Fragment {



    public CreateRideSubfragment04() {
        // Required empty public constructor
    }

    public static CreateRideSubfragment04 newInstance() {
        CreateRideSubfragment04 fragment = new CreateRideSubfragment04();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_ride_subfragment04, container, false);
    }
}
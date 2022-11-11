package com.example.uberapp.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uberapp.R;

public class CreateRideSubfragment02 extends Fragment {


    public static CreateRideSubfragment02 newInstance() {
        CreateRideSubfragment02 fragment = new CreateRideSubfragment02();
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
        return inflater.inflate(R.layout.fragment_create_ride_subfragment02, container, false);
    }
}
package com.example.uberapp.gui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uberapp.R;

public class DriverHomeFragment extends Fragment {

    public DriverHomeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flDriverFragment,  new DriverCurrentRideFragment()).commit();
        return inflater.inflate(R.layout.fragment_driver_home, container, false);
    }
}
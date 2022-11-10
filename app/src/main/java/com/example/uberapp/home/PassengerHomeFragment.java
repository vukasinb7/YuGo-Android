package com.example.uberapp.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uberapp.R;

public class PassengerHomeFragment extends Fragment {


    public PassengerHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // redirecting to passenger current ride for now
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flPassengerMainFragment,  new PassengerCurrentRideFragment()).commit();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_passenger_home, container, false);
    }
}
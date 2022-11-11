package com.example.uberapp.home;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uberapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class CreateRideFragment extends DialogFragment {


    private int currentSubfragment;

    public CreateRideFragment() {
        // Required empty public constructor
        super(R.layout.fragment_create_ride);
        currentSubfragment = 0;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_create_ride, container, false);
        FloatingActionButton button = view.findViewById(R.id.nextSubfragmentButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (currentSubfragment){
                    case 0:
                        getChildFragmentManager().beginTransaction().replace(R.id.createRideFrameLayout, new CreateRideSubfragment02()).commit();
                        currentSubfragment = 1;
                        break;
                    case 1:
                        getChildFragmentManager().beginTransaction().replace(R.id.createRideFrameLayout, new CreateRideSubfragment03()).commit();
                        currentSubfragment = 2;
                        break;
                    case 2:
                        getChildFragmentManager().beginTransaction().replace(R.id.createRideFrameLayout, new CreateRideSubfragment04()).commit();
                        button.setVisibility(View.GONE);
                        currentSubfragment = 3;
                        break;
                }
            }
        });
        getChildFragmentManager().beginTransaction().replace(R.id.createRideFrameLayout, new CreateRideSubfragment01()).commit();
        return view;

    }
    public static String TAG = "CreateRideFragmentDialog";
}
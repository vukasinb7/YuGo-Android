package com.example.uberapp.home;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.uberapp.R;

public class PassengerCurrentRideFragment extends Fragment {

    public PassengerCurrentRideFragment() {
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
        View view = inflater.inflate(R.layout.fragment_passenger_current_ride, container, false);

        ImageView profilePic = (ImageView) view.findViewById(R.id.profilePic);
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout driverPopupMenu = (RelativeLayout) view.findViewById(R.id.driverPopupMenu);
                if(driverPopupMenu.getVisibility()==View.GONE) {
                    driverPopupMenu.setVisibility(View.VISIBLE);
                }
                else if (driverPopupMenu.getVisibility() == View.VISIBLE)
                {
                    driverPopupMenu.setVisibility(View.GONE);
                }
            }
        });

        TextView reportDriver = (TextView) view.findViewById(R.id.reportDriver);
        reportDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }


}
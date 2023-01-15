package com.example.uberapp.gui.fragments.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.uberapp.R;
import com.example.uberapp.core.dto.RideDetailedDTO;
import com.example.uberapp.core.model.RideStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CreateRideLoader extends Fragment {

    TextView message;
    RelativeLayout progressBar;

    public void changeLoadingStatus(RideDetailedDTO ride){
        if(ride.getStatus().equals("SCHEDULED")){
            message.setText("The ride has been scheduled. You will get a confirmation notification, 30 minutes before ride.");
            progressBar.setVisibility(View.GONE);
        }else if(ride.getStatus().equals("REJECTED")){
            message.setText("We couldn't find available driver, please try again later.");
            progressBar.setVisibility(View.GONE);
        }else if(ride.getStatus().equals("ACCEPTED")){
            LocalDateTime startTime = LocalDateTime.parse(ride.getStartTime(), DateTimeFormatter.ISO_DATE_TIME);
            message.setText("Driver is on his way.\nEstimated time of arrival: " + startTime.getHour() + ":" + startTime.getMinute() + "h");
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_ride_loader, container, false);

        message = view.findViewById(R.id.driverSearchingMessage);
        progressBar = view.findViewById(R.id.loadingPanel);

        return view;
    }
}
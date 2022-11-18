package com.example.uberapp.gui.fragments.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.uberapp.R;

public class CreateRideSubfragment03 extends Fragment {

    public static CreateRideSubfragment03 newInstance() {
        CreateRideSubfragment03 fragment = new CreateRideSubfragment03();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_ride_subfragment03, container, false);
        DatePicker datePicker = view.findViewById(R.id.rideDatePicker);
        datePicker.setEnabled(false);
        TimePicker timePicker = view.findViewById(R.id.rideTimePicker);
        timePicker.setEnabled(false);

        CheckBox checkBox = view.findViewById(R.id.useCurrentTimeCheckBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    datePicker.setEnabled(false);
                    timePicker.setEnabled(false);
                }else{
                    datePicker.setEnabled(true);
                    timePicker.setEnabled(true);
                }
            }
        });
        return view;

    }
}
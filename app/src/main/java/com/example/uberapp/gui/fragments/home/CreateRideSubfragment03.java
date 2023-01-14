package com.example.uberapp.gui.fragments.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.fragment.app.Fragment;

import com.example.uberapp.R;

import java.time.LocalDateTime;

public class CreateRideSubfragment03 extends Fragment {

    public interface OnDateTimeChangedListener{
        void onDateTimeChanged(LocalDateTime dateTime);
    }
    OnDateTimeChangedListener dateTimeChangedListener;

    public LocalDateTime dateTime = LocalDateTime.now();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dateTimeChangedListener = (OnDateTimeChangedListener) getParentFragment();
        dateTimeChangedListener.onDateTimeChanged(dateTime);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_ride_subfragment03, container, false);
        DatePicker datePicker = view.findViewById(R.id.rideDatePicker);
        datePicker.setEnabled(false);
        TimePicker timePicker = view.findViewById(R.id.rideTimePicker);
        timePicker.setEnabled(false);
        datePicker.setMinDate(System.currentTimeMillis());
        CheckBox nowCheckBox = view.findViewById(R.id.useCurrentTimeCheckBox);
        nowCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(!isChecked){
                dateTime = dateTime.withYear(datePicker.getYear())
                        .withMonth(datePicker.getMonth() + 1)
                        .withDayOfMonth(datePicker.getDayOfMonth())
                        .withHour(timePicker.getHour())
                        .withMinute(timePicker.getMinute());
            }else{
                dateTime = LocalDateTime.now();
                dateTimeChangedListener.onDateTimeChanged(dateTime);
            }

        });

        datePicker.setOnDateChangedListener((view12, year, monthOfYear, dayOfMonth) -> {
            dateTime = dateTime.withYear(year).withMonth(monthOfYear + 1).withDayOfMonth(dayOfMonth);
            dateTimeChangedListener.onDateTimeChanged(dateTime);
        });
        timePicker.setOnTimeChangedListener((view1, hourOfDay, minute) -> {
            dateTime = dateTime.withHour(hourOfDay).withMinute(minute);
            dateTimeChangedListener.onDateTimeChanged(dateTime);
        });

        CheckBox checkBox = view.findViewById(R.id.useCurrentTimeCheckBox);
        checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
            datePicker.setEnabled(!b);
            timePicker.setEnabled(!b);
        });
        return view;

    }
}
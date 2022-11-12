package com.example.uberapp.gui.home;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.uberapp.R;
import com.example.uberapp.gui.adapters.VehicleTypeAdapter;

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
        View view = inflater.inflate(R.layout.fragment_create_ride_subfragment02, container, false);
        ListView listView = (ListView) view.findViewById(R.id.listViewVehicleType);
        VehicleTypeAdapter adapter = new VehicleTypeAdapter((Activity) getContext());
        listView.setAdapter(adapter);
        return view;
    }
}
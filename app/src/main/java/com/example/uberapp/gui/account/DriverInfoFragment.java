package com.example.uberapp.gui.account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uberapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DriverInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DriverInfoFragment extends Fragment {


    public DriverInfoFragment() {
        // Required empty public constructor
    }
    public static DriverInfoFragment newInstance() {
        DriverInfoFragment fragment = new DriverInfoFragment();
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
        return inflater.inflate(R.layout.fragment_driver_info, container, false);
    }
}
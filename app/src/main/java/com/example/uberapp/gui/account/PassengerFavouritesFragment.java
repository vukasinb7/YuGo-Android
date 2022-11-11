package com.example.uberapp.gui.account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uberapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PassengerFavouritesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PassengerFavouritesFragment extends Fragment {

    public PassengerFavouritesFragment() {
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
        return inflater.inflate(R.layout.fragment_passenger_favourites, container, false);
    }
}
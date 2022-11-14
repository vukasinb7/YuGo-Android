package com.example.uberapp.gui.account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uberapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DriverFavouritesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DriverFavouritesFragment extends Fragment {


    public DriverFavouritesFragment() {
        // Required empty public constructor
    }

    public static DriverFavouritesFragment newInstance() {
        DriverFavouritesFragment fragment = new DriverFavouritesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_driver_favourites, container, false);
    }
}
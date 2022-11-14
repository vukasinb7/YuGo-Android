package com.example.uberapp.gui.home;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.uberapp.R;

public class CreateRideSubfragment04 extends Fragment {



    public CreateRideSubfragment04() {
        // Required empty public constructor
    }

    public static CreateRideSubfragment04 newInstance() {
        CreateRideSubfragment04 fragment = new CreateRideSubfragment04();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_ride_subfragment04, container, false);
        ImageButton button = view.findViewById(R.id.buttonReturnBack);
        final Boolean[] clicked = {false};
        ImageButton favourites=view.findViewById(R.id.addToFavouritesBtn);
        CreateRideFragment fragment = (CreateRideFragment) getParentFragment();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.buttonPrevOnClick();
            }
        });
        favourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!clicked[0]) {
                    favourites.setImageResource(R.drawable.star_filled);
                    clicked[0] =true;
                }
                else {
                    favourites.setImageResource(R.drawable.star_outline);
                    clicked[0] =false;
                }

            }
        });
        return view;
    }
}
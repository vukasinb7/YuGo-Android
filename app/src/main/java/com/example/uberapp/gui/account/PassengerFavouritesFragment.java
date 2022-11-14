package com.example.uberapp.gui.account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.uberapp.R;

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

        View v= inflater.inflate(R.layout.fragment_passenger_favourites, container, false);
        ImageButton arrowF = v.findViewById(R.id.arrowBtnFavouriteRoute);
        RelativeLayout hiddenViewF = v.findViewById(R.id.hiddenFavouritePathPart);
        LinearLayout hiddenView2F = v.findViewById(R.id.hiddenPathCheckPoints);

        arrowF.setOnClickListener(view_clickF -> {
            if (hiddenViewF.getVisibility() == View.VISIBLE) {
                hiddenViewF.setVisibility(View.GONE);
                hiddenView2F.setVisibility(View.GONE);
                arrowF.setImageResource(R.drawable.arrow_down);
            }
            else {
                hiddenViewF.setVisibility(View.VISIBLE);
                hiddenView2F.setVisibility(View.VISIBLE);
                arrowF.setImageResource(R.drawable.arrow_up);
            }
        });
        return v;
    }
}
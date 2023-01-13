package com.example.uberapp.gui.fragments.home;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.uberapp.R;
import com.example.uberapp.core.model.LocationInfo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class CreateRideFragment extends DialogFragment implements CreateRideSubfragment01.OnRouteChangedListener {

    private int currentSubfragment;
    private CreateRideSubfragment01 subFrag01;
    private CreateRideSubfragment02 subFrag02;
    private CreateRideSubfragment03 subFrag03;
    private FloatingActionButton buttonNext;
    private FloatingActionButton buttonPrev;

    private LocationInfo destination;
    private LocationInfo departure;

    public static String TAG = "CreateRideFragmentDialog";

    public CreateRideFragment() {
        // Required empty public constructor
        super(R.layout.fragment_create_ride);
        currentSubfragment = 0;
    }


    public void buttonPrevOnClick(){
        switch (currentSubfragment){
            case 1:
                getChildFragmentManager().beginTransaction().replace(R.id.createRideFrameLayout, subFrag01).commit();
                buttonPrev.setVisibility(View.GONE);
                break;
            case 2:
                getChildFragmentManager().beginTransaction().replace(R.id.createRideFrameLayout, subFrag02).commit();
                break;
            case 3:
                getChildFragmentManager().beginTransaction().replace(R.id.createRideFrameLayout, subFrag03).commit();
                buttonPrev.setVisibility(View.VISIBLE);
                buttonNext.setVisibility(View.VISIBLE);
                break;
        }
        currentSubfragment--;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.subFrag01  = new CreateRideSubfragment01();
        this.subFrag02 = new CreateRideSubfragment02();
        this.subFrag03 = new CreateRideSubfragment03();

        View view = inflater.inflate(R.layout.fragment_create_ride, container, false);
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        buttonNext = view.findViewById(R.id.nextSubfragmentButton);
        buttonNext.setEnabled(false);
        buttonPrev = view.findViewById(R.id.previouosSubfragmentButton);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (currentSubfragment){
                    case 0:
                        getChildFragmentManager().beginTransaction().replace(R.id.createRideFrameLayout, subFrag02).commit();
                        buttonPrev.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        getChildFragmentManager().beginTransaction().replace(R.id.createRideFrameLayout, subFrag03).commit();
                        break;
                    case 2:
                        getChildFragmentManager().beginTransaction().replace(R.id.createRideFrameLayout, new CreateRideSubfragment04()).commit();
                        buttonNext.setVisibility(View.GONE);
                        buttonPrev.setVisibility(View.GONE);
                        break;
                }
                currentSubfragment++;
            }
        });


        buttonPrev.setVisibility(View.GONE);
        buttonPrev.setOnClickListener(view1 -> buttonPrevOnClick());
        getChildFragmentManager().beginTransaction().replace(R.id.createRideFrameLayout, new CreateRideSubfragment01()).commit();
        return view;

    }

    @Override
    public void onRideRouteChanged(LocationInfo departure, LocationInfo destination) {
        this.departure = departure;
        this.destination = destination;
        this.buttonNext.setEnabled(departure != null && destination != null);
    }
}
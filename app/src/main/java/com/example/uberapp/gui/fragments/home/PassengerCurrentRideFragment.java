package com.example.uberapp.gui.fragments.home;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.view.ContextThemeWrapper;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.example.uberapp.R;

public class PassengerCurrentRideFragment extends Fragment {

    public PassengerCurrentRideFragment() {
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
        View view = inflater.inflate(R.layout.fragment_passenger_current_ride, container, false);

        ImageView profilePic = (ImageView) view.findViewById(R.id.profilePic);
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });

        return view;
    }

    public void showPopupMenu(View view){
        Context wrapper = new ContextThemeWrapper(getActivity(), R.style.popupBgStyle);
        PopupMenu popupMenu = new PopupMenu(wrapper,view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        Menu menu = popupMenu.getMenu();
        menu.setGroupDividerEnabled(true);
        inflater.inflate(R.menu.driver_options_popup_menu, menu);
        popupMenu.show();
    }
}
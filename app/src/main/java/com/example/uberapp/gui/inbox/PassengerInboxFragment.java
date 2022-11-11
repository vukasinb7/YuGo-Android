package com.example.uberapp.gui.inbox;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uberapp.R;
import com.google.android.material.tabs.TabLayout;

public class PassengerInboxFragment extends Fragment {
    TabLayout inboxTabLayout;
    ViewPager2 inboxViewPager;
    PassengerInboxViewPagerAdapter inboxAdapter;

    public PassengerInboxFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_passenger_inbox, container, false);

        inboxTabLayout = view.findViewById(R.id.passengerInboxTabLayout);
        inboxViewPager = view.findViewById(R.id.passengerInboxViewPager);
        inboxAdapter =  new PassengerInboxViewPagerAdapter(this);
        inboxViewPager.setAdapter(inboxAdapter);

        inboxTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                inboxViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        inboxViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                inboxTabLayout.getTabAt(position).select();
            }
        });
        return view;
    }

}
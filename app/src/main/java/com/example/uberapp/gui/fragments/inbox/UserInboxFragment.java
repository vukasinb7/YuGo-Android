package com.example.uberapp.gui.fragments.inbox;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uberapp.R;
import com.example.uberapp.gui.adapters.viewpagers.UserInboxViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class UserInboxFragment extends Fragment {
    TabLayout inboxTabLayout;
    ViewPager2 inboxViewPager2;
    UserInboxViewPagerAdapter inboxAdapter;

    public UserInboxFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_inbox, container, false);

        inboxTabLayout = view.findViewById(R.id.inboxTabLayout);
        inboxViewPager2 = view.findViewById(R.id.driverInboxViewPager);
        inboxAdapter =  new UserInboxViewPagerAdapter(this);
        inboxViewPager2.setAdapter(inboxAdapter);

        inboxTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                inboxViewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        inboxViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                inboxTabLayout.getTabAt(position).select();
            }
        });
        return view;
    }
}
package com.example.uberapp.gui.fragments.account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uberapp.R;
import com.example.uberapp.core.model.User;
import com.example.uberapp.core.tools.UserMockup;
import com.example.uberapp.gui.adapters.viewpagers.DriverAccountViewPagerAdapter;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.tabs.TabLayout;

public class DriverAccountFragment extends Fragment {
    TabLayout accountTabLayout;
    ViewPager2 accountViewPager;
    DriverAccountViewPagerAdapter accountAdapter;

    public DriverAccountFragment() {
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
        View view = inflater.inflate(R.layout.fragment_driver_account, container, false);

        accountTabLayout = view.findViewById(R.id.driverAccountTabLayout);
        accountViewPager = view.findViewById(R.id.driverAccountViewPager);
        accountAdapter =  new DriverAccountViewPagerAdapter(this);
        accountViewPager.setAdapter(accountAdapter);

        accountTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                accountViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        accountViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                accountTabLayout.getTabAt(position).select();
            }
        });

        /*User user = UserMockup.getUsers().get(1);
        ShapeableImageView imageView = view.findViewById(R.id.profilePic);
        imageView.setImageResource(user.getProfilePicture());*/
        return view;
    }
}
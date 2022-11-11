package com.example.uberapp.gui.account;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class DriverAccountViewPagerAdapter extends FragmentStateAdapter {

    public DriverAccountViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new DriverInfoFragment();
            case 1:
                return new DriverFavouritesFragment();
            case 2:
                return new DriverReportFragment();
            default:
                return new DriverInfoFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}

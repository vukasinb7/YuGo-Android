package com.example.uberapp.gui.account;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class PassengerAccountViewPagerAdapter extends FragmentStateAdapter {

    public PassengerAccountViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new PassengerInfoFragment();
            case 1:
                return new PassengerFavouritesFragment();
            case 2:
                return new PassengerReportFragment();
            default:
                return new PassengerInfoFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}

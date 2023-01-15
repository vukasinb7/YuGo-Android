package com.example.uberapp.gui.adapters.viewpagers;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.uberapp.gui.fragments.account.PassengerFavouritesFragment;
import com.example.uberapp.gui.fragments.account.PassengerReportFragment;
import com.example.uberapp.gui.fragments.account.UserInfoFragment;

public class PassengerAccountViewPagerAdapter extends FragmentStateAdapter {

    public PassengerAccountViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new UserInfoFragment();
            case 1:
                return new PassengerFavouritesFragment();
            case 2:
                return new PassengerReportFragment();
            default:
                return new UserInfoFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}

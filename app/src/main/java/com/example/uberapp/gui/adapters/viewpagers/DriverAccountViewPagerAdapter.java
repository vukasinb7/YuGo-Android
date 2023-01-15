package com.example.uberapp.gui.adapters.viewpagers;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.uberapp.gui.fragments.account.DocumentsFragment;
import com.example.uberapp.gui.fragments.account.DriverReportFragment;
import com.example.uberapp.gui.fragments.account.DriverStatisticsFragment;
import com.example.uberapp.gui.fragments.account.UserInfoFragment;

public class DriverAccountViewPagerAdapter extends FragmentStateAdapter {

    public DriverAccountViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new UserInfoFragment();
            case 1:
                return new DocumentsFragment();
            case 2:
                return new DriverStatisticsFragment();
            case 3:
                return new DriverReportFragment();
            default:
                return new UserInfoFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}

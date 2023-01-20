package com.example.uberapp.gui.adapters.viewpagers;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.uberapp.core.dto.UserDetailedDTO;
import com.example.uberapp.gui.fragments.account.PasswordFragment;
import com.example.uberapp.gui.fragments.account.UserDocumentsFragment;
import com.example.uberapp.gui.fragments.account.DriverReportFragment;
import com.example.uberapp.gui.fragments.account.DriverStatisticsFragment;
import com.example.uberapp.gui.fragments.account.UserInfoFragment;

public class DriverAccountViewPagerAdapter extends FragmentStateAdapter {

    UserDetailedDTO user;
    public DriverAccountViewPagerAdapter(@NonNull Fragment fragment, UserDetailedDTO user) {
        super(fragment);
        this.user = user;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new UserInfoFragment(user);
            case 1:
                return new PasswordFragment(user);
            case 2:
                return new UserDocumentsFragment();
            case 3:
                return new DriverStatisticsFragment();
            case 4:
                return new DriverReportFragment();
            default:
                return new UserInfoFragment(user);
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}

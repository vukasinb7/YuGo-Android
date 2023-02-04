package com.example.uberapp.gui.adapters.viewpagers;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.uberapp.core.dto.UserDetailedDTO;
import com.example.uberapp.gui.fragments.account.PassengerFavouritesFragment;
import com.example.uberapp.gui.fragments.account.PassengerReportFragment;
import com.example.uberapp.gui.fragments.account.PasswordFragment;
import com.example.uberapp.gui.fragments.account.UserInfoFragment;

public class PassengerAccountViewPagerAdapter extends FragmentStateAdapter {
    UserDetailedDTO user;
    public PassengerAccountViewPagerAdapter(@NonNull Fragment fragment, UserDetailedDTO user) {
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
                return new PassengerReportFragment();
            default:
                return new UserInfoFragment(user);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}

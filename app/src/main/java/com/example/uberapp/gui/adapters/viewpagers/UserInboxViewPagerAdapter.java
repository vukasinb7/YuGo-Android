package com.example.uberapp.gui.adapters.viewpagers;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.uberapp.gui.fragments.inbox.UserMessagesFragment;
import com.example.uberapp.gui.fragments.inbox.UserNotificationsFragment;

public class UserInboxViewPagerAdapter extends FragmentStateAdapter {

    public UserInboxViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new UserMessagesFragment();
            case 1:
                return new UserNotificationsFragment();
            default:
                return new UserMessagesFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}

package com.example.uberapp.gui.inbox;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class DriverInboxViewPagerAdapter extends FragmentStateAdapter {

    public DriverInboxViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new MessagesFragment();
            case 1:
                return new NotificationsFragment();
            default:
                return new MessagesFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}

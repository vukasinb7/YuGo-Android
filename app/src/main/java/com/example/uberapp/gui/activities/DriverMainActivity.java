package com.example.uberapp.gui.activities;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.uberapp.gui.dialogs.ExitAppDialog;
import com.example.uberapp.gui.dialogs.NewRideDialog;
import com.example.uberapp.gui.fragments.account.DriverAccountFragment;
import com.example.uberapp.R;
import com.example.uberapp.gui.fragments.history.UserHistoryFragment;
import com.example.uberapp.gui.fragments.home.DriverHomeFragment;
import com.example.uberapp.gui.fragments.inbox.UserInboxFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class DriverMainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener{
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.driverHome);

    }
    @Override
    public void onBackPressed() {
        if (bottomNavigationView.getSelectedItemId()==R.id.driverHome)
        {
            ExitAppDialog ead=new ExitAppDialog(this);
            ead.show();
        }
        else {
            getSupportFragmentManager().beginTransaction().replace(R.id.flDriverFragment, driverHomeFragment).commit();
            bottomNavigationView.setSelectedItemId(R.id.driverHome);
        }
    }
    DriverHomeFragment driverHomeFragment = new DriverHomeFragment();
    UserInboxFragment userInboxFragment = new UserInboxFragment();
    UserHistoryFragment userHistoryFragment = new UserHistoryFragment();
    DriverAccountFragment driverAccountFragment = new DriverAccountFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.driverHome:
                getSupportFragmentManager().beginTransaction().replace(R.id.flDriverFragment, driverHomeFragment).commit();
                return true;
            case R.id.driverInbox:
                getSupportFragmentManager().beginTransaction().replace(R.id.flDriverFragment, userInboxFragment).commit();
                return true;
            case R.id.driverHistory:
                getSupportFragmentManager().beginTransaction().replace(R.id.flDriverFragment, userHistoryFragment).commit();
                return true;
            case R.id.driverAccount:
                getSupportFragmentManager().beginTransaction().replace(R.id.flDriverFragment, driverAccountFragment).commit();
                return true;
        }
        return false;
    }
}
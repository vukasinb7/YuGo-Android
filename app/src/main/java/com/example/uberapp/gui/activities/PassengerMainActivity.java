package com.example.uberapp.gui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.uberapp.gui.dialogs.ExitAppDialog;
import com.example.uberapp.gui.fragments.account.PassengerAccountFragment;
import com.example.uberapp.R;
import com.example.uberapp.gui.fragments.history.UserHistoryFragment;
import com.example.uberapp.gui.fragments.home.PassengerHomeFragment;
import com.example.uberapp.gui.fragments.inbox.UserInboxFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class PassengerMainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    PassengerHomeFragment homeFragment = new PassengerHomeFragment();
    PassengerAccountFragment accountFragment = new PassengerAccountFragment();
    UserInboxFragment inboxFragment = new UserInboxFragment();
    UserHistoryFragment historyFragment = new UserHistoryFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_main);

        bottomNavigationView = findViewById(R.id.passengerBottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.passenger_home);
    }
    @Override
    public void onBackPressed() {
        if (bottomNavigationView.getSelectedItemId()==R.id.passenger_home)
        {
            ExitAppDialog ead=new ExitAppDialog(this);
            ead.show();
        }
        else {
            getSupportFragmentManager().beginTransaction().replace(R.id.flPassengerMainFragment, homeFragment).commit();
            bottomNavigationView.setSelectedItemId(R.id.passenger_home);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.passenger_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.flPassengerMainFragment, homeFragment).commit();
                break;
            case R.id.passanger_account:
                getSupportFragmentManager().beginTransaction().replace(R.id.flPassengerMainFragment, accountFragment).commit();
                break;
            case R.id.passenger_inbox:
                getSupportFragmentManager().beginTransaction().replace(R.id.flPassengerMainFragment, inboxFragment).commit();
                break;
            case R.id.passanger_history:
                getSupportFragmentManager().beginTransaction().replace(R.id.flPassengerMainFragment, historyFragment).commit();
                break;
        }
        return true;
    }
}
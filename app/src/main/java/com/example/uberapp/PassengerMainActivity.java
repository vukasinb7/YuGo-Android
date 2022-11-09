package com.example.uberapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class PassengerMainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    BottomNavigationView navigation;
    PassengerHomeFragment homeFragment = new PassengerHomeFragment();
    PassengerAccountFragment accountFragment = new PassengerAccountFragment();
    PassengerInboxFragment inboxFragment = new PassengerInboxFragment();
    PassengerHistoryFragment historyFragment = new PassengerHistoryFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_main);

        navigation = findViewById(R.id.passengerBottomNavigationView);
        navigation.setOnItemSelectedListener(this);
        navigation.setSelectedItemId(R.id.passenger_home);
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
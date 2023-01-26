package com.example.uberapp.gui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.uberapp.R;
import com.example.uberapp.core.LocalSettings;
import com.example.uberapp.core.auth.TokenManager;
import com.example.uberapp.core.dto.RideDetailedDTO;
import com.example.uberapp.gui.dialogs.ExitAppDialog;
import com.example.uberapp.gui.dialogs.NewRideDialog;
import com.example.uberapp.gui.fragments.account.AccountFragment;
import com.example.uberapp.gui.fragments.history.UserHistoryFragment;
import com.example.uberapp.gui.fragments.home.HomeFragment;
import com.example.uberapp.gui.fragments.inbox.UserInboxFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;

public class DriverMainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, NewRideDialog.OnAcceptRideListener {
    BottomNavigationView bottomNavigationView;
    NewRideDialog nrd;
    private StompClient mStompClient;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.driverHome);
        mStompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "ws://" + LocalSettings.localIP + ":9000/api/socket/websocket");

        mStompClient.topic("/ride-topic/driver-request/"+ TokenManager.getUserId()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(topicMessage -> {
                    Gson gson= new Gson();
                    JsonObject jsonObject=gson.fromJson(topicMessage.getPayload(), JsonObject.class);
                    Integer rideID=jsonObject.getAsJsonPrimitive("rideID").getAsInt();
                    nrd=new NewRideDialog(rideID);
                    nrd.show(getSupportFragmentManager(),NewRideDialog.TAG);

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        System.out.println(throwable.getMessage());
                    }
                });
        mStompClient.lifecycle().subscribe(lifecycleEvent -> {
            switch (lifecycleEvent.getType()) {

                case OPENED:
                    System.out.println("OPENED");
                    break;

                case ERROR:
                    System.out.println(lifecycleEvent.getException().getMessage());
                    break;

                case CLOSED:
                    System.out.println("CLOSED");
                    break;
            }
        });

        mStompClient.connect();


    }

    @Override
    public void onBackPressed() {
        if (bottomNavigationView.getSelectedItemId() == R.id.driverHome) {
            ExitAppDialog ead = new ExitAppDialog(this);
            ead.show();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.flDriverFragment, homeFragment).commit();
            bottomNavigationView.setSelectedItemId(R.id.driverHome);
        }
    }

    HomeFragment homeFragment = new HomeFragment();
    UserInboxFragment userInboxFragment = new UserInboxFragment();
    UserHistoryFragment userHistoryFragment = new UserHistoryFragment();
    AccountFragment driverAccountFragment = new AccountFragment();


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.driverHome:
                getSupportFragmentManager().beginTransaction().replace(R.id.flDriverFragment, homeFragment).commit();
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
    @Override
    public void onDestroy() {
        super.onDestroy();
        mStompClient.disconnect();
        if (nrd!=null)
            nrd.onDestroy();

    }

    @Override
    public void onPause(){
        super.onPause();
        if (nrd!=null)
            nrd.onPause();
    }

    @Override
    public void acceptRide(RideDetailedDTO ride) {
        homeFragment.acceptRide(ride);
    }
}
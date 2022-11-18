package com.example.uberapp.gui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.uberapp.R;

public class NewUserRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_register);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonCreateAccount:
                Intent passengerMainActivity = new Intent(this, PassengerMainActivity.class);
                startActivity(passengerMainActivity);
        }
    }
}
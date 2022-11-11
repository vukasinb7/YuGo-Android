package com.example.uberapp.gui.account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.uberapp.R;
import com.example.uberapp.gui.main.DriverMainActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Button btn = (Button)findViewById(R.id.loginButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, DriverMainActivity.class));
            }
        });

    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.registerButton:
                Intent registerActivity = new Intent(this, NewUserRegisterActivity.class);
                startActivity(registerActivity);
        }
    }
}
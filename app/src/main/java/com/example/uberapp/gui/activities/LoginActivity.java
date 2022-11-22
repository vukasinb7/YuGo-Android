package com.example.uberapp.gui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.uberapp.R;
import com.example.uberapp.gui.dialogs.ForgotPasswordDialog;
import com.example.uberapp.gui.dialogs.NewRideDialog;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        TextView forgotPassword=findViewById(R.id.forgotPassword);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ForgotPasswordDialog fpd=new ForgotPasswordDialog(LoginActivity.this);
                fpd.show();
            }
        });
        Button btn = (Button)findViewById(R.id.loginButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, DriverMainActivity.class));
                finish();
            }
        });

    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.registerButton:
                Intent registerActivity = new Intent(this, NewUserRegisterActivity.class);
                startActivity(registerActivity);
                finish();
        }
    }
}
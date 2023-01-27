package com.example.uberapp.gui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uberapp.R;
import com.example.uberapp.core.dto.LoginCredentialsDTO;
import com.example.uberapp.core.services.APIClient;
import com.example.uberapp.core.services.UserService;
import com.example.uberapp.core.auth.TokenManager;
import com.example.uberapp.gui.dialogs.ForgotPasswordDialog;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {

    UserService userService;
    TextView emailTextView;
    TextView passwordTextView;
    TextView forgotPassword;
    Button loginButton;
    Button signUpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userService = APIClient.getClient().create(UserService.class);
        emailTextView = findViewById(R.id.loginEmailAddress);
        passwordTextView = findViewById(R.id.loginPassword);
        forgotPassword=findViewById(R.id.forgotPassword);
        loginButton = findViewById(R.id.loginButton);
        signUpButton = findViewById(R.id.registerButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onClick(View v) {
                LoginCredentialsDTO credentials = new LoginCredentialsDTO();
                String email = emailTextView.getText().toString();
                String password = passwordTextView.getText().toString();
                credentials.email = email;
                credentials.password = password;
                userService.login(credentials)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(tokenState -> {
                            TokenManager.setToken(tokenState.accessToken);
                            SharedPreferences preferences = getSharedPreferences("YuGo", Context.MODE_PRIVATE);
                            preferences.edit().putString("accessToken",tokenState.accessToken).apply();
                            preferences.edit().putString("refreshToken",tokenState.refreshToken).apply();
                            if (TokenManager.getRole().equals("PASSENGER")){
                                Intent homePage = new Intent(LoginActivity.this, PassengerMainActivity.class);
                                startActivity(homePage);
                                finish();
                            }
                            else if (TokenManager.getRole().equals("DRIVER")){
                                Intent homePage = new Intent(LoginActivity.this, DriverMainActivity.class);
                                startActivity(homePage);
                                finish();
                            }
                            else{
                                Toast.makeText(LoginActivity.this, "Admin can't sign on mobile application!", Toast.LENGTH_SHORT).show();
                            }
                        }, throwable -> Toast.makeText(LoginActivity.this, "Email or password is incorrect", Toast.LENGTH_SHORT).show());
            }
        });


        forgotPassword.setOnClickListener(view -> {
            ForgotPasswordDialog fpd=new ForgotPasswordDialog(LoginActivity.this);
            fpd.show();
        });

        signUpButton.setOnClickListener(v -> {
            Intent registerActivity = new Intent(LoginActivity.this, NewUserRegisterActivity.class);
            startActivity(registerActivity);
            finish();
        });

    }
}
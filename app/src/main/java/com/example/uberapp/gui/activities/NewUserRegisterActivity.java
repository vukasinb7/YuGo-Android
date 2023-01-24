package com.example.uberapp.gui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.uberapp.R;
import com.example.uberapp.core.dto.NewUserDTO;
import com.example.uberapp.core.dto.UserDetailedDTO;
import com.example.uberapp.core.services.APIClient;
import com.example.uberapp.core.services.DriverService;
import com.example.uberapp.core.services.PassengerService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewUserRegisterActivity extends AppCompatActivity {
    PassengerService passengerService = APIClient.getClient().create(PassengerService.class);
    EditText firstName;
    EditText lastName;
    EditText phone;
    EditText address;
    EditText email;
    EditText password;
    EditText confirmPassword;
    Button createAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_register);

        firstName = findViewById(R.id.firstNameEditText);
        lastName = findViewById(R.id.lastNameEditText);
        phone = findViewById(R.id.phoneEditText);
        address = findViewById(R.id.addressEditText);
        email = findViewById(R.id.emailEditText);
        password = findViewById(R.id.passwordEditText);
        confirmPassword = findViewById(R.id.confirmPasswordEditText);

        createAccount = findViewById(R.id.buttonCreateAccount);

        createAccount.setOnClickListener(v -> {
            NewUserDTO newUserDTO = new NewUserDTO(firstName.getText().toString(),
                    lastName.getText().toString(), null, phone.getText().toString(),
                    email.getText().toString(), address.getText().toString(), password.getText().toString());
            Call<UserDetailedDTO> createPassengerCall = passengerService.createPassenger(newUserDTO);
            createPassengerCall.enqueue(new Callback<>() {
                @Override
                public void onResponse(@NonNull Call<UserDetailedDTO> call, @NonNull Response<UserDetailedDTO> response) {
                    if (response.code() == 200) {
                        Toast.makeText(NewUserRegisterActivity.this, "Account created successfully!", Toast.LENGTH_SHORT).show();
                        Intent loginActivity = new Intent(NewUserRegisterActivity.this, LoginActivity.class);
                        startActivity(loginActivity);
                        finish();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<UserDetailedDTO> call, @NonNull Throwable t) {
                    Toast.makeText(NewUserRegisterActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
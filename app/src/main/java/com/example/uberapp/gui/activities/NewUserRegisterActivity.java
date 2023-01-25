package com.example.uberapp.gui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uberapp.R;
import com.example.uberapp.core.dto.NewUserDTO;
import com.example.uberapp.core.dto.UserDetailedDTO;
import com.example.uberapp.core.services.APIClient;
import com.example.uberapp.core.services.DriverService;
import com.example.uberapp.core.services.PassengerService;
import com.example.uberapp.gui.validators.TextValidator;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewUserRegisterActivity extends AppCompatActivity {
    PassengerService passengerService = APIClient.getClient().create(PassengerService.class);
    TextInputLayout firstName;
    TextInputLayout lastName;
    TextInputLayout phone;
    TextInputLayout address;
    TextInputLayout email;
    TextInputLayout password;
    TextInputLayout confirmPassword;
    Button createAccount;
    TextView signInTextView;
    String TEL_REGEX = "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s./0-9]{0,10}$";
    String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    String WHITESPACE_REGEX = "^(?!.* ).{6,20}$";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_register);

        firstName = findViewById(R.id.firstNameTextField);
        lastName = findViewById(R.id.lastNameTextField);
        phone = findViewById(R.id.phoneTextField);
        address = findViewById(R.id.addressTextField);
        email = findViewById(R.id.emailTextField);
        password = findViewById(R.id.passwordTextField);
        confirmPassword = findViewById(R.id.confirmPasswordTextField);

        createAccount = findViewById(R.id.buttonCreateAccount);
        signInTextView = findViewById(R.id.signInTextView);

        this.setupFormValidators();
        this.setupButtons();
    }

    public void setupButtons(){
        createAccount.setOnClickListener(v -> {
            this.triggerValidations();
            if (!isFormValid()){
                Toast.makeText(NewUserRegisterActivity.this, "Form is incorrect!", Toast.LENGTH_SHORT).show();
                return;
            }

            NewUserDTO newUserDTO = new NewUserDTO(firstName.getEditText().getText().toString(),
                    lastName.getEditText().getText().toString(), null,
                    phone.getEditText().getText().toString(),
                    email.getEditText().getText().toString(),
                    address.getEditText().getText().toString(),
                    password.getEditText().getText().toString());
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
                    else if (response.code() == 400){
                        Toast.makeText(NewUserRegisterActivity.this, "User with that email already exists!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<UserDetailedDTO> call, @NonNull Throwable t) {
                    Toast.makeText(NewUserRegisterActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            });
        });

        signInTextView.setOnClickListener(v -> {
            Intent loginActivity = new Intent(NewUserRegisterActivity.this, LoginActivity.class);
            startActivity(loginActivity);
            finish();
        });
    }



    public boolean isFormValid(){
        if (firstName.getError() == null && lastName.getError() == null &&
                phone.getError() == null && address.getError() == null &&
                email.getError() == null && password.getError() == null &&
                confirmPassword.getError() == null){
            return true;
        }
        return false;
    }

    public void triggerValidations(){
        firstName.getEditText().setText(firstName.getEditText().getText().toString());
        lastName.getEditText().setText(lastName.getEditText().getText().toString());
        phone.getEditText().setText(phone.getEditText().getText().toString());
        email.getEditText().setText(email.getEditText().getText().toString());
        address.getEditText().setText(address.getEditText().getText().toString());
        password.getEditText().setText(password.getEditText().getText().toString());
        confirmPassword.getEditText().setText(confirmPassword.getEditText().getText().toString());
    }

    public void setupFormValidators(){
        firstName.getEditText().addTextChangedListener(new TextValidator(firstName.getEditText()){
            @Override
            public void validate(TextView textView, String text) {
                if (firstName.getEditText().getText().toString().isEmpty()){
                    firstName.setError("Field is necessary!");
                }
                else{
                    firstName.setError(null);
                }
            }
        });

        lastName.getEditText().addTextChangedListener(new TextValidator(lastName.getEditText()){
            @Override
            public void validate(TextView textView, String text) {
                if (lastName.getEditText().getText().toString().isEmpty()){
                    lastName.setError("Field is necessary!");
                }
                else{
                    lastName.setError(null);
                }
            }
        });

        phone.getEditText().addTextChangedListener(new TextValidator(phone.getEditText()){
            @Override
            public void validate(TextView textView, String text) {
                if (phone.getEditText().getText().toString().isEmpty()){
                    phone.setError("Field is necessary!");
                }
                else if (!phone.getEditText().getText().toString().matches(TEL_REGEX)){
                    phone.setError("Not a valid phone number!");
                }
                else{
                    phone.setError(null);
                }
            }
        });

        address.getEditText().addTextChangedListener(new TextValidator(address.getEditText()){
            @Override
            public void validate(TextView textView, String text) {
                if (address.getEditText().getText().toString().isEmpty()){
                    address.setError("Field is necessary!");
                }
                else{
                    address.setError(null);
                }
            }
        });

        email.getEditText().addTextChangedListener(new TextValidator(email.getEditText()){
            @Override
            public void validate(TextView textView, String text) {
                if (email.getEditText().getText().toString().isEmpty()){
                    email.setError("Field is necessary!");
                }
                else if (!email.getEditText().getText().toString().matches(EMAIL_REGEX)){
                    email.setError("Not a valid email!");
                }
                else{
                    email.setError(null);
                }
            }
        });

        password.getEditText().addTextChangedListener(new TextValidator(password.getEditText()){
            @Override
            public void validate(TextView textView, String text) {
                if (password.getEditText().getText().toString().isEmpty()){
                    password.setError("Field is necessary!");
                }
                else if (password.getEditText().getText().toString().length() < 6){
                    password.setError("Password must be at least 6 characters long!");
                }
                else if (password.getEditText().getText().toString().length() > 20){
                    password.setError("Password can not be longer than 20 characters!");
                }
                else if (!password.getEditText().getText().toString().matches(WHITESPACE_REGEX)){
                    password.setError("Password can not contain whitespace characters!");
                }
                else{
                    if (!confirmPassword.getEditText().getText().toString().equals(
                            password.getEditText().getText().toString())){
                        confirmPassword.setError("Passwords are not matching!");
                    }
                    else{
                        confirmPassword.setError(null);
                    }
                    password.setError(null);
                }
            }
        });

        confirmPassword.getEditText().addTextChangedListener(new TextValidator(confirmPassword.getEditText()){
            @Override
            public void validate(TextView textView, String text) {
                if (confirmPassword.getEditText().getText().toString().isEmpty()){
                    confirmPassword.setError("Field is necessary!");
                }
                else if(!confirmPassword.getEditText().getText().toString().equals(
                        password.getEditText().getText().toString())){
                    confirmPassword.setError("Passwords are not matching!");
                }
                else {
                    confirmPassword.setError(null);
                }
            }
        });
    }
}
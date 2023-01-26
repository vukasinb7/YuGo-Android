package com.example.uberapp.gui.fragments.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.uberapp.R;
import com.example.uberapp.core.auth.TokenManager;
import com.example.uberapp.core.dto.UserDetailedDTO;
import com.example.uberapp.core.services.APIClient;
import com.example.uberapp.core.services.DriverService;
import com.example.uberapp.core.services.PassengerService;
import com.example.uberapp.gui.activities.NewUserRegisterActivity;
import com.example.uberapp.gui.validators.TextValidator;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfoFragment extends Fragment {
    UserDetailedDTO user;
    DriverService driverService = APIClient.getClient().create(DriverService.class);
    PassengerService passengerService = APIClient.getClient().create(PassengerService.class);
    TextInputLayout firstName;
    TextInputLayout lastName;
    TextInputLayout phone;
    TextInputLayout address;
    TextInputLayout email;
    LinearLayout optionsLayout;
    Button editAccount;
    Button saveChanges;
    Button cancel;
    String TEL_REGEX = "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s./0-9]{0,10}$";
    String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    public UserInfoFragment(UserDetailedDTO user) {
        this.user = user;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);

        firstName = view.findViewById(R.id.firstNameTextField);
        lastName = view.findViewById(R.id.lastNameTextField);
        phone = view.findViewById(R.id.phoneTextField);
        address = view.findViewById(R.id.addressTextField);
        email = view.findViewById(R.id.emailTextField);

        optionsLayout = view.findViewById(R.id.optionsLayoutInfo);

        editAccount = view.findViewById(R.id.buttonEditInfo);
        saveChanges = view.findViewById(R.id.buttonSaveInfo);
        cancel = view.findViewById(R.id.cancelInfoChange);

        this.setupFormValidators();
        this.setupButtons();

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        this.loadUserInfo();
    }

    public void setupButtons(){
        cancel.setOnClickListener(v -> {
            this.disableAndRefreshForm();
        });

        editAccount.setOnClickListener(v -> {
            optionsLayout.setVisibility(View.VISIBLE);
            editAccount.setVisibility(View.GONE);
            firstName.setEnabled(true);
            lastName.setEnabled(true);
            phone.setEnabled(true);
            address.setEnabled(true);
            email.setEnabled(true);
        });

        saveChanges.setOnClickListener(v -> {
            this.triggerValidations();
            if (!isFormValid()){
                Toast.makeText(getContext(), "Form is incorrect!", Toast.LENGTH_SHORT).show();
                return;
            }

            UserDetailedDTO updatedUser = new UserDetailedDTO(
                    firstName.getEditText().getText().toString(),
                    lastName.getEditText().getText().toString(),
                    user.getProfilePicture(),
                    phone.getEditText().getText().toString(),
                    email.getEditText().getText().toString(),
                    address.getEditText().getText().toString());
            Call<UserDetailedDTO> userCall;
            if (TokenManager.getRole().equals("DRIVER")){
                userCall = driverService.updateDriver(TokenManager.getUserId(), updatedUser);
            }
            else{
                userCall = passengerService.updatePassenger(TokenManager.getUserId(), updatedUser);
            }
            userCall.enqueue(new Callback<>() {
                @Override
                public void onResponse(@NonNull Call<UserDetailedDTO> call, @NonNull Response<UserDetailedDTO> response) {
                    if (response.code() == 200) {
                        user = response.body();
                        disableAndRefreshForm();
                    }
                    else{
                        Toast.makeText(getContext(), "Invalid input!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<UserDetailedDTO> call, @NonNull Throwable t) {
                    Toast.makeText(getContext(), "Oops, something went wrong!", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    public boolean isFormValid(){
        if (firstName.getError() == null && lastName.getError() == null &&
                phone.getError() == null && address.getError() == null &&
                email.getError() == null){
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
    }

    public void disableAndRefreshForm(){
        this.loadUserInfo();
        optionsLayout.setVisibility(View.GONE);
        editAccount.setVisibility(View.VISIBLE);
        firstName.setEnabled(false);
        lastName.setEnabled(false);
        phone.setEnabled(false);
        address.setEnabled(false);
        email.setEnabled(false);
    }

    public void loadUserInfo(){
        firstName.getEditText().setText(user.getName());
        lastName.getEditText().setText(user.getSurname());
        phone.getEditText().setText(user.getTelephoneNumber());
        address.getEditText().setText(user.getAddress());
        email.getEditText().setText(user.getEmail());
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
    }
}
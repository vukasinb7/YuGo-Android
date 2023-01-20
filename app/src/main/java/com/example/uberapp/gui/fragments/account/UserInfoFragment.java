package com.example.uberapp.gui.fragments.account;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.uberapp.R;
import com.example.uberapp.core.auth.TokenManager;
import com.example.uberapp.core.dto.UserDetailedDTO;
import com.example.uberapp.core.services.APIClient;
import com.example.uberapp.core.services.DriverService;
import com.example.uberapp.core.services.PassengerService;
import com.example.uberapp.gui.activities.LoginActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfoFragment extends Fragment {
    UserDetailedDTO user;
    View view;
    DriverService driverService = APIClient.getClient().create(DriverService.class);
    PassengerService passengerService = APIClient.getClient().create(PassengerService.class);
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);
        this.view = view;
        loadUserInfo();
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        this.loadUserInfo();
    }

    public void loadUserInfo(){
        EditText firstName = view.findViewById(R.id.editTextFirstName);
        firstName.setText(user.getName());
        EditText lastName = view.findViewById(R.id.editTextLastName);
        lastName.setText(user.getSurname());
        EditText phone = view.findViewById(R.id.editTextPhone);
        phone.setText(user.getTelephoneNumber());
        EditText address = view.findViewById(R.id.editTextAddress);
        address.setText(user.getAddress());
        address.setInputType(InputType.TYPE_CLASS_NUMBER);
        EditText email = view.findViewById(R.id.editTextEmail);
        email.setText(user.getEmail());
        email.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        Button editAccount = view.findViewById(R.id.buttonEditInfo);
        Button saveChanges = view.findViewById(R.id.buttonSaveInfo);

        editAccount.setOnClickListener(v -> {
            saveChanges.setVisibility(View.VISIBLE);
            editAccount.setVisibility(View.GONE);
            firstName.setEnabled(true);
            lastName.setEnabled(true);
            phone.setEnabled(true);
            address.setEnabled(true);
            email.setEnabled(true);
        });

        saveChanges.setOnClickListener(v -> {
            UserDetailedDTO updatedUser = new UserDetailedDTO(firstName.getText().toString(),
                    lastName.getText().toString(), user.getProfilePicture(),
                    phone.getText().toString(), email.getText().toString(), address.getText().toString());
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
                        saveChanges.setVisibility(View.GONE);
                        editAccount.setVisibility(View.VISIBLE);
                        firstName.setEnabled(false);
                        lastName.setEnabled(false);
                        phone.setEnabled(false);
                        address.setEnabled(false);
                        email.setEnabled(false);
                    }
                    else{
                        Toast.makeText(getContext(), "Invalid input!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<UserDetailedDTO> call, @NonNull Throwable t) {

                }
            });
        });
    }
}
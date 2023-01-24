package com.example.uberapp.gui.fragments.account;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.uberapp.R;
import com.example.uberapp.core.auth.TokenManager;
import com.example.uberapp.core.dto.ChangePasswordDTO;
import com.example.uberapp.core.dto.UserDetailedDTO;
import com.example.uberapp.core.services.APIClient;
import com.example.uberapp.core.services.UserService;
import com.example.uberapp.gui.activities.LoginActivity;
import com.google.android.material.button.MaterialButton;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PasswordFragment extends Fragment {
    UserDetailedDTO user;
    UserService userService = APIClient.getClient().create(UserService.class);
    EditText currentPassword;
    EditText newPassword;
    EditText confirmPassword;
    LinearLayout optionsLayout;
    MaterialButton editPassword;
    MaterialButton saveChanges;
    MaterialButton cancel;
    public PasswordFragment(UserDetailedDTO user) {
        this.user = user;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_password, container, false);


        currentPassword = view.findViewById(R.id.currentPasswordEditText);
        newPassword = view.findViewById(R.id.newPasswordEditText);
        confirmPassword = view.findViewById(R.id.confirmPasswordEditText);

        optionsLayout = view.findViewById(R.id.optionsLayoutPassword);

        editPassword = view.findViewById(R.id.buttonEditPassword);
        saveChanges = view.findViewById(R.id.buttonSavePassword);
        cancel = view.findViewById(R.id.cancelPasswordChange);

        cancel.setOnClickListener(v -> {
            optionsLayout.setVisibility(View.GONE);
            editPassword.setVisibility(View.VISIBLE);
            currentPassword.setEnabled(false);
            currentPassword.setText("");
            newPassword.setEnabled(false);
            newPassword.setText("");
            confirmPassword.setEnabled(false);
            confirmPassword.setText("");
        });

        editPassword.setOnClickListener(v -> {
            optionsLayout.setVisibility(View.VISIBLE);
            editPassword.setVisibility(View.GONE);
            currentPassword.setEnabled(true);
            newPassword.setEnabled(true);
            confirmPassword.setEnabled(true);
        });

        saveChanges.setOnClickListener(v -> {
            if (newPassword.getText().toString().equals(confirmPassword.getText().toString())){
                ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO(newPassword.getText().toString(),
                        currentPassword.getText().toString());
                Call<ResponseBody> updatePasswordCall = userService.updatePassword(TokenManager.getUserId(), changePasswordDTO);
                updatePasswordCall.enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        if (response.code() == 204) {
                            Toast.makeText(getContext(), "Password changed successfully!", Toast.LENGTH_SHORT).show();
                            TokenManager.clearToken();
                            Intent loginPage = new Intent(getActivity(), LoginActivity.class);
                            startActivity(loginPage);
                            getActivity().finish();
                        }
                        else{
                            Toast.makeText(getContext(), "Old password is not correct!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {

                    }
                });
            }
            else{
                Toast.makeText(getContext(), "Password confirmation is incorrect!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
    }
}
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.uberapp.R;
import com.example.uberapp.core.auth.TokenManager;
import com.example.uberapp.core.dto.ChangePasswordDTO;
import com.example.uberapp.core.dto.UserDetailedDTO;
import com.example.uberapp.core.services.APIClient;
import com.example.uberapp.core.services.UserService;
import com.example.uberapp.gui.activities.LoginActivity;
import com.example.uberapp.gui.validators.TextValidator;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PasswordFragment extends Fragment {
    UserDetailedDTO user;
    UserService userService = APIClient.getClient().create(UserService.class);
    TextInputLayout currentPassword;
    TextInputLayout newPassword;
    TextInputLayout confirmPassword;
    LinearLayout optionsLayout;
    MaterialButton editPassword;
    MaterialButton saveChanges;
    MaterialButton cancel;
    String WHITESPACE_REGEX = "^(?!.* ).{6,20}$";
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


        currentPassword = view.findViewById(R.id.currentPasswordTextField);
        newPassword = view.findViewById(R.id.newPasswordTextField);
        confirmPassword = view.findViewById(R.id.confirmPasswordTextField);

        optionsLayout = view.findViewById(R.id.optionsLayoutPassword);

        editPassword = view.findViewById(R.id.buttonEditPassword);
        saveChanges = view.findViewById(R.id.buttonSavePassword);
        cancel = view.findViewById(R.id.cancelPasswordChange);

        this.setupFormValidators();
        this.setupButtons();

        return view;
    }
    @Override
    public void onResume(){
        super.onResume();
    }

    public void setupButtons(){
        cancel.setOnClickListener(v -> {
            optionsLayout.setVisibility(View.GONE);
            editPassword.setVisibility(View.VISIBLE);
            currentPassword.setEnabled(false);
            currentPassword.getEditText().setText("");
            newPassword.setEnabled(false);
            newPassword.getEditText().setText("");
            confirmPassword.setEnabled(false);
            confirmPassword.getEditText().setText("");
        });

        editPassword.setOnClickListener(v -> {
            optionsLayout.setVisibility(View.VISIBLE);
            editPassword.setVisibility(View.GONE);
            currentPassword.setEnabled(true);
            newPassword.setEnabled(true);
            confirmPassword.setEnabled(true);
        });

        saveChanges.setOnClickListener(v -> {
            this.triggerValidations();
            if (!isFormValid()){
                Toast.makeText(getContext(), "Form is incorrect!", Toast.LENGTH_SHORT).show();
                return;
            }

            ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO(
                    newPassword.getEditText().getText().toString(),
                    currentPassword.getEditText().getText().toString());
            Call<ResponseBody> updatePasswordCall = userService.updatePassword(
                    TokenManager.getUserId(), changePasswordDTO);
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
        });
    }

    public boolean isFormValid(){
        if (currentPassword.getError() == null && newPassword.getError() == null &&
                confirmPassword.getError() == null){
            return true;
        }
        return false;
    }

    public void triggerValidations(){
        currentPassword.getEditText().setText(currentPassword.getEditText().getText().toString());
        newPassword.getEditText().setText(newPassword.getEditText().getText().toString());
        confirmPassword.getEditText().setText(confirmPassword.getEditText().getText().toString());
    }

    public void setupFormValidators(){
        currentPassword.getEditText().addTextChangedListener(new TextValidator(currentPassword.getEditText()){
            @Override
            public void validate(TextView textView, String text) {
                if (currentPassword.getEditText().getText().toString().isEmpty()){
                    currentPassword.setError("Field is necessary!");
                }
                else {
                    currentPassword.setError(null);
                }
            }
        });

        newPassword.getEditText().addTextChangedListener(new TextValidator(newPassword.getEditText()){
            @Override
            public void validate(TextView textView, String text) {
                if (newPassword.getEditText().getText().toString().isEmpty()){
                    newPassword.setError("Field is necessary!");
                }
                else if (newPassword.getEditText().getText().toString().length() < 6){
                    newPassword.setError("Password must be at least 6 characters long!");
                }
                else if (newPassword.getEditText().getText().toString().length() > 20){
                    newPassword.setError("Password can not be longer than 20 characters!");
                }
                else if (!newPassword.getEditText().getText().toString().matches(WHITESPACE_REGEX)){
                    newPassword.setError("Password can not contain whitespace characters!");
                }
                else{
                    if (!confirmPassword.getEditText().getText().toString().equals(
                            newPassword.getEditText().getText().toString())){
                        confirmPassword.setError("Passwords are not matching!");
                    }
                    else{
                        confirmPassword.setError(null);
                    }
                    newPassword.setError(null);
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
                        newPassword.getEditText().getText().toString())){
                    confirmPassword.setError("Passwords are not matching!");
                }
                else {
                    confirmPassword.setError(null);
                }
            }
        });
    }
}
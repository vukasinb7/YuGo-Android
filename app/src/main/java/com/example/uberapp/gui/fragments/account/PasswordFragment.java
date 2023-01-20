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
import android.widget.Toast;

import com.example.uberapp.R;
import com.example.uberapp.core.auth.TokenManager;
import com.example.uberapp.core.dto.ChangePasswordDTO;
import com.example.uberapp.core.dto.UserDetailedDTO;
import com.example.uberapp.core.services.APIClient;
import com.example.uberapp.core.services.UserService;
import com.example.uberapp.gui.activities.LoginActivity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PasswordFragment extends Fragment {
    UserDetailedDTO user;
    View view;
    UserService userService = APIClient.getClient().create(UserService.class);
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
        this.view = view;
        loadPasswordChange();

        return view;
    }

    public void loadPasswordChange(){
        EditText currentPassword = view.findViewById(R.id.editTextCurrentPassword);
        EditText newPassword = view.findViewById(R.id.editTextNewPassword);
        EditText confirmPassword = view.findViewById(R.id.editTextConfirmPassword);

        Button editPassword = view.findViewById(R.id.buttonEditPassword);
        Button saveChanges = view.findViewById(R.id.buttonSavePassword);

        editPassword.setOnClickListener(v -> {
            saveChanges.setVisibility(View.VISIBLE);
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
                        if (response.code() == 200) {
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
    }
}
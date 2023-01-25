package com.example.uberapp.gui.fragments.account;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.uberapp.R;
import com.example.uberapp.gui.activities.LoginActivity;

public class UserDocumentsFragment extends Fragment {
    public UserDocumentsFragment() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_documents, container, false);

        ActivityResultLauncher<Intent> pickDriversLicense =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                    if (result.getResultCode() == getActivity().RESULT_OK && result.getData() != null) {

                        Toast.makeText(getContext(), "Uspeo", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getContext(), "nije", Toast.LENGTH_SHORT).show();
                    }
                });

        Button buttonDriversLicence = view.findViewById(R.id.buttonPickDriversLicence);
        buttonDriversLicence.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickDriversLicense.launch(intent);
        });

        ActivityResultLauncher<Intent> pickRegistrationLicence =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                    if (result.getResultCode() == getActivity().RESULT_OK && result.getData() != null) {
                        Toast.makeText(getContext(), "Uspeo", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getContext(), "nije", Toast.LENGTH_SHORT).show();
                    }
                });

        Button buttonRegistrationLicence = view.findViewById(R.id.buttonPickRegistrationLicence);
        buttonRegistrationLicence.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickRegistrationLicence.launch(intent);
        });

        return view;
    }
}
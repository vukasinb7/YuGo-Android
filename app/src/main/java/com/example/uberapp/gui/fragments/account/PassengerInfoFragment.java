package com.example.uberapp.gui.fragments.account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.uberapp.R;
import com.example.uberapp.core.model.User;
import com.example.uberapp.core.tools.UserMockup;

public class PassengerInfoFragment extends Fragment {
    public PassengerInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        User user = UserMockup.getUsers().get(0);
        View view = inflater.inflate(R.layout.fragment_passenger_info, container, false);

        EditText name = view.findViewById(R.id.editTextFirstName);
        name.setText(user.getName());

        EditText lastName = view.findViewById(R.id.editTextLastName);
        lastName.setText(user.getLastName());

        EditText phoneNumber = view.findViewById(R.id.editTextPhone);
        phoneNumber.setText(user.getPhoneNumber());

        EditText email = view.findViewById(R.id.editTextEmail);
        email.setText(user.getEmail());

        EditText address = view.findViewById(R.id.editTextAddress);
        address.setText(user.getAddress());

        return view;
    }
}
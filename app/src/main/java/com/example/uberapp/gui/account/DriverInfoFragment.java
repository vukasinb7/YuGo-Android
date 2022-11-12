package com.example.uberapp.gui.account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.uberapp.R;
import com.example.uberapp.core.model.User;
import com.example.uberapp.core.tools.UserMockup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DriverInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DriverInfoFragment extends Fragment {


    public DriverInfoFragment() {
        // Required empty public constructor
    }
    public static DriverInfoFragment newInstance() {
        DriverInfoFragment fragment = new DriverInfoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_driver_info, container, false);
        User user = UserMockup.getUsers().get(1);

        EditText name = view.findViewById(R.id.editTextFirstName);
        name.setText(user.getName());

        EditText lastName = view.findViewById(R.id.editTextLastName);
        lastName.setText(user.getLastName());

        EditText phoneNumber = view.findViewById(R.id.editTextPhone);
        phoneNumber.setText(user.getPhoneNumber());

        EditText email = view.findViewById(R.id.editTextEmail);
        email.setText(user.getEmail());

        return view;
    }
}
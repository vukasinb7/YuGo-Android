package com.example.uberapp.gui.fragments.account;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.uberapp.R;
import com.example.uberapp.core.model.User;
import com.example.uberapp.core.tools.UserMockup;

public class PassengerInfoFragment extends Fragment {
    public PassengerInfoFragment() {
        // Required empty public constructor
    }
    public void setupEditText(View view,String type,String value){
        EditText editText=view.findViewById(R.id.editTextTextPersonName);
        ImageButton edit= view.findViewById(R.id.editBtnDriverName);
        ImageButton accept= view.findViewById(R.id.checkBtnDriverName);
        ImageButton cancel= view.findViewById(R.id.cancelBtnDriverName);
        editText.setText(value);
        switch(type) {
            case "phone":
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case "email":
                editText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                break;
            default:
        }



        EditText finalEditText = editText;
        ImageButton finalEdit = edit;
        ImageButton finalAccept = accept;
        ImageButton finalCancel = cancel;
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalEditText.setBackgroundTintList( ColorStateList.valueOf(Color.WHITE) );
                finalEditText.setEnabled(true);
                finalEditText.setTextColor(getResources().getColor(R.color.dark_gray));
                finalEdit.setVisibility(View.INVISIBLE);
                finalAccept.setVisibility(View.VISIBLE);
                finalCancel.setVisibility(View.VISIBLE);
            }
        });
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalEditText.setBackgroundTintList( ColorStateList.valueOf(Color.TRANSPARENT ) );
                finalEditText.setEnabled(false);
                finalEditText.setTextColor(getResources().getColor(R.color.dark_gray_pewter));
                finalEdit.setVisibility(View.VISIBLE);
                finalAccept.setVisibility(View.INVISIBLE);
                finalCancel.setVisibility(View.INVISIBLE);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalEditText.setBackgroundTintList( ColorStateList.valueOf(Color.TRANSPARENT ) );
                finalEditText.setEnabled(false);
                finalEditText.setTextColor(getResources().getColor(R.color.dark_gray_pewter));
                finalEdit.setVisibility(View.VISIBLE);
                finalAccept.setVisibility(View.INVISIBLE);
                finalCancel.setVisibility(View.INVISIBLE);
            }
        });
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
        setupEditText(view.findViewById(R.id.nameContainerPassenger),"name",user.getName());
        setupEditText(view.findViewById(R.id.lastNameContainerPassenger),"lastname",user.getLastName());
        setupEditText(view.findViewById(R.id.phoneContainerPassenger),"phone",user.getPhoneNumber());
        setupEditText(view.findViewById(R.id.emailContainerPassenger),"email",user.getEmail());
        setupEditText(view.findViewById(R.id.addressContainerPassenger),"email",user.getAddress());

        return view;
    }
}
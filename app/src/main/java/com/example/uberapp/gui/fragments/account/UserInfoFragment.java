package com.example.uberapp.gui.fragments.account;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.example.uberapp.R;
import com.example.uberapp.core.dto.UserDetailedDTO;

public class UserInfoFragment extends Fragment {
    UserDetailedDTO user;
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

        setupEditText(view.findViewById(R.id.nameContainer),"name",user.getName());
        setupEditText(view.findViewById(R.id.lastNameContainer),"lastname",user.getSurname());
        setupEditText(view.findViewById(R.id.phoneContainer),"phone",user.getTelephoneNumber());
        setupEditText(view.findViewById(R.id.emailContainer),"email",user.getEmail());
        setupEditText(view.findViewById(R.id.addressContainer),"email",user.getAddress());

        return view;
    }
    public void setupEditText(View view, String type, String value){
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
        edit.setOnClickListener(view1 -> {
            finalEditText.setBackgroundTintList( ColorStateList.valueOf(getResources().getColor(R.color.pewter_dark_blue)) );
            finalEditText.setEnabled(true);
            finalEditText.setTextColor(getResources().getColor(R.color.dark_gray));
            finalEdit.setVisibility(View.INVISIBLE);
            finalAccept.setVisibility(View.VISIBLE);
            finalCancel.setVisibility(View.VISIBLE);
        });

        accept.setOnClickListener(view12 -> {
            finalEditText.setBackgroundTintList( ColorStateList.valueOf(Color.TRANSPARENT ) );
            finalEditText.setEnabled(false);
            finalEditText.setTextColor(getResources().getColor(R.color.dark_gray_pewter));
            finalEdit.setVisibility(View.VISIBLE);
            finalAccept.setVisibility(View.INVISIBLE);
            finalCancel.setVisibility(View.INVISIBLE);
        });

        cancel.setOnClickListener(view13 -> {
            finalEditText.setBackgroundTintList( ColorStateList.valueOf(Color.TRANSPARENT ) );
            finalEditText.setEnabled(false);
            finalEditText.setTextColor(getResources().getColor(R.color.dark_gray_pewter));
            finalEdit.setVisibility(View.VISIBLE);
            finalAccept.setVisibility(View.INVISIBLE);
            finalCancel.setVisibility(View.INVISIBLE);
        });
    }
}
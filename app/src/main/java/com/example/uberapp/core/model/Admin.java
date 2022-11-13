package com.example.uberapp.core.model;

import android.graphics.Bitmap;

public class Admin extends User{

    public Admin(String id, String name, String lastName, Integer profilePicture, String phoneNumber, String email, String address, String password, Boolean isBlocked) {
        super(id, name, lastName, profilePicture, phoneNumber, email, address, password, isBlocked);
    }
}

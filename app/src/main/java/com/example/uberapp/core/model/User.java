package com.example.uberapp.core.model;

import android.graphics.Bitmap;

public abstract class User {
    private String id;
    private String name;
    private String lastName;
    private Bitmap profilePicture;
    private String phoneNumber;
    private String email;
    private String address;
    private String password;
    private Boolean isBlocked;

    public User(String id, String name, String lastName, Bitmap profilePicture, String phoneNumber, String email, String address, String password, Boolean isBlocked) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.profilePicture = profilePicture;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.password = password;
        this.isBlocked = isBlocked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Bitmap getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Bitmap profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getBlocked() {
        return isBlocked;
    }

    public void setBlocked(Boolean blocked) {
        isBlocked = blocked;
    }
}

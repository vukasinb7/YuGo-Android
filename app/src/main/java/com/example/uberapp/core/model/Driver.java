package com.example.uberapp.core.model;

import android.graphics.Bitmap;

public class Driver extends User{
    private Bitmap driversLicence;
    private Bitmap registrationLicence;
    private Boolean isActive;
    private Vehicle vehicle;

    public Driver(String id, String name, String lastName, Bitmap profilePicture, String phoneNumber, String email, String address, String password, Boolean isBlocked, Bitmap driversLicence, Bitmap registrationLicence, Boolean isActive, Vehicle vehicle) {
        super(id, name, lastName, profilePicture, phoneNumber, email, address, password, isBlocked);
        this.driversLicence = driversLicence;
        this.registrationLicence = registrationLicence;
        this.isActive = isActive;
        this.vehicle = vehicle;
    }

    public Bitmap getDriversLicence() {
        return driversLicence;
    }

    public void setDriversLicence(Bitmap driversLicence) {
        this.driversLicence = driversLicence;
    }

    public Bitmap getRegistrationLicence() {
        return registrationLicence;
    }

    public void setRegistrationLicence(Bitmap registrationLicence) {
        this.registrationLicence = registrationLicence;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}

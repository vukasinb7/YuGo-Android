package com.example.uberapp.core.dto;

import com.example.uberapp.core.model.LocationInfo;

public class LocationDTO {
    private String address;
    private double latitude;
    private double longitude;

    public LocationDTO(String address, double latitude, double longitude) {
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public LocationDTO(LocationInfo locationInfo){
        this.address = locationInfo.getAddress();
        this.latitude = locationInfo.getLatitude();
        this.longitude = locationInfo.getLongitude();
    }

    public LocationDTO() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}

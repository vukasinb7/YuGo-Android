package com.example.uberapp.core.dto;


import com.example.uberapp.core.model.Location;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter @Setter
@NoArgsConstructor
public class LocationInOut {
    private String address;
    private double latitude;
    private double longitude;

    public LocationInOut(String address, double latitude, double longitude) {
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public LocationInOut(Location location){
        this(location.getAddress(), location.getLatitude(), location.getLongitude());
    }
}

package com.example.uberapp.core.dto;

import com.example.uberapp.core.model.VehicleType;

import java.util.List;

public class RideRequestDTO {
    public List<PathDTO> locations;
    public List<UserSimpleDTO> passengers;
    public String vehicleType;
    public boolean babyTransport;
    public boolean petTransport;
    public String dateTime;
}

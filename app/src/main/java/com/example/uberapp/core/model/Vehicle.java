package com.example.uberapp.core.model;


import com.example.uberapp.core.dto.VehicleIn;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter
@NoArgsConstructor
public class Vehicle {
    private Integer id;
    private Driver driver;
    private VehicleType vehicleType;
    private String model;
    private String licencePlateNumber;
    private int numberOfSeats;
    private Location currentLocation;
    private Boolean areBabiesAllowed;
    private Boolean arePetsAllowed;

    public Vehicle(VehicleIn vehicleIn){
        this.vehicleType = vehicleIn.getVehicleType();
        this.model = vehicleIn.getModel();
        this.licencePlateNumber = vehicleIn.getLicenseNumber();
        Location location = new Location();
        location.setAddress(vehicleIn.getCurrentLocation().getAddress());
        location.setLatitude(vehicleIn.getCurrentLocation().getLatitude());
        location.setLongitude(vehicleIn.getCurrentLocation().getLongitude());
        this.currentLocation = location;
        this.numberOfSeats = vehicleIn.getPassengerSeats();
        this.areBabiesAllowed = vehicleIn.getBabyTransport();
        this.arePetsAllowed = vehicleIn.getPetTransport();
    }
}

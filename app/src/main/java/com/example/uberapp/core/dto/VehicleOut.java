package com.example.uberapp.core.dto;

import com.example.uberapp.core.model.Vehicle;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class VehicleOut {
    private Integer id;
    private Integer driverId;
    private String vehicleType;
    private String model;
    private String licenseNumber;
    private LocationInOut currentLocation;
    private int passengerSeats;
    private Boolean babyTransport;
    private Boolean petTransport;

    public VehicleOut(Integer id, Integer driverId, String vehicleType, String model, String licenseNumber, LocationInOut currentLocation, int passengerSeats, Boolean babyTransport, Boolean petTransport) {
        this.id = id;
        this.driverId = driverId;
        this.vehicleType = vehicleType;
        this.model = model;
        this.licenseNumber = licenseNumber;
        this.currentLocation = currentLocation;
        this.passengerSeats = passengerSeats;
        this.babyTransport = babyTransport;
        this.petTransport = petTransport;
    }

    public VehicleOut(Integer id, String vehicleType, String model, String licenseNumber, LocationInOut currentLocation, int passengerSeats, Boolean babyTransport, Boolean petTransport) {
        this.id = id;
        this.vehicleType = vehicleType;
        this.model = model;
        this.licenseNumber = licenseNumber;
        this.currentLocation = currentLocation;
        this.passengerSeats = passengerSeats;
        this.babyTransport = babyTransport;
        this.petTransport = petTransport;
    }

    public VehicleOut(Vehicle vehicle){
        this(
                vehicle.getId(),
                vehicle.getDriver().getId(),
                vehicle.getVehicleType().toString(),
                vehicle.getModel(),
                vehicle.getLicencePlateNumber(),
                new LocationInOut(vehicle.getCurrentLocation()),
                vehicle.getNumberOfSeats(),
                vehicle.getAreBabiesAllowed(),
                vehicle.getArePetsAllowed()
        );
    }
}

package com.example.uberapp.core.model;

public class Vehicle {
    private String id;
    private String model;
    private String registerPlateNumber;
    private int seatsCount;
    private Boolean babiesAllowed;
    private Boolean petsAllowed;
    private Driver driver;
    private VehicleType vehicleType;

    public Vehicle(String id, String model, String registerPlateNumber, int seatsCount, Boolean babiesAllowed, Boolean petsAllowed, Driver driver, VehicleType vehicleType) {
        this.id = id;
        this.model = model;
        this.registerPlateNumber = registerPlateNumber;
        this.seatsCount = seatsCount;
        this.babiesAllowed = babiesAllowed;
        this.petsAllowed = petsAllowed;
        this.driver = driver;
        this.vehicleType = vehicleType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRegisterPlateNumber() {
        return registerPlateNumber;
    }

    public void setRegisterPlateNumber(String registerPlateNumber) {
        this.registerPlateNumber = registerPlateNumber;
    }

    public int getSeatsCount() {
        return seatsCount;
    }

    public void setSeatsCount(int seatsCount) {
        this.seatsCount = seatsCount;
    }

    public Boolean getBabiesAllowed() {
        return babiesAllowed;
    }

    public void setBabiesAllowed(Boolean babiesAllowed) {
        this.babiesAllowed = babiesAllowed;
    }

    public Boolean getPetsAllowed() {
        return petsAllowed;
    }

    public void setPetsAllowed(Boolean petsAllowed) {
        this.petsAllowed = petsAllowed;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }
}

package com.example.uberapp.core.model;

public class VehicleType {
    private String id;
    private double pricePerUnit;
    private VehicleCategory vehicleCategory;

    public VehicleType(String id, double pricePerUnit, VehicleCategory vehicleCategory) {
        this.id = id;
        this.pricePerUnit = pricePerUnit;
        this.vehicleCategory = vehicleCategory;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public VehicleCategory getVehicleCategory() {
        return vehicleCategory;
    }

    public void setVehicleCategory(VehicleCategory vehicleCategory) {
        this.vehicleCategory = vehicleCategory;
    }
}

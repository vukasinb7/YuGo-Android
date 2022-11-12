package com.example.uberapp.core.model;

import android.graphics.Bitmap;

public class VehicleType {
    private String id;
    private double pricePerUnit;
    private VehicleCategory vehicleCategory;
    private Bitmap icon;

    public VehicleType(String id, double pricePerUnit, VehicleCategory vehicleCategory, Bitmap icon) {
        this.id = id;
        this.pricePerUnit = pricePerUnit;
        this.vehicleCategory = vehicleCategory;
        this.icon = icon;
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

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }
}

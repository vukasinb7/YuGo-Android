package com.example.uberapp.core.model;

import android.graphics.Bitmap;

public class VehicleType {
    private String id;
    private double pricePerUnit;
    private VehicleCategory vehicleCategory;
    private int icon;

    public VehicleType(String id, double pricePerUnit, VehicleCategory vehicleCategory, int icon) {
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

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}

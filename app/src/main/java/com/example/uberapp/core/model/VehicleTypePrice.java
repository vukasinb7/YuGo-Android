package com.example.uberapp.core.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class VehicleTypePrice {
    private Integer id;
    private VehicleType vehicleType;
    private double pricePerKM;
    private String imagePath;

    @Override
    public String toString() {
        return this.vehicleType.toString();
    }
}

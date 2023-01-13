package com.example.uberapp.core.dto;

import com.example.uberapp.core.model.VehicleType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleTypeOutReduced {
    private Integer id;
    private VehicleType vehicleType;
    private String imgPath;
    private double pricePerKm;
}

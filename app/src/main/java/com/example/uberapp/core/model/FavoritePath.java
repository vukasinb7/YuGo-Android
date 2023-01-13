package com.example.uberapp.core.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class FavoritePath {
    private String favoriteName;
    private List<Path> locations;
    private Set<Passenger> passengers;
    private Passenger owner;
    private VehicleTypePrice vehicleTypePrice;
    private Boolean babyTransport;
    private Boolean petTransport;
    private Integer id;

    public FavoritePath(String favoriteName, List<Path> locations, Set<Passenger> passengers, VehicleTypePrice vehicleTypePrice, Boolean babyTransport, Boolean petTransport) {
        this.favoriteName = favoriteName;
        this.locations = locations;
        this.passengers = passengers;
        this.vehicleTypePrice = vehicleTypePrice;
        this.babyTransport = babyTransport;
        this.petTransport = petTransport;
    }
}

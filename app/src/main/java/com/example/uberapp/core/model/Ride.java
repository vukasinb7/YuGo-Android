package com.example.uberapp.core.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter @Setter
public class Ride {
    public Integer id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double totalCost;
    private Driver driver;
    private Set<Passenger> passengers = new HashSet<>();

    private List<Path> locations;
    private int estimatedTimeInMinutes;
    private Set<RideReview> rideReviews = new HashSet<RideReview>();
    private RideStatus status;
    private Rejection rejection;
    private Boolean isPanicPressed;
    private Boolean babyTransport;
    private Boolean petTransport;
    private VehicleTypePrice vehicleTypePrice;

    public Ride(LocalDateTime startTime, LocalDateTime endTime, double price, Driver driver, Set<Passenger> passengers, List<Path> paths, int estimatedTime, Set<RideReview> rideReviews, RideStatus status, Rejection rejection, Boolean isPanicPressed, Boolean includesBabies, Boolean includesPets, VehicleTypePrice vehicleTypePrice) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalCost = price;
        this.driver = driver;
        this.passengers = passengers;
        this.locations = paths;
        this.estimatedTimeInMinutes = estimatedTime;
        this.rideReviews = rideReviews;
        this.status = status;
        this.rejection = rejection;
        this.isPanicPressed = isPanicPressed;
        this.babyTransport = includesBabies;
        this.petTransport = includesPets;
        this.vehicleTypePrice = vehicleTypePrice;
    }
}

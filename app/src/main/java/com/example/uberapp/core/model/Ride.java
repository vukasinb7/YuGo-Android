package com.example.uberapp.core.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

public class Ride {
    private String id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double totalPrice;
    private Duration estimatedTime;
    private Vehicle vehicle;
    private Boolean isPanicActivated;
    private Boolean includesBabies;
    private Boolean includesPets;
    private Boolean isSplitFare;
    private List<Passenger> passengers;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Duration getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(Duration estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Boolean getPanicActivated() {
        return isPanicActivated;
    }

    public void setPanicActivated(Boolean panicActivated) {
        isPanicActivated = panicActivated;
    }

    public Boolean getIncludesBabies() {
        return includesBabies;
    }

    public void setIncludesBabies(Boolean includesBabies) {
        this.includesBabies = includesBabies;
    }

    public Boolean getIncludesPets() {
        return includesPets;
    }

    public void setIncludesPets(Boolean includesPets) {
        this.includesPets = includesPets;
    }

    public Boolean getSplitFare() {
        return isSplitFare;
    }

    public void setSplitFare(Boolean splitFare) {
        isSplitFare = splitFare;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    public RideStatus getStatus() {
        return status;
    }

    public void setStatus(RideStatus status) {
        this.status = status;
    }

    private RideStatus status;


    public Ride(String id, LocalDateTime startTime, LocalDateTime endTime, double totalPrice, Duration estimatedTime, Vehicle vehicle, Boolean isPanicActivated, Boolean includesBabies, Boolean includesPets, Boolean isSplitFare, List<Passenger> passengers, RideStatus status) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalPrice = totalPrice;
        this.estimatedTime = estimatedTime;
        this.vehicle = vehicle;
        this.isPanicActivated = isPanicActivated;
        this.includesBabies = includesBabies;
        this.includesPets = includesPets;
        this.isSplitFare = isSplitFare;
        this.passengers = passengers;
        this.status = status;
    }
}

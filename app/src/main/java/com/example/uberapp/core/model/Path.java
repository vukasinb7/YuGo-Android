package com.example.uberapp.core.model;

import java.time.LocalDateTime;
import java.time.Period;

public class Path {
    private String id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double length;
    private Period estimatedTime;
    private double price;
    private int order;
    private Location startingLocation;
    private Location endLocation;
    private Ride ride;

    public Path(String id, LocalDateTime startTime, LocalDateTime endTime, double length, Period estimatedTime, double price, int order, Location startingLocation, Location endLocation, Ride ride) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.length = length;
        this.estimatedTime = estimatedTime;
        this.price = price;
        this.order = order;
        this.startingLocation = startingLocation;
        this.endLocation = endLocation;
        this.ride = ride;
    }

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

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public Period getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(Period estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Location getStartingLocation() {
        return startingLocation;
    }

    public void setStartingLocation(Location startingLocation) {
        this.startingLocation = startingLocation;
    }

    public Location getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(Location endLocation) {
        this.endLocation = endLocation;
    }

    public Ride getRide() {
        return ride;
    }

    public void setRide(Ride ride) {
        this.ride = ride;
    }
}

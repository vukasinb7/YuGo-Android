package com.example.uberapp.core.model;

import java.time.LocalDateTime;
import java.time.Period;

public class Path {
    private LocationInfo departure;
    private LocationInfo destination;

    public Path() {
    }

    public Path(LocationInfo departure, LocationInfo destination) {
        this.departure = departure;
        this.destination = destination;
    }

    public LocationInfo getDeparture() {
        return departure;
    }

    public void setDeparture(LocationInfo departure) {
        this.departure = departure;
    }

    public LocationInfo getDestination() {
        return destination;
    }

    public void setDestination(LocationInfo destination) {
        this.destination = destination;
    }
}

package com.example.uberapp.core.model;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

public class Ride {
    private String id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double totalPrice;
    private Period estimatedTime;
    private Vehicle vehicle;
    private Boolean isPanicActivated;
    private Boolean includesBabies;
    private Boolean includesPets;
    private Boolean isSplitFare;
    private List<Passenger> passengers;
    private RideStatus status;

}

package com.example.uberapp.core.model;

import java.time.LocalDateTime;

public class Rejection {
    private String id;
    private String reason;
    private LocalDateTime time;
    private User passenger;
    private Ride ride;

    public Rejection(String id, String reason, LocalDateTime time, User passenger, Ride ride) {
        this.id = id;
        this.reason = reason;
        this.time = time;
        this.passenger = passenger;
        this.ride = ride;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public User getPassenger() {
        return passenger;
    }

    public void setPassenger(User passenger) {
        this.passenger = passenger;
    }

    public Ride getRide() {
        return ride;
    }

    public void setRide(Ride ride) {
        this.ride = ride;
    }
}

package com.example.uberapp.core.model;

public class Review {
    private String id;
    private Passenger passenger;
    private int rating;
    private String comment;
    private Ride ride;

    public Review(String id, Passenger passenger, int rating, String comment, Ride ride) {
        this.id = id;
        this.passenger = passenger;
        this.rating = rating;
        this.comment = comment;
        this.ride = ride;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Ride getRide() {
        return ride;
    }

    public void setRide(Ride ride) {
        this.ride = ride;
    }
}

package com.example.uberapp.core.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter
@NoArgsConstructor
public class RideReview {
    private String comment;
    private int rating;
    private Ride ride;
    private Passenger passenger;
    private ReviewType type;
    private Integer id;

    public RideReview(String comment, int rating, Ride ride, Passenger passenger, ReviewType type) {
        this.comment = comment;
        this.rating = rating;
        this.ride = ride;
        this.passenger = passenger;
        this.type = type;
    }
}

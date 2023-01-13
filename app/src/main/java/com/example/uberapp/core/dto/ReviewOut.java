package com.example.uberapp.core.dto;

import com.example.uberapp.core.dto.UserSimplifiedOut;
import com.example.uberapp.core.model.Passenger;
import com.example.uberapp.core.model.RideReview;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter @Setter
@NoArgsConstructor
public class ReviewOut {
    private Integer id;
    private int rating;
    private String comment;
    private UserSimplifiedOut passenger;

    public ReviewOut(Integer id, int rating, String review, Passenger passenger) {
        this.id = id;
        this.rating = rating;
        this.comment = review;
        this.passenger=new UserSimplifiedOut(passenger);
    }

    public ReviewOut(RideReview vehicleReview) {
        this(vehicleReview.getId(), vehicleReview.getRating(), vehicleReview.getComment(),vehicleReview.getPassenger());
    }
}

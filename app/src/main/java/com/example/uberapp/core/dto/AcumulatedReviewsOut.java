package com.example.uberapp.core.dto;

import com.example.uberapp.core.model.RideReview;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class AcumulatedReviewsOut {
    ReviewOut vehicleReview;
    ReviewOut driverReview;

    /*public AcumulatedReviewsOut(RideReview vehicleReviews, RideReview driverReviews){
        this.vehicleReview=RideReviewMapper.fromRideReviewtoDTO(vehicleReviews);
        this.driverReview=RideReviewMapper.fromRideReviewtoDTO(driverReviews);
    }*/

}

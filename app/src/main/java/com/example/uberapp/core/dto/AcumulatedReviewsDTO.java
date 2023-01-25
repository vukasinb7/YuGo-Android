package com.example.uberapp.core.dto;

public class AcumulatedReviewsDTO {
    ReviewResponseDTO vehicleReview;
    ReviewResponseDTO driverReview;

    public AcumulatedReviewsDTO() {
    }

    public AcumulatedReviewsDTO(ReviewResponseDTO vehicleReview, ReviewResponseDTO driverReview) {
        this.vehicleReview = vehicleReview;
        this.driverReview = driverReview;
    }

    public ReviewResponseDTO getVehicleReview() {
        return vehicleReview;
    }

    public void setVehicleReview(ReviewResponseDTO vehicleReview) {
        this.vehicleReview = vehicleReview;
    }

    public ReviewResponseDTO getDriverReview() {
        return driverReview;
    }

    public void setDriverReview(ReviewResponseDTO driverReview) {
        this.driverReview = driverReview;
    }
}

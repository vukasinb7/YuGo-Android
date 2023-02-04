package com.example.uberapp.core.services;

import com.example.uberapp.core.dto.AcumulatedReviewsDTO;
import com.example.uberapp.core.dto.ReviewRequestDTO;
import com.example.uberapp.core.dto.ReviewResponseDTO;
import com.example.uberapp.core.dto.RideDetailedDTO;
import com.example.uberapp.core.dto.RideRequestDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ReviewService {

    @POST("/api/review/{rideId}/vehicle")
    Call<ReviewResponseDTO> createVehicleReview(@Body ReviewRequestDTO reviewIn,@Path("rideId") Integer rideId);

    @POST("/api/review/{rideId}/driver")
    Call<ReviewResponseDTO> createRideReview(@Body ReviewRequestDTO reviewIn,@Path("rideId") Integer rideId);

    @GET("/api/review/{id}")
    Call<List<AcumulatedReviewsDTO>> getRideReviews(@Path("id") Integer rideId);
}

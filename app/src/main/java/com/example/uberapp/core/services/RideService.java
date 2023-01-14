package com.example.uberapp.core.services;

import com.example.uberapp.core.dto.RideDetailedDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RideService {
    @GET("/api/ride/driver/{id}/active")
    Call<RideDetailedDTO> getActiveDriverRide(@Path("id") Integer driverId);

    @GET("/api/ride/passenger/{id}/active")
    Call<RideDetailedDTO> getActivePassengerRide(@Path("id") Integer passengerId);
    @GET("/api/ride/{id}")
    Call<RideDetailedDTO> getRide(@Path("id") Integer rideId);
}

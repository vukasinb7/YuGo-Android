package com.example.uberapp.core.services;

import com.example.uberapp.core.dto.AllRidesDTO;
import com.example.uberapp.core.dto.RideDetailedDTO;
import com.example.uberapp.core.dto.StatisticsDTO;
import com.example.uberapp.core.dto.UserDetailedDTO;
import com.example.uberapp.core.dto.VehicleDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DriverService {
    @GET("/api/driver/{id}")
    Call<UserDetailedDTO> getDriver(@Path("id") Integer id);

    @GET("/api/driver/{id}/rides")
    Call<AllRidesDTO> getDriverRides(@Path("id") Integer id);

    @PUT("/api/driver/{id}")
    Call<UserDetailedDTO> updateDriver(@Path("id") Integer id, @Body UserDetailedDTO userDetailedDTO);

    @GET("/api/driver/{driverId}/vehicle")
    Call<VehicleDTO> getVehicle(@Path("driverId") Integer driverId);

    @GET("/api/driver/{driverId}/statistics")
    Call<StatisticsDTO> getDriverStatistics(@Path("driverId") Integer driverId);
}

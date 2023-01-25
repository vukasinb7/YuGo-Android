package com.example.uberapp.core.services;

import com.example.uberapp.core.dto.NewVehicleDTO;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface VehicleService {
    @POST("/api/vehicle/{driverId}/makeRequest")
    Call<ResponseBody> makeVehicleChangeRequest(@Path("driverId") Integer driverId, @Body NewVehicleDTO newVehicleDTO);
}

package com.example.uberapp.core.services;

import com.example.uberapp.core.dto.LocationDTO;
import com.example.uberapp.core.dto.NewVehicleDTO;
import com.example.uberapp.core.dto.VehicleDTO;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface VehicleService {
    @POST("/api/vehicle/{driverId}/makeRequest")
    Call<ResponseBody> makeVehicleChangeRequest(@Path("driverId") Integer driverId, @Body NewVehicleDTO newVehicleDTO);

    @PUT("api/vehicle/{vehicleID}/location")
    Call<Void> updateVehicleLocation(@Path("vehicleID") Integer vehicleID, @Body LocationDTO location);

    @GET("api/vehicle/vehicles")
    Call<List<VehicleDTO>> getVehicles();
}

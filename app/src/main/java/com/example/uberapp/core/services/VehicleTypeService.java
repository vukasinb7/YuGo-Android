package com.example.uberapp.core.services;

import com.example.uberapp.core.dto.VehicleTypeDTO;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface VehicleTypeService {
    @GET("/api/vehicleType")
    Observable<List<VehicleTypeDTO>> getVehicleTypes();
}

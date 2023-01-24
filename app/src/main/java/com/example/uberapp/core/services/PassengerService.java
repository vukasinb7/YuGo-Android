package com.example.uberapp.core.services;

import com.example.uberapp.core.dto.NewUserDTO;
import com.example.uberapp.core.dto.UserDetailedDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PassengerService {
        @POST("/api/passenger")
        Call<UserDetailedDTO> createPassenger(@Body NewUserDTO newUserDTO);
        @GET("/api/passenger/{id}")
        Call<UserDetailedDTO> getPassenger(@Path("id") Integer id);

        @PUT("/api/passenger/{id}")
        Call<UserDetailedDTO> updatePassenger(@Path("id") Integer id, @Body UserDetailedDTO userDetailedDTO);
}

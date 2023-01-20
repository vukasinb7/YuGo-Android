package com.example.uberapp.core.services;

import com.example.uberapp.core.dto.UserDetailedDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PassengerService {
        @GET("/api/passenger/{id}")
        Call<UserDetailedDTO> getPassenger(@Path("id") Integer id);

        @PUT("/api/passenger/{id}")
        Call<UserDetailedDTO> updatePassenger(@Path("id") Integer id, @Body UserDetailedDTO userDetailedDTO);
}

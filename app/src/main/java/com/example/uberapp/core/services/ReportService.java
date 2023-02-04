package com.example.uberapp.core.services;

import com.example.uberapp.core.dto.AcumulatedReviewsDTO;
import com.example.uberapp.core.dto.ReportDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ReportService {
    @GET("/api/report/{id}/ridesPerDay")
    Call<ReportDTO> ridesPerDay(@Path("id") Integer userId, @Query("from") String from,@Query("to") String to);

    @GET("/api/report/{id}/kilometersPerDay")
    Call<ReportDTO> kilometersPerDay(@Path("id") Integer userId, @Query("from") String from,@Query("to") String to);

    @GET("/api/report/{id}/totalCostPerDay")
    Call<ReportDTO> spendingsPerDay(@Path("id") Integer userId, @Query("from") String from,@Query("to") String to);

}

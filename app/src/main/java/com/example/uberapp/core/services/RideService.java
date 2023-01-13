package com.example.uberapp.core.services;

import android.database.Observable;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RideService
{
    @GET("api/ride/driver/{id}/active")
    Observable<ResponseBody> getActiveDriver(@Path("id") Integer id);
}

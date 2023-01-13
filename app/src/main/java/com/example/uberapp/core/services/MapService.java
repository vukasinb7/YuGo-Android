package com.example.uberapp.core.services;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MapService {
    @GET("/reverse?format=json")
    Observable<ResponseBody> reverseSearch(@Query("lat") String lat, @Query("lon") String lng);

    @GET("/search?format=json&polygon_geojson=1&countrycodes=rs&format=jsonv2")
    Observable<ResponseBody> search(@Query("q") String address);
}

package com.example.uberapp.core.services;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RoutingService {

    @GET("/routed-car/route/v1/driving/{lng1},{lat1};{lng2},{lat2}?geometries=geojson&overview=false&alternatives=true&steps=true")
    Observable<ResponseBody> getRoute(@Path("lat1") double departureLat,@Path("lng1") double departureLng,@Path("lat2") double destinationLat,@Path("lng2") double destinationLng);
}

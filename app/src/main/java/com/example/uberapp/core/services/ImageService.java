package com.example.uberapp.core.services;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ImageService {
    @GET("/api/image/{imgName}")
    Observable<ResponseBody> getImage(@Path("imgName") String imgName);
}

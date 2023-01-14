package com.example.uberapp.core.services;

import com.example.uberapp.core.dto.LoginCredentialsDTO;
import com.example.uberapp.core.dto.UserDetailedDTO;
import com.example.uberapp.core.services.auth.TokenState;


import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {
    @POST("/api/user/login")
    Observable<TokenState> login(@Body LoginCredentialsDTO credentials);

    @GET("/api/driver/{id}")
    Call<UserDetailedDTO> getDriver(@Path("id") Integer id);
}

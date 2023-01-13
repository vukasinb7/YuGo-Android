package com.example.uberapp.core.services;

import com.example.uberapp.core.dto.LoginCredentialsDTO;
import com.example.uberapp.core.services.auth.TokenState;


import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {
    @POST("/api/user/login")
    Observable<TokenState> login(@Body LoginCredentialsDTO credentials);
}

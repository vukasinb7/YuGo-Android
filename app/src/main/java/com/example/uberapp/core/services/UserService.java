package com.example.uberapp.core.services;

import com.example.uberapp.core.dto.AllMessagesDTO;
import com.example.uberapp.core.dto.LoginCredentialsDTO;
import com.example.uberapp.core.dto.MessageDTO;
import com.example.uberapp.core.dto.MessageSendDTO;
import com.example.uberapp.core.dto.UserDetailedDTO;
import com.example.uberapp.core.auth.TokenState;


import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {
    @POST("/api/user/login")
    Observable<TokenState> login(@Body LoginCredentialsDTO credentials);
    @POST("/api/user/logout")
    Call<ResponseBody> logout();
    @GET("/api/user/{id}/message")
    Call<AllMessagesDTO> getUserMessages(@Path("id") Integer id);
    @GET("/api/user/{id}/conversation")
    Call<AllMessagesDTO> getUsersConversation(@Path("id") Integer id);
    @POST("/api/user/{id}/message")
    Call<MessageDTO> sendMessageToUser(@Path("id") Integer id, @Body MessageSendDTO messageSendDTO);
    @GET("/api/user/{id}")
    Call<UserDetailedDTO> getUser(@Path("id") Integer id);
}

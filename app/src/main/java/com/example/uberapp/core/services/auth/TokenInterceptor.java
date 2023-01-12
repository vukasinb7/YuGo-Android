package com.example.uberapp.core.services.auth;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class TokenInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl originalURL = original.url();

        if(TokenManager.getToken() != null){
            Builder newRequestBuilder = original.newBuilder().addHeader("authorization", "JWT " + TokenManager.getToken()).url(originalURL);
            chain.proceed(newRequestBuilder.build());
        }else{
            chain.proceed(original);
        }

        Gson gson = new Gson();
        Response response = chain.proceed(original);
        ResponseBody body = response.body();
        if(originalURL.encodedPath().contains("/login") && body != null && response.code() == 200){
            TokenState tokenState = gson.fromJson(body.string(), TokenState.class);
            TokenManager.setToken(tokenState.accessToken);
            TokenManager.setRefreshToken(tokenState.refreshToken);
        }else if(originalURL.encodedPath().contains("/logout") && body != null && response.code() == 200){
            TokenManager.setRefreshToken(null);
            TokenManager.setToken(null);
        }
        return response;
    }
}

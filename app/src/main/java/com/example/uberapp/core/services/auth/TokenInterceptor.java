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

        return chain.proceed(original);
    }
}

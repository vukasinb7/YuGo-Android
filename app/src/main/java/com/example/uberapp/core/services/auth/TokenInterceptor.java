package com.example.uberapp.core.services.auth;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl originalURL = original.url();

        if(TokenManager.getToken() != null){
            Builder newRequestBuilder = original.newBuilder().addHeader("authorization", TokenManager.getTokenType()+ " " + TokenManager.getToken()).url(originalURL);
            return chain.proceed(newRequestBuilder.build());
        }
        return chain.proceed(original);
    }
}

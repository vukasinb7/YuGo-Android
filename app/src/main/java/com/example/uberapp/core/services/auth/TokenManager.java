package com.example.uberapp.core.services.auth;

import com.auth0.android.jwt.JWT;

public class TokenManager {
    private static JWT token = null;
    private static String refreshToken = null;

    public static String getRefreshToken() {
        return refreshToken;
    }

    public static void setRefreshToken(String refreshToken) {
        TokenManager.refreshToken = refreshToken;
    }

    public static String getToken() {
        if (token != null){
            return token.toString();
        }
        return null;
    }

    public static void setToken(String token) {
        TokenManager.token = new JWT(token);
    }

    public static String getRole(){
        return token.getClaim("role").asString();
    }

    public static String getUserId(){
        return token.getClaim("id").asString();
    }

    public static String getTokenType(){
        return token.getClaim("type").asString();
    }
}

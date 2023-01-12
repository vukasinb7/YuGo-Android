package com.example.uberapp.core.services.auth;

public class TokenManager {
    private static String token = null;
    private static String refreshToken = null;

    public static String getRefreshToken() {
        return refreshToken;
    }

    public static void setRefreshToken(String refreshToken) {
        TokenManager.refreshToken = refreshToken;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        TokenManager.token = token;
    }
}

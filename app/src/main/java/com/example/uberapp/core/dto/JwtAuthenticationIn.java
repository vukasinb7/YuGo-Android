package com.example.uberapp.core.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtAuthenticationIn {
    private String email;

    private String password;

    public JwtAuthenticationIn() {
        super();
    }

    public JwtAuthenticationIn(String email, String password) {
        this.setEmail(email);
        this.setPassword(password);
    }
}

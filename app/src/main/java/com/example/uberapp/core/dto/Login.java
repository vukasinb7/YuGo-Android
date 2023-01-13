package com.example.uberapp.core.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter @Setter
@NoArgsConstructor
public class Login {
    private String email;
    private String password;

    public Login(String email, String password) {
        this.email = email;
        this.password = password;
    }
}

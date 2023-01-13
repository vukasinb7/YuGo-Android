package com.example.uberapp.core.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class PasswordResetIn {
    String newPassword;
    Integer code;

    public PasswordResetIn(String newPassword, Integer code){
        this.newPassword = newPassword;
        this.code = code;
    }
}

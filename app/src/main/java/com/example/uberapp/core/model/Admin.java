package com.example.uberapp.core.model;

import com.example.uberapp.core.dto.UserDetailedIn;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;//

@NoArgsConstructor
@Getter @Setter
public class Admin extends User{

    public Admin(UserDetailedIn userDetailedIn){
        super(userDetailedIn);
    }

}

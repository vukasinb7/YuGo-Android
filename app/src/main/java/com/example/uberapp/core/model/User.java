package com.example.uberapp.core.model;


import com.example.uberapp.core.dto.UserDetailedIn;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Getter @Setter
public abstract class User /*implements UserDetails*/ {
    private String role;
    private String name;
    private String surname;
    private String profilePicture;
    private String telephoneNumber;
    private String email;
    private String address;
    private String password;
    private boolean isBlocked;
    private boolean isActive;
    private Integer id;
    private List<Role> roles;
    private Timestamp lastPasswordResetDate;

    public void setPassword(String password) {
        Timestamp now = new Timestamp((new Date()).getTime());
        this.lastPasswordResetDate=now;
        this.password = password;
    }

    public User(UserDetailedIn userDetailedIn) {
        this.name = userDetailedIn.getName();
        this.surname = userDetailedIn.getSurname();
        this.profilePicture = userDetailedIn.getProfilePicture();
        this.telephoneNumber = userDetailedIn.getTelephoneNumber();
        this.email = userDetailedIn.getEmail();
        this.address = userDetailedIn.getAddress();
        this.password = userDetailedIn.getPassword();
        this.isBlocked = false;
        this.isActive = false;
    }
}

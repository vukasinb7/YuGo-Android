package com.example.uberapp.core.dto;

import com.example.uberapp.core.model.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class UserDetailedInOut {
    private Integer id;
    private String name;
    private String surname;
    private String profilePicture;
    private String telephoneNumber;
    private String email;
    private String address;
    private String role;
    private boolean isBlocked;

    public UserDetailedInOut(Integer id, String name, String surname, String profilePicture, String telephoneNumber, String email, String address, String role, boolean isBlocked) {
        super();
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.profilePicture = profilePicture;
        this.telephoneNumber = telephoneNumber;
        this.email = email;
        this.address = address;
        this.role = role;
        this.isBlocked = isBlocked;
    }

    public UserDetailedInOut(User user) {
        this(user.getId(), user.getName(), user.getSurname(), user.getProfilePicture(), user.getTelephoneNumber(), user.getEmail(), user.getAddress(), user.getRole(), user.isBlocked());
    }
}

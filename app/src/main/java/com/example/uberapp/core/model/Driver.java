package com.example.uberapp.core.model;

import com.example.uberapp.core.dto.UserDetailedIn;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@Getter @Setter
public class Driver extends User{
    private Set<Document> documents;
    private Set<Ride> rides;
    private Vehicle vehicle;
    private boolean isOnline;
    public Driver(UserDetailedIn userDetailedIn) {
        super(userDetailedIn);
    }
}

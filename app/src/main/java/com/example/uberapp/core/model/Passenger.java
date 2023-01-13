package com.example.uberapp.core.model;

import com.example.uberapp.core.dto.UserDetailedIn;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@Getter @Setter
public class Passenger extends User{
    private Set<Ride> rides;
    private Set<FavoritePath> favouritePaths;

    public Passenger(UserDetailedIn userDetailedIn) {
        super(userDetailedIn);
    }

}

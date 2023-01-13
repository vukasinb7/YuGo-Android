package com.example.uberapp.core.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter @Setter
@NoArgsConstructor
public class PathInOut {
    private LocationInOut departure;
    private LocationInOut destination;

    public PathInOut(LocationInOut departure, LocationInOut destination) {
        this.departure = departure;
        this.destination = destination;
    }



}

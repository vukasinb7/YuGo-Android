package com.example.uberapp.core.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class Location {
    private String address;
    private double longitude;
    private double latitude;
    private Integer id;


}

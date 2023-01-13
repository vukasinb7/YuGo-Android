package com.example.uberapp.core.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Path{
    private Location departure;
    private Location destination;
    private Integer id;
}

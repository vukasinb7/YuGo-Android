package com.example.uberapp.core.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter @Setter
public class RideIn {
    private List<PathInOut> locations;
    private List<UserSimplifiedOut> passengers;
    private String vehicleType;
    private boolean babyTransport;
    private boolean petTransport;
    private String dateTime;
}

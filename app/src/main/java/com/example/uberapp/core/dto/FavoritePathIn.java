package com.example.uberapp.core.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class FavoritePathIn {
    private String favoriteName;
    private List<PathInOut> locations;
    private List<UserSimplifiedOut> passengers;
    private String vehicleType;

    private Boolean babyTransport;

    private Boolean petTransport;
}

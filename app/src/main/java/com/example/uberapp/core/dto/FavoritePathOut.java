package com.example.uberapp.core.dto;

import com.example.uberapp.core.model.FavoritePath;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class FavoritePathOut {

    private String favoriteName;
    private List<PathInOut> locations;
    private List<UserSimplifiedOut> passengers;
    private String vehicleType;
    private Boolean babyTransport;
    private Boolean petTransport;
    private Integer id;

    /*public FavoritePathOut(FavoritePath path){
        this.favoriteName=path.getFavoriteName();
        this.vehicleType=path.getVehicleTypePrice().getVehicleType().toString();
        this.babyTransport=path.getBabyTransport();
        this.petTransport=path.getPetTransport();
        this.id=path.getId();
        this.locations=path.getLocations().stream().map(PathMapper::fromPathtoDTO).toList();
        this.passengers = path.getPassengers().stream().map(UserSimplifiedMapper::fromUsertoDTO).toList();

    }*/
}

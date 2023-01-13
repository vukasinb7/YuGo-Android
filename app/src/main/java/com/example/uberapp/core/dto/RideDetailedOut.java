package com.example.uberapp.core.dto;

import com.example.uberapp.core.model.Ride;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
@Getter @Setter
@NoArgsConstructor
public class RideDetailedOut {
    private Integer id;
    private List<PathInOut> locations;
    private String startTime;
    private String endTime;
    private Double totalCost;
    private UserSimplifiedOut driver;
    private List<UserSimplifiedOut> passengers;
    private int estimatedTimeInMinutes;
    private String vehicleType;
    private boolean babyTransport;
    private boolean petTransport;
    private RejectionOut rejection;
    private String status;

    public RideDetailedOut(Ride ride) {
        this.id= ride.getId();
        this.startTime = ride.getStartTime().toString();
        if (ride.getEndTime()!=null)
            this.endTime = ride.getEndTime().toString();
        else
            this.endTime=null;
        this.totalCost = ride.getTotalCost();
        //this.driver = UserSimplifiedMapper.fromUsertoDTO(ride.getDriver());
        //this.passengers = ride.getPassengers().stream().map(UserSimplifiedMapper::fromUsertoDTO).toList();
        this.estimatedTimeInMinutes = ride.getEstimatedTimeInMinutes();
        this.vehicleType = ride.getDriver().getVehicle().getVehicleType().toString();
        this.babyTransport = ride.getBabyTransport();
        this.petTransport = ride.getPetTransport();
        //this.locations = ride.getLocations().stream().map(PathMapper::fromPathtoDTO).toList();
        this.status = ride.getStatus().toString();
        if (ride.getRejection()!=null)
            this.rejection=new RejectionOut(ride.getRejection());
    }
}

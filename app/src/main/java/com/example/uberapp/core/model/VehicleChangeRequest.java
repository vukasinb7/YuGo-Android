package com.example.uberapp.core.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class VehicleChangeRequest {
    private Integer id;
    private Driver driver;
    private Vehicle vehicle;
    private VehicleChangeRequestStatus status;
    private LocalDateTime dateCreated;

    public VehicleChangeRequest(Driver driver, Vehicle vehicle){
        this.driver = driver;
        this.vehicle = vehicle;
        this.status = VehicleChangeRequestStatus.PENDING;
        this.dateCreated = LocalDateTime.now();
    }
}

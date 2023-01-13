package com.example.uberapp.core.dto;

import java.util.List;

public class RideDetailedDTO {
    private Integer id;
    private List<PathDTO> locations;
    private String startTime;
    private String endTime;
    private Double totalCost;
    private UserSimplifiedDTO driver;
    private List<UserSimplifiedDTO> passengers;
    private int estimatedTimeInMinutes;
    private String vehicleType;
    private boolean babyTransport;
    private boolean petTransport;
    private RejectionDTO rejection;
    private String status;

    public RideDetailedDTO() {
    }

    public RideDetailedDTO(Integer id, List<PathDTO> locations, String startTime, String endTime,
                           Double totalCost, UserSimplifiedDTO driver,
                           List<UserSimplifiedDTO> passengers, int estimatedTimeInMinutes,
                           String vehicleType, boolean babyTransport, boolean petTransport,
                           RejectionDTO rejection, String status) {
        this.id = id;
        this.locations = locations;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalCost = totalCost;
        this.driver = driver;
        this.passengers = passengers;
        this.estimatedTimeInMinutes = estimatedTimeInMinutes;
        this.vehicleType = vehicleType;
        this.babyTransport = babyTransport;
        this.petTransport = petTransport;
        this.rejection = rejection;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<PathDTO> getLocations() {
        return locations;
    }

    public void setLocations(List<PathDTO> locations) {
        this.locations = locations;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public UserSimplifiedDTO getDriver() {
        return driver;
    }

    public void setDriver(UserSimplifiedDTO driver) {
        this.driver = driver;
    }

    public List<UserSimplifiedDTO> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<UserSimplifiedDTO> passengers) {
        this.passengers = passengers;
    }

    public int getEstimatedTimeInMinutes() {
        return estimatedTimeInMinutes;
    }

    public void setEstimatedTimeInMinutes(int estimatedTimeInMinutes) {
        this.estimatedTimeInMinutes = estimatedTimeInMinutes;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public boolean isBabyTransport() {
        return babyTransport;
    }

    public void setBabyTransport(boolean babyTransport) {
        this.babyTransport = babyTransport;
    }

    public boolean isPetTransport() {
        return petTransport;
    }

    public void setPetTransport(boolean petTransport) {
        this.petTransport = petTransport;
    }

    public RejectionDTO getRejection() {
        return rejection;
    }

    public void setRejection(RejectionDTO rejection) {
        this.rejection = rejection;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

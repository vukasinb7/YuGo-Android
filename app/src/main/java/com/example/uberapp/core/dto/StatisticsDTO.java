package com.example.uberapp.core.dto;

public class StatisticsDTO {
    private Double totalIncome;
    private Double totalRides;

    private Double averageRating;
    private Double totalWorkHours;
    private Double totalDistance;
    private Double totalPassengers;
    public StatisticsDTO(){}

    public StatisticsDTO(Double totalIncome, Double totalRides, Double averageRating, Double totalWorkHours, Double totalDistance, Double totalPassengers) {
        this.totalIncome = totalIncome;
        this.totalRides = totalRides;
        this.averageRating = averageRating;
        this.totalWorkHours = totalWorkHours;
        this.totalDistance = totalDistance;
        this.totalPassengers = totalPassengers;
    }

    public Double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(Double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public Double getTotalRides() {
        return totalRides;
    }

    public void setTotalRides(Double totalRides) {
        this.totalRides = totalRides;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Double getTotalWorkHours() {
        return totalWorkHours;
    }

    public void setTotalWorkHours(Double totalWorkHours) {
        this.totalWorkHours = totalWorkHours;
    }

    public Double getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(Double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public Double getTotalPassengers() {
        return totalPassengers;
    }

    public void setTotalPassengers(Double totalPassengers) {
        this.totalPassengers = totalPassengers;
    }
}

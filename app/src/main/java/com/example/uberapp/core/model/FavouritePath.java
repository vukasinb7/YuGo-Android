package com.example.uberapp.core.model;

public class FavouritePath {
    private Passenger user;
    private String id;
    private Location startingLocation;
    private Location endLocation;

    public FavouritePath(Passenger user, String id, Location startingLocation, Location endLocation) {
        this.user = user;
        this.id = id;
        this.startingLocation = startingLocation;
        this.endLocation = endLocation;
    }

    public Passenger getUser() {
        return user;
    }

    public void setUser(Passenger user) {
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Location getStartingLocation() {
        return startingLocation;
    }

    public void setStartingLocation(Location startingLocation) {
        this.startingLocation = startingLocation;
    }

    public Location getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(Location endLocation) {
        this.endLocation = endLocation;
    }
}

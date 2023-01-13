package com.example.uberapp.core.model;

import android.graphics.Bitmap;

import java.util.List;

public class Passenger extends User{

    List<Ride> rides;
    List<Review> reviews;
    List<FavouritePath> favourites;

    public Passenger(String id, String name, String lastName, Integer profilePicture, String phoneNumber, String email, String address, String password, Boolean isBlocked, List<Ride> rides, List<Review> reviews, List<FavouritePath> favourites) {
        super(id, name, lastName, profilePicture, phoneNumber, email, address, password, isBlocked);
        this.rides = rides;
        this.reviews = reviews;
        this.favourites = favourites;
    }

    public List<Ride> getRides() {
        return rides;
    }

    public void setRides(List<Ride> rides) {
        this.rides = rides;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<FavouritePath> getFavourites() {
        return favourites;
    }

    public void setFavourites(List<FavouritePath> favourites) {
        this.favourites = favourites;
    }
}

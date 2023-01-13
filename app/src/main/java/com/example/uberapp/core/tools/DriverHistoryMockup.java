package com.example.uberapp.core.tools;

import com.example.uberapp.core.model.FavoritePath;
import com.example.uberapp.core.model.Passenger;
import com.example.uberapp.core.model.Ride;
import com.example.uberapp.core.model.RideReview;
import com.example.uberapp.core.model.RideStatus;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DriverHistoryMockup {
    public static List<Ride> getRides(){
        List<Ride> rides = new ArrayList<>();
        Passenger pass1 = new Passenger();
        ArrayList<Passenger> passengers=new ArrayList<>();
        passengers.add(pass1);
        LocalDateTime start=LocalDateTime.of(2021,12,12,17,15);
        LocalDateTime start2=LocalDateTime.of(2021,11,12,17,15);
        LocalDateTime start3=LocalDateTime.of(2021,11,07,17,15);
        LocalDateTime end=LocalDateTime.of(2021,12,12,17,30);
        LocalDateTime end2=LocalDateTime.of(2021,11,12,17,45);
        LocalDateTime end3=LocalDateTime.of(2021,11,7,18,0);
        Ride r1=new Ride();
        Ride r2=new Ride();
        Ride r3=new Ride();
        Ride r4=new Ride();

        //Bitmap icon1 = BitmapFactory.decodeFile("./../../../../../../res/drawable/image_car_model_01.png");
        rides.add(r1);
        rides.add(r2);
        rides.add(r3);
        rides.add(r4);

        return rides;
    }
}

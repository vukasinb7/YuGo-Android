package com.example.uberapp.core.tools;

import com.example.uberapp.R;
import com.example.uberapp.core.model.FavouritePath;
import com.example.uberapp.core.model.Passenger;
import com.example.uberapp.core.model.Review;
import com.example.uberapp.core.model.Ride;
import com.example.uberapp.core.model.RideStatus;
import com.example.uberapp.core.model.VehicleCategory;
import com.example.uberapp.core.model.VehicleType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.List;

public class DriverHistoryMockup {
    public static List<Ride> getRides(){
        List<Ride> rides = new ArrayList<>();
        Passenger pass1 = new Passenger("001", "Marko", "Markovic",
                null, "0604560456", "example@example.com",
                "Bulevar oslobodjenja 213", "asdasd", false,
                new ArrayList<Ride>(), new ArrayList<Review>() , new ArrayList<FavouritePath>());
        ArrayList<Passenger> passengers=new ArrayList<>();
        passengers.add(pass1);
        LocalDateTime start=LocalDateTime.of(2021,12,12,17,15);
        LocalDateTime end=LocalDateTime.of(2021,12,12,17,30);
        Ride r1=new Ride("001",start,end,15.60,Duration.between(end,start),null,false,false,false,false,passengers,RideStatus.Finished);

        //Bitmap icon1 = BitmapFactory.decodeFile("./../../../../../../res/drawable/car_model_01.png");
        rides.add(r1);

        return rides;
    }
}

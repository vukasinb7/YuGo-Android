package com.example.uberapp.core.tools;

import com.example.uberapp.core.model.FavouritePath;
import com.example.uberapp.core.model.Passenger;
import com.example.uberapp.core.model.Review;
import com.example.uberapp.core.model.Ride;
import com.example.uberapp.core.model.RideStatus;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PassengerHistoryMockup {
    public static List<Ride> getRides(){
        List<Ride> rides = new ArrayList<>();
        Passenger pass1 = new Passenger("001", "Marko", "Markovic",
                null, "0604560456", "example@example.com",
                "Bulevar oslobodjenja 213", "asdasd", false,
                new ArrayList<Ride>(), new ArrayList<Review>() , new ArrayList<FavouritePath>());
        ArrayList<Passenger> passengers=new ArrayList<>();
        passengers.add(pass1);
        LocalDateTime start=LocalDateTime.of(2021,12,12,17,15);
        LocalDateTime start2=LocalDateTime.of(2021,11,12,17,15);
        LocalDateTime start3=LocalDateTime.of(2021,11,07,17,15);
        LocalDateTime end=LocalDateTime.of(2021,12,12,17,30);
        LocalDateTime end2=LocalDateTime.of(2021,11,12,17,45);
        LocalDateTime end3=LocalDateTime.of(2021,11,7,18,0);
        Ride r1=new Ride("001",start,end,15.60,Duration.between(end,start),null,false,false,false,false,passengers,RideStatus.Finished);
        Ride r2=new Ride("002",start,end,17.20,Duration.between(end,start),null,false,false,false,false,passengers,RideStatus.Finished);
        Ride r3=new Ride("003",start2,end2,9.60,Duration.between(end,start),null,false,false,false,false,passengers,RideStatus.Finished);
        Ride r4=new Ride("004",start3,end2,13.00,Duration.between(end,start),null,false,false,false,false,passengers,RideStatus.Finished);

        //Bitmap icon1 = BitmapFactory.decodeFile("./../../../../../../res/drawable/image_car_model_01.png");
        rides.add(r1);
        rides.add(r2);
        rides.add(r3);
        rides.add(r4);

        return rides;
    }
}

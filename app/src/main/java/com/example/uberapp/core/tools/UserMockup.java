package com.example.uberapp.core.tools;

import com.example.uberapp.R;
import com.example.uberapp.core.model.Driver;
import com.example.uberapp.core.model.Passenger;
import com.example.uberapp.core.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserMockup {
    static User user01 = new Passenger(
            "001",
            "Amar",
            "Sinha",
            R.drawable.image_profile_pic_01,
            "06512332100",
            "example@email.com",
            "some address",
            "123",
            false,
            null,
            null,
            null
    );
    static User user02 = new Driver(
            "002",
            "Marko",
            "Markovic",
            R.drawable.image_profile_pic_02,
            "023857235",
            "example@email.com",
            "some address",
            "123",
            false,
            null,
            null,
            true,
            null

    );

    static List<User> users = new ArrayList<>(Arrays.asList(user01, user02));

    public static List<User> getUsers(){
        return users;
    }

    public static User getLoggedUser(){
        return user01;
    }
}

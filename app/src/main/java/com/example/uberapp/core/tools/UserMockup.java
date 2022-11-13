package com.example.uberapp.core.tools;

import com.example.uberapp.R;
import com.example.uberapp.core.model.Driver;
import com.example.uberapp.core.model.Passenger;
import com.example.uberapp.core.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserMockup {
    public static List<User> getUsers(){
        User user01 = new Passenger(
                "001",
                "Josip",
                "Broz",
                R.drawable.profile_picture_01,
                "06512332100",
                "josip.broz@sfrj.kpj",
                "some address",
                "sfrj123",
                false,
                null,
                null,
                null
        );
        User user02 = new Driver(
                "002",
                "Mao",
                "Zedong",
                R.drawable.profile_picture_02,
                "023857235",
                "mao.zedong@wee.com",
                "some address",
                "safasgf",
                false,
                null,
                null,
                true,
                null

        );
        List<User> users = new ArrayList<>();
        users.add(user01);
        users.add(user02);
        return users;
    }
}

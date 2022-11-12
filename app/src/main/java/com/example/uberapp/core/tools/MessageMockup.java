package com.example.uberapp.core.tools;

import com.example.uberapp.core.model.FavouritePath;
import com.example.uberapp.core.model.Message;
import com.example.uberapp.core.model.MessageType;
import com.example.uberapp.core.model.Passenger;
import com.example.uberapp.core.model.Review;
import com.example.uberapp.core.model.Ride;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MessageMockup {
    public static List<Message> getMessages(){
        List<Message> messages = new ArrayList<>();

        Passenger pass1 = new Passenger("001", "Marko", "Markovic",
                null, "0604560456", "example@example.com",
                "Bulevar oslobodjenja 213", "asdasd", false,
                new ArrayList<Ride>(), new ArrayList<Review>() , new ArrayList<FavouritePath>());

        Passenger pass2 = new Passenger("001", "Darko", "Markovic",
                null, "0604560456", "example@example.com",
                "Bulevar oslobodjenja 213", "asdasd", false,
                new ArrayList<Ride>(), new ArrayList<Review>() , new ArrayList<FavouritePath>());

        Message mess1 = new Message("001", "Pozdrav iz prve poruke",
                LocalDateTime.of(2021,12,12,17,15),
                pass1, pass2, MessageType.Drive, null);

        Message mess2 = new Message("001", "Pozdrav iz prve poruke",
                LocalDateTime.of(2021,12,12,17,15),
                pass1, pass2, MessageType.Drive, null);

        Message mess3 = new Message("001", "Pozdrav iz prve poruke",
                LocalDateTime.of(2021,12,12,17,15),
                pass1, pass2, MessageType.Drive, null);

        Message mess4 = new Message("001", "Pozdrav iz prve poruke",
                LocalDateTime.of(2021,12,12,17,15),
                pass1, pass2, MessageType.Drive, null);

        Message mess5 = new Message("001", "Pozdrav iz prve poruke",
                LocalDateTime.of(2021,12,12,17,15),
                pass1, pass2, MessageType.Drive, null);

        messages.add(mess1);
        messages.add(mess2);
        messages.add(mess3);
        messages.add(mess4);
        messages.add(mess5);

        return messages;
    }
}

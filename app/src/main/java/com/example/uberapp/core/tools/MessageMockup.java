package com.example.uberapp.core.tools;

import com.example.uberapp.R;
import com.example.uberapp.core.model.FavouritePath;
import com.example.uberapp.core.model.Message;
import com.example.uberapp.core.model.MessageType;
import com.example.uberapp.core.model.Passenger;
import com.example.uberapp.core.model.Review;
import com.example.uberapp.core.model.Ride;
import com.example.uberapp.core.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MessageMockup {
    public static List<Message> getMessages(){
        List<Message> messages = new ArrayList<>();

        User pass1 = UserMockup.getUsers().get(0);
        User pass2 = UserMockup.getUsers().get(1);

        String sample_text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque sodales sem sit amet ipsum scelerisque consectetur. Vestibulum pellentesque diam sit amet diam commodo laoreet. Vivamus lobortis aliquam neque id hendrerit.";
        Message mess1 = new Message("001", sample_text,
                LocalDateTime.of(2022,11,13,17,15),
                pass1, pass2, MessageType.Drive, null);

        Message mess2 = new Message("001", sample_text,
                LocalDateTime.of(2021,12,12,17,15),
                pass1, pass2, MessageType.Assistance, null);

        Message mess3 = new Message("001", sample_text,
                LocalDateTime.of(2021,12,12,17,15),
                pass1, pass2, MessageType.Drive, null);

        Message mess4 = new Message("001", sample_text,
                LocalDateTime.of(2021,12,12,17,15),
                pass1, pass2, MessageType.Panic, null);

        Message mess5 = new Message("001", sample_text,
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

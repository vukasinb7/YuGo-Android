package com.example.uberapp.core.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter @Setter
public class Message {
    private User sender;
    private User receiver;
    private String messageContent;
    private LocalDateTime timeOfSending;
    private MessageType messageType;
    private Ride ride;
    private Integer id;

    public Message(User sender, User receiver, String messageContent, LocalDateTime timeOfSending, MessageType messageType, Ride ride) {
        this.sender = sender;
        this.receiver = receiver;
        this.messageContent = messageContent;
        this.timeOfSending = timeOfSending;
        this.messageType = messageType;
        this.ride = ride;
    }
}

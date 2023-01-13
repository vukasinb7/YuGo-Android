package com.example.uberapp.core.dto;

import com.example.uberapp.core.model.MessageType;

import lombok.Getter;
import lombok.Setter;
@Getter @Setter
public class MessageIn {
    private Integer receiverId;

    private String message;

    private MessageType type;
    private Integer rideId;

    public MessageIn(Integer receiverId, String message, MessageType type, Integer rideId) {
        super();
        this.receiverId = receiverId;
        this.message = message;
        this.type = type;
        this.rideId = rideId;
    }
}

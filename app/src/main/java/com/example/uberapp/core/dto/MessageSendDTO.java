package com.example.uberapp.core.dto;

public class MessageSendDTO {
    private String type;
    private String message;
    private int rideId;

    public MessageSendDTO(String type, String message, int rideId) {
        this.type = type;
        this.message = message;
        this.rideId = rideId;
    }

    public MessageSendDTO() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getRideId() {
        return rideId;
    }

    public void setRideId(int rideId) {
        this.rideId = rideId;
    }
}

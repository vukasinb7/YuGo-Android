package com.example.uberapp.core.model;

import java.time.LocalDateTime;

public class Message {
    private String id;
    private String text;
    private LocalDateTime sendDateTime;
    private User sender;
    private User receiver;
    private MessageType messageType;
    private Ride ride;

    public Message(String id, String text, LocalDateTime sendDateTime, User sender, User receiver, MessageType messageType, Ride ride) {
        this.id = id;
        this.text = text;
        this.sendDateTime = sendDateTime;
        this.sender = sender;
        this.receiver = receiver;
        this.messageType = messageType;
        this.ride = ride;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getSendDateTime() {
        return sendDateTime;
    }

    public void setSendDateTime(LocalDateTime sendDateTime) {
        this.sendDateTime = sendDateTime;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public Ride getRide() {
        return ride;
    }

    public void setRide(Ride ride) {
        this.ride = ride;
    }
}

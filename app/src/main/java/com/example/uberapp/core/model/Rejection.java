package com.example.uberapp.core.model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter @Setter
public class Rejection {
    private Ride ride;
    private User byUser;
    private String reason;
    private LocalDateTime timeOfRejection;
    private Integer id;

    public Rejection(Ride ride, User byUser, String reason, LocalDateTime timeOfRejection) {
        this.ride = ride;
        this.byUser = byUser;
        this.reason = reason;
        this.timeOfRejection = timeOfRejection;
    }
}

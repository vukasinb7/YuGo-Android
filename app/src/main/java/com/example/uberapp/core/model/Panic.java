package com.example.uberapp.core.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter @Setter
public class Panic {
    private User user;
    private Ride ride;
    private LocalDateTime time;
    private String reason;
    private Integer id;

    public Panic(User user, Ride ride, LocalDateTime time, String reason) {
        this.user = user;
        this.ride = ride;
        this.time = time;
        this.reason = reason;
    }
}

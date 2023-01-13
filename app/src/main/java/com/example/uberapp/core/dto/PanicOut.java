package com.example.uberapp.core.dto;

import com.example.uberapp.core.model.Panic;
import com.example.uberapp.core.model.Ride;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter @Setter
@NoArgsConstructor
public class PanicOut {
    Integer id;
    UserDetailedOut user;
    RideDetailedOut ride;
    LocalDateTime time;
    String reason;

    public PanicOut(Integer id, UserDetailedOut user, Ride ride, LocalDateTime time, String reason) {
        this.id = id;
        this.user = user;
        //this.ride = RideMapper.fromRidetoDTO(ride);
        this.time = time;
        this.reason = reason;
    }

    public PanicOut(Panic panic) {
        this.id = panic.getId();
        this.user = new UserDetailedOut(panic.getUser());
        //this.ride = RideMapper.fromRidetoDTO(panic.getRide());
        this.time = panic.getTime();
        this.reason = panic.getReason();
    }
}

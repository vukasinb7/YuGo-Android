package com.example.uberapp.core.dto;

import com.example.uberapp.core.model.Rejection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
public class RejectionOut {
    String reason;
    LocalDateTime timeOfRejection;

    public RejectionOut(Rejection rejection){
        this.reason=rejection.getReason();
        this.timeOfRejection=rejection.getTimeOfRejection();
    }
}

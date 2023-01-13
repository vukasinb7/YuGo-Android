package com.example.uberapp.core.dto;

import java.time.LocalDateTime;

public class RejectionDTO {
    String reason;
    LocalDateTime timeOfRejection;

    public RejectionDTO() {
    }

    public RejectionDTO(String reason, LocalDateTime timeOfRejection) {
        this.reason = reason;
        this.timeOfRejection = timeOfRejection;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getTimeOfRejection() {
        return timeOfRejection;
    }

    public void setTimeOfRejection(LocalDateTime timeOfRejection) {
        this.timeOfRejection = timeOfRejection;
    }
}

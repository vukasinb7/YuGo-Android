package com.example.uberapp.core.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class RejectionDTO implements Serializable {
    String reason;
    String timeOfRejection;

    public RejectionDTO() {
    }

    public RejectionDTO(String reason, String timeOfRejection) {
        this.reason = reason;
        this.timeOfRejection = timeOfRejection;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getTimeOfRejection() {
        return timeOfRejection;
    }

    public void setTimeOfRejection(String timeOfRejection) {
        this.timeOfRejection = timeOfRejection;
    }
}

package com.example.uberapp.core.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;


@Getter @Setter
@NoArgsConstructor
public class UserActivation {
    private User user;
    private LocalDateTime dateCreated;
    private Duration lifeSpan;
    private boolean valid;
    private Integer code;

    /*public UserActivation(User user, Duration lifeSpan) {
        this.user = user;
        this.dateCreated = LocalDateTime.now();
        this.lifeSpan = lifeSpan;
        this.valid = true;
        this.code = Math.abs(user.hashCode() + dateCreated.hashCode() + RandomGenerator.getDefault().nextInt(100));
    }*/
}

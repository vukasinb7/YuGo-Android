package com.example.uberapp.core.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter @Setter
public class Note {
    private String message;
    private LocalDateTime date;
    private User user;
    private Integer id;

    public Note(User user, String message, LocalDateTime date){
        this.user = user;
        this.message = message;
        this.date = date;
    }
}

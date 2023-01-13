package com.example.uberapp.core.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter @Setter
@NoArgsConstructor
public class NoteIn {

    String message;

    public NoteIn(String message){
        this.message = message;
    }
}

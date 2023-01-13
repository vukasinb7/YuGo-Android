package com.example.uberapp.core.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class EndWorkTimeIn {
    private String end;

    public EndWorkTimeIn(String end) {
        this.end = end;
    }

}

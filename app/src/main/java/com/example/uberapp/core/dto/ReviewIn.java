package com.example.uberapp.core.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class ReviewIn {
    private int rating;
    private String comment;

    public ReviewIn(int rating, String comment) {
        this.rating = rating;
        this.comment = comment;
    }
}

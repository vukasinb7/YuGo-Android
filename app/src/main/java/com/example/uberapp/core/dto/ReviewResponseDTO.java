package com.example.uberapp.core.dto;

public class ReviewResponseDTO {
    private Integer id;
    private int rating;
    private String comment;
    private UserSimplifiedDTO passenger;

    private String type;

    public ReviewResponseDTO() {
    }

    public ReviewResponseDTO(Integer id, int rating, String comment, UserSimplifiedDTO passenger) {
        this.id = id;
        this.rating = rating;
        this.comment = comment;
        this.passenger = passenger;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public UserSimplifiedDTO getPassenger() {
        return passenger;
    }

    public void setPassenger(UserSimplifiedDTO passenger) {
        this.passenger = passenger;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

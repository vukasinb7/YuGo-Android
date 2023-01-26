package com.example.uberapp.core.dto;

public class PictureResponseDTO {
    String pictureName;

    public PictureResponseDTO() {
    }

    public PictureResponseDTO(String pictureName) {
        this.pictureName = pictureName;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }
}

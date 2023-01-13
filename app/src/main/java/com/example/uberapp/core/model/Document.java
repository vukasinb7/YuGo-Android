package com.example.uberapp.core.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class Document {
    private String name;
    private DocumentType type;
    private Driver driver;
    private Integer id;
    private String image;

    public Document(String name, String image, Driver driver,DocumentType documentType) {
        this.name = name;
        this.image = image;
        this.driver = driver;
        this.type=documentType;
    }
}

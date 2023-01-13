package com.example.uberapp.core.dto;

import com.example.uberapp.core.model.DocumentType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class DocumentIn {

    private String name;

    private String documentImage;

    private DocumentType documentType;

    public DocumentIn(String name, String documentImage,DocumentType documentType) {
        this.name = name;
        this.documentImage = documentImage;
        this.documentType=documentType;
    }
}

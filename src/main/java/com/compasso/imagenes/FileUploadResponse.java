package com.compasso.imagenes;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FileUploadResponse {
    private String fileName;
    private String downloadUri;
    private String size;
}

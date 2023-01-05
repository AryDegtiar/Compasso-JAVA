package com.compasso.imagenes;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FileUploadResponse {
    private String fileName;
    private String downloadUri;
    private String size;
}

package com.compasso.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ProductoPatch {
    @NotNull
    private Integer creadorID;

    private Boolean activo;

    private String nombre;

    private String descripcion;

    private String imagen;

    private String fichaTecnica;
}

package com.compasso.productos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@Entity
@Table(name = "producto")
public class Producto {
    @Id
    @GeneratedValue
    private Integer id;
    private Boolean activo;
    @NotNull @NotBlank
    private String nombre;
    @NotNull @NotBlank
    private String descripcion;
    @NotNull @NotBlank
    private String imagen;
    @NotNull
    private String fichaTecnica;

    private String creador;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;

    public Producto(String nombre, String descripcion, String imagen, String fichaTecnica) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.fichaTecnica = fichaTecnica;
        this.activo = true;
        this.fechaCreacion = LocalDateTime.now();
    }

    public Producto() {
        this.activo = true;
    }

    public void guardarFechaModificacion() {
        this.fechaModificacion = LocalDateTime.now();
    }
}

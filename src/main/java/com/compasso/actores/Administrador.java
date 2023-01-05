package com.compasso.actores;

import com.compasso.productos.Producto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
@Table(name = "administrador")
public class Administrador {

    @Id
    @GeneratedValue
    private Integer id;
    private String nombre;
    private String contrasenia;

    @OneToMany
    @JoinColumn(name = "administradorId", referencedColumnName = "id")
    private List<Producto> productos;

    public Administrador() {
        this.productos = new ArrayList<>();
    }

    public Administrador(String nombre, String contrasenia) {
        super();
        this.nombre = nombre;
        this.contrasenia = contrasenia;
    }
}

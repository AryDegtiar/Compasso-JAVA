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
    private String usuario;
    private String contrasenia;

    @OneToMany
    @JoinColumn(name = "administradorId", referencedColumnName = "id")
    private List<Producto> productos;

    public void addProducto(Producto producto) {
        if (productos == null) {
            productos = new ArrayList<>();
        }
        productos.add(producto);
    }

    public Administrador() {
        this.productos = new ArrayList<>();
    }

    public Administrador(String usuario, String contrasenia) {
        super();
        this.usuario = usuario;
        this.contrasenia = contrasenia;
    }

}

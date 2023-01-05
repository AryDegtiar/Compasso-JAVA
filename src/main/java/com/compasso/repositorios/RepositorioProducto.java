package com.compasso.repositorios;

import com.compasso.productos.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioProducto extends JpaRepository<Producto, Integer> {
    Page<Producto> findByActivo(Boolean activo, Pageable pageable);
}

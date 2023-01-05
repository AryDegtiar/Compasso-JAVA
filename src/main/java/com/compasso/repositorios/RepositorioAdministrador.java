package com.compasso.repositorios;

import com.compasso.actores.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioAdministrador extends JpaRepository<Administrador, Integer> {
}

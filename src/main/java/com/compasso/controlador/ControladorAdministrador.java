package com.compasso.controlador;

import com.compasso.actores.Administrador;
import com.compasso.repositorios.RepositorioAdministrador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/administradores")
public class ControladorAdministrador {
    @Autowired private RepositorioAdministrador repositorioAdministrador;

    // modificar para que no se vea la contraseña

    @GetMapping(path = {"","/"})
    List<Administrador> administradorList(){
        return repositorioAdministrador.findAll();
    }


}

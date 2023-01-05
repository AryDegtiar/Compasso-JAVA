package com.compasso.controlador;

import com.compasso.DTO.AdministradorLogIn;
import com.compasso.actores.Administrador;
import com.compasso.productos.Producto;
import com.compasso.repositorios.RepositorioAdministrador;
import com.compasso.repositorios.RepositorioProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/administradores")
public class ControladorAdministrador {
    @Autowired
    private RepositorioAdministrador repositorioAdministrador;

    // modificar para que no se vea la contraseña

    @GetMapping(path = {"","/"})
    List<Administrador> administradorList(){
        return repositorioAdministrador.findAll();
    }

    @GetMapping(path = {"/{administradorID}"})
    ResponseEntity<?> administrador(@PathVariable("administradorID") Integer administradorID){
        if (repositorioAdministrador.findById(administradorID).isPresent()) {
            return ResponseEntity.ok(repositorioAdministrador.findById(administradorID).get());
        }else{
            return new ResponseEntity<>("Error, administrador no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = {"/login"})
    public ResponseEntity<?> logInAdminitrador (@RequestBody AdministradorLogIn administradorLogIn){
        Administrador admin = repositorioAdministrador.findByUsuarioAndContrasenia(administradorLogIn.getUsuario(), administradorLogIn.getContrasenia());
        if (admin != null){
            return new ResponseEntity<>(admin.getId(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Error, usuario o contraseña incorrectos", HttpStatus.BAD_REQUEST);
        }
    }

}

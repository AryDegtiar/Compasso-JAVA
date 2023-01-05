package com.compasso.controlador;


import com.compasso.DTO.ProductoPatch;
import com.compasso.DTO.ProductoPost;
import com.compasso.actores.Administrador;
import com.compasso.productos.Producto;
import com.compasso.repositorios.RepositorioAdministrador;
import com.compasso.repositorios.RepositorioProducto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/productos")
public class ControladorProducto {
// ver de poner validaciones paginacion y dto para fechas
    @Autowired
    private RepositorioProducto repositorioProducto;
    @Autowired
    private RepositorioAdministrador repositorioAdministrador;

    @GetMapping(path = {"","/"})
    Page<Producto> productoList(@RequestParam(required = false) Boolean activo,
                                @PageableDefault(size = 6) Pageable pageable){
        if (activo == null){
            return repositorioProducto.findAll(pageable);
        }else{
            return repositorioProducto.findByActivo(activo, pageable);
        }
    }

    @GetMapping(path = {"/{productoID}"})
    Producto producto(@PathVariable("productoID") Integer productoID){
        return repositorioProducto.findById(productoID).get();
    }

    @PostMapping(path = {"","/"})
    ResponseEntity<?> agregarProducto(@Valid @RequestBody ProductoPost productoPost, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return new ResponseEntity<>("Error, campos invalidos", HttpStatus.BAD_REQUEST);
        }

        Producto producto = new Producto(productoPost.getNombre(), productoPost.getDescripcion(), productoPost.getImagen(), productoPost.getFichaTecnica(), productoPost.getCreador());

        return ResponseEntity.ok(repositorioProducto.save(producto));
    }

    @PatchMapping(path = {"/{productoID}"})
    ResponseEntity<?> modificarProducto(@PathVariable("productoID") Integer productoID,
                                        @Valid @RequestBody ProductoPatch productoPatch, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return new ResponseEntity<>("Error, campos invalidos", HttpStatus.BAD_REQUEST);
        }

        if (repositorioProducto.existsById(productoID)){
            Producto producto = repositorioProducto.findById(productoID).get();
            Administrador admin = repositorioAdministrador.findByUsuario(producto.getCreador());

            System.out.println("admin: " + admin);

            // valida que solo pueda comidificar el creador
            if (productoPatch.getCreadorID() != admin.getId()) {
                return new ResponseEntity<>("Error, campos invalidos", HttpStatus.BAD_REQUEST);
            }

            if (productoPatch.getActivo() != null){
                producto.setActivo(productoPatch.getActivo());
            }
            if (productoPatch.getNombre() != null){
                producto.setNombre(productoPatch.getNombre());
            }
            if (productoPatch.getDescripcion() != null){
                producto.setDescripcion(productoPatch.getDescripcion());
            }
            if (productoPatch.getImagen() != null){
                producto.setImagen(productoPatch.getImagen());
            }
            if (productoPatch.getFichaTecnica() != null){
                producto.setFichaTecnica(productoPatch.getFichaTecnica());
            }

            producto.guardarFechaModificacion();
            return ResponseEntity.ok(repositorioProducto.save(producto));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = {"/{productoID}"})
    public void darDeBajaProducto(@PathVariable("productoID") Integer productoID){
        if (repositorioProducto.existsById(productoID)){
            Producto producto = repositorioProducto.findById(productoID).get();
            producto.setActivo(false);
            producto.guardarFechaModificacion();
            repositorioProducto.save(producto);
        }
    }
}
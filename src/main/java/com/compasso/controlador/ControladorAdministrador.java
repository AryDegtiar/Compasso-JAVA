package com.compasso.controlador;

import com.compasso.DTO.AdministradorLogIn;
import com.compasso.DTO.ProductoPatch;
import com.compasso.DTO.ProductoPost;
import com.compasso.actores.Administrador;
import com.compasso.imagenes.FileUploadResponse;
import com.compasso.imagenes.FileUtil;
import com.compasso.productos.Producto;
import com.compasso.repositorios.RepositorioAdministrador;
import com.compasso.repositorios.RepositorioProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/administradores")
public class ControladorAdministrador {
    @Autowired
    private RepositorioAdministrador repositorioAdministrador;
    @Autowired
    private RepositorioProducto repositorioProducto;

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

    @GetMapping(path = {"/{administradorID}/productos"})
    public ResponseEntity<?> administradorProductos(@PathVariable("administradorID") Integer administradorID){
        if (repositorioAdministrador.findById(administradorID).isPresent()) {
            Administrador admin = repositorioAdministrador.findById(administradorID).get();
            return new ResponseEntity<>(admin.getProductos(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Error, administrador no encontrado", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = {"/{administradorID}/productos/{productoID}"})
    public ResponseEntity<?> getAdminProducto(@PathVariable("administradorID") Integer administradorID, @PathVariable("productoID") Integer productoID){
        if (repositorioAdministrador.existsById(administradorID) && repositorioProducto.existsById(productoID)){
            Administrador admin = repositorioAdministrador.findById(administradorID).get();
            Producto prod = repositorioProducto.findById(productoID).get();

            if (admin.getProductos().contains(prod)){
                return new ResponseEntity<>(prod, HttpStatus.OK);
            }else{
                return new ResponseEntity<>("Error, campos invalidos", HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity<>("Error, administrador o producto no encontrado", HttpStatus.BAD_REQUEST);
    }

    @PostMapping(path = {"/{administradorID}/productos"})
    public ResponseEntity<?> agregarProducto(@PathVariable("administradorID") Integer administradorID, @RequestBody ProductoPost productoPost){
        if (repositorioAdministrador.findById(administradorID).isPresent()) {
            Administrador admin = repositorioAdministrador.findById(administradorID).get();
            Producto producto = new Producto(productoPost.getNombre(), productoPost.getDescripcion(), productoPost.getFichaTecnica());
            producto.setCreador(admin.getUsuario());
            admin.addProducto(producto);
            repositorioProducto.save(producto);
            return new ResponseEntity<>(producto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Error, administrador no encontrado", HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping(path = {"/{administradorID}/productos/{productoID}"})
    public ResponseEntity<?> modificarProducto(@PathVariable("administradorID") Integer administradorID,
                                               @PathVariable("productoID") Integer productoID, @RequestBody ProductoPatch productoPatch){

        if (repositorioAdministrador.findById(administradorID).isPresent()) {
            if (repositorioProducto.findById(productoID).isPresent()) {
                Administrador admin = repositorioAdministrador.findById(administradorID).get();
                Producto producto = repositorioProducto.findById(productoID).get();

                if (producto.getCreador().equals(admin.getUsuario())){

                    if (productoPatch.getNombre() != null){
                        producto.setNombre(productoPatch.getNombre());
                    }
                    if (productoPatch.getDescripcion() != null){
                        producto.setDescripcion(productoPatch.getDescripcion());
                    }
                    if (productoPatch.getFichaTecnica() != null){
                        producto.setFichaTecnica(productoPatch.getFichaTecnica());
                    }
                    if (productoPatch.getActivo() != null){
                        producto.setActivo(productoPatch.getActivo());
                    }

                    producto.setFechaModificacion(LocalDateTime.now());
                    repositorioProducto.save(producto);
                    return new ResponseEntity<>(producto, HttpStatus.OK);
                }else{
                    // el administrador no es el mismo que el creador
                    return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
                }

            }else{
                return new ResponseEntity<>("Error, producto no encontrado", HttpStatus.BAD_REQUEST);
            }
        }else{
            return new ResponseEntity<>("Error, administrador no encontrado", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = {"/{administradorID}/productos/{productoID}"})
    public ResponseEntity<?> eliminarProducto(@PathVariable("administradorID") Integer administradorID,
                                               @PathVariable("productoID") Integer productoID){

        if (repositorioAdministrador.findById(administradorID).isPresent()) {
            if (repositorioProducto.findById(productoID).isPresent()) {
                Administrador admin = repositorioAdministrador.findById(administradorID).get();
                Producto producto = repositorioProducto.findById(productoID).get();

                if (producto.getCreador().equals(admin.getUsuario())){
                    producto.setActivo(false);
                    repositorioProducto.save(producto);
                    return new ResponseEntity<>(producto, HttpStatus.OK);
                }else{
                    // el administrador no es el mismo que el creador
                    return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
                }

            }else{
                return new ResponseEntity<>("Error, producto no encontrado", HttpStatus.BAD_REQUEST);
            }
        }else{
            return new ResponseEntity<>("Error, administrador no encontrado", HttpStatus.BAD_REQUEST);
        }
    }

    //imagen
    @PostMapping(path = {"/{administradorID}/productos/{productoID}/imagen"})
    public ResponseEntity<?> uploadFile(@PathVariable("administradorID") Integer administradorID,
                                                         @PathVariable("productoID") Integer productoID,
                                                         @RequestParam("file") MultipartFile multipartFile) throws IOException {
        if (repositorioAdministrador.existsById(administradorID) && repositorioProducto.existsById(productoID)){
            Administrador admin = repositorioAdministrador.findById(administradorID).get();
            Producto producto = repositorioProducto.findById(productoID).get();

            if (producto.getCreador().equals(admin.getUsuario())) {
                String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                long size = multipartFile.getSize();

                String fileCode = FileUtil.saveFile(fileName, multipartFile);

                FileUploadResponse response = new FileUploadResponse();
                response.setFileName(fileName);
                response.setSize(String.valueOf(size));
                response.setDownloadUri("/productos/imagen/" + fileCode);

                producto.setImagen(response.getDownloadUri());
                repositorioProducto.save(producto);

                return new ResponseEntity<>(response, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }


}

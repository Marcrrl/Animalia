package com.animalia.spring.controladores;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.animalia.spring.entidades.Usuarios;
import com.animalia.spring.servicios.UsuarioServicio;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/usuarios")
public class UsuariosControlador {

    @Autowired
    private UsuarioServicio usuariosServicio;

    @GetMapping
    public ResponseEntity<List<Usuarios>> obtenerUsuarios() {
        return usuariosServicio.obtenerUsuarios().isEmpty() ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(usuariosServicio.obtenerUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuarios> obtenerUsuarioPorId(@PathVariable long id) {
        return ResponseEntity.ok(usuariosServicio.obtenerUsuarioPorId(id));
    }

    @GetMapping("/imagen/{nombreImagen}")
    public ResponseEntity<Resource> obtenerImagen(@PathVariable String nombreImagen) {
        try {
            // Cargar la imagen desde resources/static/
            Resource resource = new ClassPathResource("static/img/" + nombreImagen);

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/subir-imagen")
    public ResponseEntity<String> subirImagen(@RequestParam("file") MultipartFile file) {
        try {
            
            String uploadDir = "static/img/"; 
            Path path = Paths.get(uploadDir + file.getOriginalFilename());

            // Guarda la imagen en la carpeta
            Files.write(path, file.getBytes());

            return ResponseEntity.ok("Imagen subida exitosamente: " + file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al subir la imagen");
        }
    }

    @PostMapping
    public ResponseEntity<Usuarios> guardarUsuario(@RequestBody Usuarios usuario) {
        return ResponseEntity.ok(usuariosServicio.guardarUsuario(usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable long id) {
        usuariosServicio.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Usuarios> actualizarUsuario(@RequestBody Usuarios usuario) {
        return ResponseEntity.ok(usuariosServicio.actualizarUsuario(usuario));
    }

}

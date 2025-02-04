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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

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
@Tag(name = "Usuarios", description = "Operaciones relacionadas con usuarios")
public class UsuariosControlador {

    @Autowired
    private UsuarioServicio usuariosServicio;

    @GetMapping
    @Operation(summary = "Mostrar todos los usuarios del sistema", description = "Devuelve una lista con todos los usuarios del sistema")
    public ResponseEntity<List<Usuarios>> obtenerUsuarios() {
        return usuariosServicio.obtenerUsuarios().isEmpty() ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(usuariosServicio.obtenerUsuarios());
    }

    // @GetMapping("/{id}")
    // @Operation(summary = "Buscar usuario", description = "Buscar un usuario a partir de su id")
    // public ResponseEntity<Usuarios> obtenerUsuarioPorId(@PathVariable long id) {
    //     return ResponseEntity.ok(usuariosServicio.obtenerUsuarioPorId(id));
    // }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuario", description = "Buscar un usuario a partir de su id")
    public ResponseEntity<Usuarios> obtenerUsuarioPorId(@PathVariable long id) {
        return ResponseEntity.ok(usuariosServicio.obtenerUsuarioPorId(id));
    }

    @GetMapping("/imagen/")
    @Operation(summary = "Buscar una imagen", description = "Buscar una imagen de usuario a partir de su nombre")
    public ResponseEntity<Resource> obtenerImagen(@PathVariable String nombreImagen) {
        try {
            // Cargar la imagen desde resources/static/
            Resource resource = new ClassPathResource("static/img/" + nombreImagen);

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // Ajusta el tipo según la imagen
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/subir-imagen")
    @Operation(summary = "Subir una imagen", description = "Subir una imagen desde los archivos del sistema.")
    public ResponseEntity<String> subirImagen(@RequestBody MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("El archivo está vacío o no se ha enviado.");
        }
        try {
            String uploadDir = "backend/src/main/resources/static/img"; 
            Path uploadPath = Paths.get(uploadDir);

            // Crear el directorio si no existe
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path path = uploadPath.resolve(file.getOriginalFilename());

            // Guarda la imagen en la carpeta
            Files.write(path, file.getBytes());

            return ResponseEntity.ok("Imagen subida exitosamente: " + file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al subir la imagen");
        }
    }

    @PostMapping
     @Operation(summary = "Crear un usuario", description = "Crea un nuevo usuario en el sistema.")
    public ResponseEntity<Usuarios> guardarUsuario(@RequestBody Usuarios usuario) {
        return ResponseEntity.ok(usuariosServicio.guardarUsuario(usuario));
    }

    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario del sistema poniendo el id.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable long id) {
        usuariosServicio.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Editar usuario", description = "Editar usuario en el sistema.")
    @PutMapping
    public ResponseEntity<Usuarios> actualizarUsuario(@RequestBody Usuarios usuario) {
        return ResponseEntity.ok(usuariosServicio.actualizarUsuario(usuario));
    }

}

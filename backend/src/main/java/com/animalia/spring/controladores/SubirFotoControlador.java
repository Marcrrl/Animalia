package com.animalia.spring.controladores;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("api")
public class SubirFotoControlador {

    @PostMapping("/subir-imagen")
    @Operation(summary = "Subir una imagen", description = "Subir una imagen desde los archivos del sistema.")
    public ResponseEntity<Map<String, String>> subirImagen(@RequestBody MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "El archivo está vacío o no se ha enviado."));
        }
        try {
            String uploadDir = "backend/src/main/resources/static/img";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path path = uploadPath.resolve(file.getOriginalFilename());
            Files.write(path, file.getBytes());

            return ResponseEntity
                    .ok(Map.of("message", "Imagen subida exitosamente", "url_foto_perfil", file.getOriginalFilename()));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al subir la imagen"));
        }
    }
}

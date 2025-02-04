package com.animalia.spring.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.animalia.spring.entidades.Animales;
import com.animalia.spring.servicios.AnimalesServicio;

import org.springframework.core.io.Resource;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("api/animales")
public class AnimalesController {
    
    @Autowired
    private AnimalesServicio animalesServicio;

    
    @GetMapping
    public ResponseEntity<List<Animales>> obtenerAnimales() {
        return ResponseEntity.ok(animalesServicio.obtenerAnimales());
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<Animales> obtenerAnimalPorId(@PathVariable long id) {
        return ResponseEntity.ok(animalesServicio.obtenerAnimalPorId(id));
    }
    @GetMapping("/imagen/{nombreImagen}")
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

    
    // @GetMapping("/buscar/{busqueda}")
    // public ResponseEntity<List<Animales>> buscarAnimales(@PathVariable String busqueda) {
    //     return ResponseEntity.ok(animalesServicio.buscarAnimales(busqueda));
    // }

    @PostMapping
    public ResponseEntity<Animales> guardarAnimal(@RequestBody Animales animal) {
        return ResponseEntity.ok(animalesServicio.guardarAnimal(animal));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAnimal(@PathVariable long id) {
        animalesServicio.eliminarAnimal(id);;
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Animales> actualizarAnimal(@RequestBody Animales animal) {
        return ResponseEntity.ok(animalesServicio.actualizarAnimal(animal));
    }

}

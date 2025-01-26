package com.animalia.spring.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("api/animales")
public class AnimalesController {
    
    @Autowired
    private AnimalesServicio animalesServicio;

    // @GetMapping
    @GetMapping
    public ResponseEntity<List<Animales>> obtenerAnimales() {
        return ResponseEntity.ok(animalesServicio.obtenerAnimales());
    }

    // @GetMapping("/{id}")
    @GetMapping("/{id}")
    public ResponseEntity<Animales> obtenerAnimalPorId(@PathVariable long id) {
        return ResponseEntity.ok(animalesServicio.obtenerAnimalPorId(id));
    }

    // @GetMapping("/buscar/{busqueda}")
    @GetMapping("/buscar/{busqueda}")
    public ResponseEntity<List<Animales>> buscarAnimales(@PathVariable String busqueda) {
        return ResponseEntity.ok(animalesServicio.buscarAnimales(busqueda));
    }

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

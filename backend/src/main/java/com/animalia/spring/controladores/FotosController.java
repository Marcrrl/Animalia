package com.animalia.spring.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.animalia.spring.entidades.Fotos;
import com.animalia.spring.servicios.FotosServicio;

@RestController
@RequestMapping("api/fotos")
public class FotosController {

    @Autowired
    private FotosServicio fotosServicio;

    @GetMapping
    public ResponseEntity<List<Fotos>> obtenerFotos() {
        return fotosServicio.obtenerFotos().isEmpty() ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(fotosServicio.obtenerFotos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fotos> obtenerFotoPorId(@PathVariable long id) {
        return ResponseEntity.ok(fotosServicio.obtenerFotoPorId(id));
    }

    @PostMapping
    public ResponseEntity<Fotos> guardarFoto(@RequestBody Fotos foto) {
        return ResponseEntity.ok(fotosServicio.guardarFoto(foto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFoto(@PathVariable long id) {
        fotosServicio.eliminarFoto(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Fotos> actualizarFoto(@RequestBody Fotos foto) {
        return ResponseEntity.ok(fotosServicio.actualizarFoto(foto));
    
    }
}

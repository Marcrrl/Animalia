package com.animalia.spring.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.animalia.spring.entidades.Rescates;
import com.animalia.spring.servicios.RescatesServicio;

@RestController
@RequestMapping("api/rescates")
public class RescatesController {

    @Autowired
    private RescatesServicio rescatesServicio;

    @GetMapping
    public ResponseEntity<List<Rescates>> obtenerRescates() {
        return rescatesServicio.obtenerRescates().isEmpty() ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(rescatesServicio.obtenerRescates());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rescates> obtenerRescatePorId(@PathVariable long id) {
        return ResponseEntity.ok(rescatesServicio.obtenerRescatePorId(id));
    }

    @PostMapping
    public ResponseEntity<Rescates> guardarRescate(@RequestBody Rescates rescate) {
        return ResponseEntity.ok(rescatesServicio.guardarRescate(rescate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRescate(@PathVariable long id) {
        rescatesServicio.eliminarRescate(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Rescates> actualizarRescate(@RequestBody Rescates rescate) {
        return ResponseEntity.ok(rescatesServicio.actualizarRescate(rescate));
    
    }
}

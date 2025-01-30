package com.animalia.spring.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.animalia.spring.entidades.Rescates;
import com.animalia.spring.servicios.RescatesServicio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/rescates")
@Tag(name = "Rescates", description = "Operaciones relacionadas con rescates")
public class RescatesController {

    @Autowired
    private RescatesServicio rescatesServicio;

    @GetMapping
    @Operation(summary = "Mostrar todos los rescates del sistema", description = "Devuelve una lista con todos los rescates del sistema")
    public ResponseEntity<List<Rescates>> obtenerRescates() {
        return rescatesServicio.obtenerRescates().isEmpty() ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(rescatesServicio.obtenerRescates());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar un rscate a partir de su id", description = "Devuelve una lista con todos los usuarios del sistema")
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

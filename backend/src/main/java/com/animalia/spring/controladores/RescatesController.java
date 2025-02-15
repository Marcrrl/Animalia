package com.animalia.spring.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.animalia.spring.Excepciones.RescateNoEcontrada;
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

    @GetMapping("/todos")
    @Operation(summary = "Mostrar todos los rescates del sistema", description = "Devuelve una lista con todos los rescates del sistema")
    public ResponseEntity<List<Rescates>> obtenerRescates() {
        if (rescatesServicio.obtenerRescates().isEmpty()) {
            throw new RescateNoEcontrada();
        } else {
            return ResponseEntity.ok(rescatesServicio.obtenerRescates());
        }
    }

    @GetMapping
    @Operation(summary = "Mostrar todos los rescates del sistema paginados", description = "Devuelve una lista paginada con todos los rescates del sistema")
    public ResponseEntity<List<Rescates>> obtenerUsuariosPagebale(
            @PageableDefault(size = 5, page = 0) Pageable pageable) {
        Page<Rescates> Rescates = rescatesServicio.obtenerRescatesPaginacion(pageable);
        if (Rescates.isEmpty()) {
            throw new RescateNoEcontrada();
        } else {
            return ResponseEntity.ok(Rescates.getContent());
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar un rescate por ID", description = "Buscar un rescate a partir de su ID")
    public ResponseEntity<Rescates> obtenerRescatePorId(@PathVariable long id) {
        if (rescatesServicio.obtenerRescatePorId(id) == null) {
            throw new RescateNoEcontrada(id);
        } else {
            return ResponseEntity.ok(rescatesServicio.obtenerRescatePorId(id));
        }
    }

    @PostMapping
    @Operation(summary = "Guardar un rescate", description = "Guardar un nuevo rescate en el sistema")
    public ResponseEntity<Rescates> guardarRescate(@RequestBody Rescates rescate) {
        return ResponseEntity.ok(rescatesServicio.guardarRescate(rescate));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un rescate por ID", description = "Eliminar un rescate del sistema a partir de su ID")
    public ResponseEntity<Void> eliminarRescate(@PathVariable long id) {
        rescatesServicio.eliminarRescate(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    @Operation(summary = "Actualizar un rescate", description = "Actualizar los datos de un rescate en el sistema")
    public ResponseEntity<Rescates> actualizarRescate(@RequestBody Rescates rescate) {
        return ResponseEntity.ok(rescatesServicio.actualizarRescate(rescate));
    }
}

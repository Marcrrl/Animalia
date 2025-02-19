package com.animalia.spring.controladores;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import com.animalia.spring.Excepciones.EmpresaNoEcontrada;
import com.animalia.spring.Excepciones.FotosNoEcontrada;
import com.animalia.spring.entidades.Fotos;
import com.animalia.spring.entidades.DTO.FotoDTO;
import com.animalia.spring.servicios.FotosServicio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/fotos")
@Tag(name = "Fotos", description = "Operaciones relacionadas con fotos")
public class FotosController {

    @Autowired
    private FotosServicio fotosServicio;

    @GetMapping("/todos")
    @Operation(summary = "Mostrar todas las fotos del sistema", description = "Devuelve una lista con todas las fotos del sistema")
    public ResponseEntity<List<Fotos>> obtenerFotos() {
        if (fotosServicio.obtenerFotos().isEmpty()) {
            throw new FotosNoEcontrada();
        } else {
            return ResponseEntity.ok(fotosServicio.obtenerFotos());
        }
    }

    @GetMapping
    @Operation(summary = "Mostrar todas las fotos del sistema paginadas", description = "Devuelve una lista paginada con todas las fotos del sistema")
    public ResponseEntity<List<Fotos>> obtenerFotosPagebale(
            @PageableDefault(size = 5, page = 0) Pageable pageable) {
        Page<Fotos> Fotos = fotosServicio.obtenerEmpresasPaginacion(pageable);
        if (Fotos.isEmpty()) {
            throw new EmpresaNoEcontrada();
        } else {
            return ResponseEntity.ok(Fotos.getContent());
        }
    }

    @GetMapping("{id}/rescate")
    @Operation(summary = "Mostrar todas las fotos del sistema paginadas", description = "Devuelve una lista paginada con todas las fotos del sistema")
    public ResponseEntity<Long> obtenerIdRescates(@PathVariable long id) {
        Fotos f = fotosServicio.obtenerFotoPorId(id);
        Long idFotos = f.getRescate().getId();

        return ResponseEntity.ok(idFotos);
        
        
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar una foto por ID", description = "Buscar una foto a partir de su ID")
    public ResponseEntity<Fotos> obtenerFotoPorId(@PathVariable long id) {
        return ResponseEntity.ok(fotosServicio.obtenerFotoPorId(id));
    }

    @PostMapping("/crear")
    @Operation(summary = "Crear una foto con rescate y usuario", description = "Crear una nueva foto en el sistema asignando rescate y usuario por sus IDs")
    public ResponseEntity<Fotos> crearFoto(@RequestBody FotoDTO fotoDTO) {
        Fotos foto = fotosServicio.crearFoto(fotoDTO);
        if (foto != null) {
            return ResponseEntity.ok(foto);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    @Operation(summary = "Guardar una foto", description = "Guardar una nueva foto en el sistema")
    public ResponseEntity<Fotos> guardarFoto(@RequestBody Fotos foto) {
        return ResponseEntity.ok(fotosServicio.guardarFoto(foto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una foto por ID", description = "Eliminar una foto del sistema a partir de su ID")
    public ResponseEntity<Void> eliminarFoto(@PathVariable long id) {
        fotosServicio.eliminarFoto(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    @Operation(summary = "Actualizar una foto", description = "Actualizar los datos de una foto en el sistema")
    public ResponseEntity<Fotos> actualizarFoto(@RequestBody Fotos foto) {
        return ResponseEntity.ok(fotosServicio.actualizarFoto(foto));
    }

}

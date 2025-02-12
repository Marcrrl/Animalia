package com.animalia.spring.controladores;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import com.animalia.spring.Excepciones.EmpresaNoEcontrada;
import com.animalia.spring.Excepciones.FotosNoEcontrada;
import com.animalia.spring.entidades.Fotos;
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
    @Operation(summary = "Mostrar todos los fotos del sistema", description = "Devuelve una lista con todos los fotos del sistema")
    public ResponseEntity<List<Fotos>> obtenerFotos() {

        if (fotosServicio.obtenerFotos().isEmpty()) {
            throw new FotosNoEcontrada();
        } else {
            return ResponseEntity.ok(fotosServicio.obtenerFotos());
        }
    }

    @GetMapping
    @Operation(summary = "Mostrar todos los fotos del sistema", description = "Devuelve una lista con todos los fotos del sistema paginados")
    public ResponseEntity<List<Fotos>> obtenerUsuariosPagebale(
            @PageableDefault(size = 5, page = 0) Pageable pageable) {

        Page<Fotos> Fotos = fotosServicio.obtenerEmpresasPaginacion(pageable);

        if (Fotos.isEmpty()) {
            throw new EmpresaNoEcontrada();
        } else {
            return ResponseEntity.ok(Fotos.getContent());
        }
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

    @GetMapping("/imagen/{nombreImagen}")
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

}

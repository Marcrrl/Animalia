package com.animalia.spring.controladores;

import java.util.Base64;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.animalia.spring.Excepciones.RescateNoEcontrada;
import com.animalia.spring.entidades.Fotos;
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

    @GetMapping("/{id}/fotos")
    @Operation(summary = "Obtener URLs de imágenes de un rescate", description = "Devuelve una lista de URLs de imágenes en base64 de un rescate")
    public ResponseEntity<List<Map<String, String>>> obtenerImagenesBase64(@PathVariable long id) {
        Rescates rescate = rescatesServicio.obtenerRescatePorId(id);
        if (rescate == null) {
            throw new RescateNoEcontrada(id);
        }

        List<Map<String, String>> imagenesBase64 = new ArrayList<>();

        for (Fotos foto : rescate.getFotos()) {
            try {
                ClassPathResource imgFile = new ClassPathResource("static/img/" + foto.getUrl_foto());
                InputStream inputStream = imgFile.getInputStream();
                byte[] imageBytes = inputStream.readAllBytes();
                inputStream.close();

                String base64Image = Base64.getEncoder().encodeToString(imageBytes);

                Map<String, String> imagenData = new HashMap<>();
                imagenData.put("nombre", foto.getUrl_foto());
                imagenData.put("base64", "data:image/jpeg;base64," + base64Image);

                imagenesBase64.add(imagenData);
            } catch (IOException e) {
                throw new RuntimeException("Error al cargar la imagen: " + foto.getUrl_foto(), e);
            }
        }

        return ResponseEntity.ok(imagenesBase64);
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

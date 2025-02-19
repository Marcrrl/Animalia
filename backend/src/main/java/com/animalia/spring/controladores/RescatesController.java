package com.animalia.spring.controladores;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.animalia.spring.Excepciones.RescateNoEcontrada;
import com.animalia.spring.entidades.Fotos;
import com.animalia.spring.entidades.Rescates;
import com.animalia.spring.entidades.DTO.RescateDTO;
import com.animalia.spring.entidades.DTO.RescateDetalleDTO;
import com.animalia.spring.entidades.DTO.RescateCreacionDTO;
import com.animalia.spring.entidades.converter.RescateDtoConverter;
import com.animalia.spring.entidades.converter.RescateDetalleDtoConverter;
import com.animalia.spring.entidades.converter.RescateCreacionDtoConverter;
import com.animalia.spring.servicios.FotosServicio;
import com.animalia.spring.servicios.RescatesServicio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/rescates")
@Tag(name = "Rescates", description = "Operaciones relacionadas con rescates")
public class RescatesController {

    @Autowired
    private RescatesServicio rescatesServicio;

    @Autowired
    private FotosServicio fotosServicio;

    @Autowired
    private RescateDtoConverter rescateDtoConverter;

    @Autowired
    private RescateDetalleDtoConverter rescateDetalleDtoConverter;

    @Autowired
    private RescateCreacionDtoConverter rescateCreacionDtoConverter;

    @GetMapping("/todos")
    @Operation(summary = "Mostrar todos los rescates del sistema", description = "Devuelve una lista con todos los rescates del sistema")
    public ResponseEntity<List<Rescates>> obtenerRescates() {
        List<Rescates> rescates = rescatesServicio.obtenerRescates();
        if (rescates.isEmpty()) {
            throw new RescateNoEcontrada();
        } else {
            return ResponseEntity.ok(rescates);
        }
    }

    @GetMapping("/todos-sin-fotos")
    @Operation(summary = "Mostrar todos los rescates del sistema sin fotos", description = "Devuelve una lista con todos los rescates del sistema sin incluir las fotos")
    public ResponseEntity<List<RescateDTO>> obtenerRescatesSinFotos() {
        List<Rescates> rescates = rescatesServicio.obtenerRescates();
        if (rescates.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            List<RescateDTO> rescateDTOs = rescates.stream()
                    .map(rescateDtoConverter::convertRescateEntityToRescateDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(rescateDTOs);
        }
    }

    @GetMapping("/detalle")
    @Operation(summary = "Mostrar todos los rescates del sistema con detalles", description = "Devuelve una lista con todos los rescates del sistema con detalles")
    public ResponseEntity<List<RescateDetalleDTO>> obtenerRescatesConDetalles() {
        List<Rescates> rescates = rescatesServicio.obtenerRescates();
        if (rescates.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            List<RescateDetalleDTO> rescateDetalleDTOs = rescates.stream()
                    .map(rescateDetalleDtoConverter::convertRescateEntityToRescateDetalleDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(rescateDetalleDTOs);
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
        Rescates rescate = rescatesServicio.obtenerRescatePorId(id);
        if (rescate == null) {
            throw new RescateNoEcontrada(id);
        } else {
            return ResponseEntity.ok(rescate);
        }
    }

    @PostMapping
    @Operation(summary = "Guardar un rescate", description = "Guardar un nuevo rescate en el sistema")
    public ResponseEntity<Rescates> guardarRescate(@RequestBody Rescates rescate) {
        return ResponseEntity.ok(rescatesServicio.guardarRescate(rescate));
    }

    @PostMapping("/crear")
    @Operation(summary = "Crear un nuevo rescate", description = "Crear un nuevo rescate en el sistema")
    public ResponseEntity<Rescates> crearRescate(@RequestBody RescateCreacionDTO rescateCreacionDTO) {
        if (rescateCreacionDTO.getAnimalId() == null || rescateCreacionDTO.getUsuarioId() == null) {
            return ResponseEntity.badRequest().build();
        }
        Rescates rescate = rescateCreacionDtoConverter.convertRescateCreacionDtoToRescateEntity(rescateCreacionDTO);
        rescate.setEmpresa(null);
        rescate.setFotos(null);
        rescate.setEstado_rescate(Rescates.Estado.NO_ASIGNADO);
        rescate.setFecha_rescate(LocalDate.now());
        return ResponseEntity.ok(rescatesServicio.guardarRescate(rescate));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un rescate por ID", description = "Eliminar un rescate del sistema a partir de su ID")
    public ResponseEntity<Void> eliminarRescate(@PathVariable long id) {
        rescatesServicio.eliminarRescate(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un rescate", description = "Actualizar los datos de un rescate en el sistema")
    public ResponseEntity<Rescates> actualizarRescateDetalleDTO(@PathVariable long id,
            @RequestParam(required = false) Long empresaId, @RequestParam(required = false) Long usuarioId,
            @RequestParam(required = false) Long animalId, @RequestBody RescateDTO rescateDTO) {
        Rescates rescateActualizado = rescatesServicio.actualizarRescateDetalleDTO(id, empresaId, usuarioId, animalId,
                rescateDTO);
        if (rescateActualizado != null) {
            return ResponseEntity.ok(rescateActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/ubicacion")
    @Operation(summary = "Actualizar la ubicación de un rescate", description = "Actualizar la ubicación de un rescate en el sistema")
    public ResponseEntity<Rescates> actualizarUbicacionRescate(@PathVariable long id, @RequestBody String ubicacion) {
        Rescates rescate = rescatesServicio.obtenerRescatePorId(id);
        if (rescate != null) {
            rescate.setUbicacion(ubicacion);
            return ResponseEntity.ok(rescatesServicio.guardarRescate(rescate));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/fotos")
    @Operation(summary = "Actualizar la lista de fotos de un rescate", description = "Actualizar la lista de fotos de un rescate en el sistema")
    public ResponseEntity<Rescates> actualizarFotosRescate(@PathVariable long id, @RequestBody List<Long> fotosIds) {
        Rescates rescate = rescatesServicio.obtenerRescatePorId(id);
        if (rescate != null) {
            List<Fotos> fotos = fotosIds.stream()
                .map(fotoId -> {
                    Fotos foto = fotosServicio.obtenerFotoPorId(fotoId);
                    foto.setRescate(rescate);
                    return foto;
                })
                .collect(Collectors.toList());
            rescate.setFotos(fotos);
            return ResponseEntity.ok(rescatesServicio.guardarRescate(rescate));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/animal/{animalId}/foto")
    @Operation(summary = "Obtener ID de la foto relacionada a un rescate por ID del animal", description = "Devuelve la ID de la foto relacionada a un rescate buscando por la ID del animal")
    public ResponseEntity<Long> obtenerFotoIdPorAnimalId(@PathVariable long animalId) {
        Long fotoId = rescatesServicio.obtenerFotoIdPorAnimalId(animalId);
        if (fotoId != null) {
            return ResponseEntity.ok(fotoId);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/animal/{animalId}")
    @Operation(summary = "Obtener ID del rescate por ID del animal", description = "Devuelve la ID del rescate buscando por la ID del animal")
    public ResponseEntity<Long> obtenerRescateIdPorAnimalId(@PathVariable long animalId) {
        Long rescateId = rescatesServicio.obtenerRescateIdPorAnimalId(animalId);
        if (rescateId != null) {
            return ResponseEntity.ok(rescateId);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

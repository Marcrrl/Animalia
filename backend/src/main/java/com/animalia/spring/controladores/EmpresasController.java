package com.animalia.spring.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.animalia.spring.Excepciones.EmpresaNoEcontrada;
import com.animalia.spring.entidades.Empresas;
import com.animalia.spring.servicios.EmpresasServicio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/empresas")
@Tag(name = "Empresas", description = "Operaciones relacionadas con empresas")
public class EmpresasController {

    @Autowired
    private EmpresasServicio empresasServicio;

    @GetMapping("/todos")
    @Operation(summary = "Mostrar todas las empresas del sistema", description = "Devuelve una lista con todas las empresas del sistema")
    public ResponseEntity<List<Empresas>> obtenerEmpresas() {
        if (empresasServicio.obtenerEmpresas().isEmpty()) {
            throw new EmpresaNoEcontrada();
        } else {
            return ResponseEntity.ok(empresasServicio.obtenerEmpresas());
        }
    }

    @GetMapping
    @Operation(summary = "Mostrar todas las empresas del sistema paginadas", description = "Devuelve una lista paginada con todas las empresas del sistema")
    public ResponseEntity<List<Empresas>> obtenerUsuariosPagebale(
            @PageableDefault(size = 5, page = 0) Pageable pageable) {
        Page<Empresas> empresas = empresasServicio.obtenerEmpresasPaginacion(pageable);
        if (empresas.isEmpty()) {
            throw new EmpresaNoEcontrada();
        } else {
            return ResponseEntity.ok(empresas.getContent());
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar una empresa por ID", description = "Buscar una empresa a partir de su ID")
    public ResponseEntity<Empresas> obtenerEmpresaPorId(@PathVariable long id) {
        Empresas e = empresasServicio.obtenerEmpresaPorId(id);
        if (e == null) {
            throw new EmpresaNoEcontrada(id);
        } else {
            return ResponseEntity.ok(e);
        }
    }

    @PostMapping
    @Operation(summary = "Guardar una empresa", description = "Guardar una nueva empresa en el sistema")
    public ResponseEntity<Empresas> guardarEmpresa(@RequestBody Empresas empresa) {
        return ResponseEntity.ok(empresasServicio.guardarEmpresa(empresa));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una empresa por ID", description = "Eliminar una empresa del sistema a partir de su ID")
    public ResponseEntity<Void> eliminarEmpresa(@PathVariable long id) {
        empresasServicio.eliminarEmpresa(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    @Operation(summary = "Actualizar una empresa", description = "Actualizar los datos de una empresa en el sistema")
    public ResponseEntity<Empresas> actualizarEmpresa(@RequestBody Empresas empresa) {
        return ResponseEntity.ok(empresasServicio.actualizarEmpresa(empresa));
    }
}

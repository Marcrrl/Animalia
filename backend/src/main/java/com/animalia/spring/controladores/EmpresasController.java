package com.animalia.spring.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.animalia.spring.entidades.Empresas;
import com.animalia.spring.servicios.EmpresasServicio;

@RestController
@RequestMapping("api/empresas")
public class EmpresasController {

    @Autowired
    private EmpresasServicio empresasServicio;

    @GetMapping
    public ResponseEntity<List<Empresas>> obtenerEmpresas() {
        return empresasServicio.obtenerEmpresas().isEmpty() ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(empresasServicio.obtenerEmpresas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empresas> obtenerEmpresaPorId(@PathVariable long id) {
        return ResponseEntity.ok(empresasServicio.obtenerEmpresaPorId(id));
    }

    @PostMapping
    public ResponseEntity<Empresas> guardarEmpresa(@RequestBody Empresas empresa) {
        return ResponseEntity.ok(empresasServicio.guardarEmpresa(empresa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpresa(@PathVariable long id) {
        empresasServicio.eliminarEmpresa(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Empresas> actualizarEmpresa(@RequestBody Empresas empresa) {
        return ResponseEntity.ok(empresasServicio.actualizarEmpresa(empresa));
    
    }
}

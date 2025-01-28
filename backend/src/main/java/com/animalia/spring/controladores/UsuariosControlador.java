package com.animalia.spring.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.animalia.spring.entidades.Animales;
import com.animalia.spring.entidades.Usuarios;
import com.animalia.spring.servicios.UsuarioServicio;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/usuarios")
public class UsuariosControlador {

    @Autowired
    private UsuarioServicio usuariosServicio;

    @GetMapping
    public ResponseEntity<List<Usuarios>> obtenerUsuarios() {
        return usuariosServicio.obtenerUsuarios().isEmpty() ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(usuariosServicio.obtenerUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuarios> obtenerUsuarioPorId(@PathVariable long id) {
        return ResponseEntity.ok(usuariosServicio.obtenerUsuarioPorId(id));
    }

    @PostMapping
    public ResponseEntity<Usuarios> guardarUsuario(@RequestBody Usuarios usuario) {
        return ResponseEntity.ok(usuariosServicio.guardarUsuario(usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable long id) {
        usuariosServicio.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Usuarios> actualizarUsuario(@RequestBody Usuarios usuario) {
        return ResponseEntity.ok(usuariosServicio.actualizarUsuario(usuario));
    }

}

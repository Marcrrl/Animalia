package com.animalia.spring.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.animalia.spring.entidades.Usuarios;
import com.animalia.spring.servicios.UsuarioServicio;
import org.springframework.web.bind.annotation.GetMapping;

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

}

package com.animalia.spring.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.animalia.spring.entidades.Usuarios;
import com.animalia.spring.repositorio.UsuarioRepositorio;

@Service
public class UsuarioServicio {
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    public List<Usuarios> obtenerUsuarios() {
        return usuarioRepositorio.findAll();
    }
}

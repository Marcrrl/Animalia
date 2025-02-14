package com.animalia.spring.servicios;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.animalia.spring.entidades.Usuarios;
import com.animalia.spring.repositorio.UsuarioRepositorio;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    public Page<Usuarios> obtenerUsuariosPaginacion(Pageable pageable) {
        return usuarioRepositorio.findAll(pageable);
    }

    public List<Usuarios> obtenerUsuarios() {
        return usuarioRepositorio.findAll();
    }

    public Usuarios obtenerUsuarioPorId(long id) {
        return usuarioRepositorio.findById(id).orElse(null);
    }

    public Usuarios guardarUsuario(Usuarios usuario) {
        return usuarioRepositorio.save(usuario);
    }

    public void eliminarUsuario(long id) {
        usuarioRepositorio.deleteById(id);
    }

    public Usuarios actualizarUsuario(Usuarios usuario) {
        return usuarioRepositorio.save(usuario);
    }

    public Usuarios obtenerUsuarioPorCorreo(String correo) {
        return usuarioRepositorio.findByEmail(correo);
    }
}

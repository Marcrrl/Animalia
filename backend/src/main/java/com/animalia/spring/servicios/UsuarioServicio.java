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

    public Usuarios obtenerUsuarioPorId(long id) {
        return usuarioRepositorio.findById(id).get();
    }

    public Usuarios guardarUsuario(Usuarios usuario) {
        return usuarioRepositorio.save(usuario);
    }

    public void eliminarUsuario(int id) {
        usuarioRepositorio.deleteById((long) id);
    }

    public void actualizarUsuario(Usuarios usuario) {
        usuarioRepositorio.save(usuario);
    }

    public Usuarios obtenerUsuarioPorCorreo(String correo) {
        return usuarioRepositorio.findByCorreo(correo);
    }

    public Usuarios obtenerUsuarioPorNombre(String nombre) {
        return usuarioRepositorio.findByNombre(nombre);
    }

    public List<Usuarios> search(String consulta) {
        return usuarioRepositorio.findByNombreContainsIgnoreCaseOrCorreoContainsIgnoreCaseOrTelefonoContainsIgnoreCase(consulta, consulta, consulta);
    }

}

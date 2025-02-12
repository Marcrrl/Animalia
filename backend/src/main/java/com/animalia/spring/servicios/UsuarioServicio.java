package com.animalia.spring.servicios;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.animalia.spring.entidades.Usuarios;
import com.animalia.spring.repositorio.UsuarioRepositorio;

@Service
public class UsuarioServicio {
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Page<Usuarios> obtenerUsuariosPaginacion(Pageable pageable) {
        return usuarioRepositorio.findAll(pageable);
    }
    public List<Usuarios> obtenerUsuarios() {
        return usuarioRepositorio.findAll();
    }
    public Usuarios obtenerUsuarioPorId(long id) {
        return usuarioRepositorio.findById(id).get();
    }

    public Usuarios obtenerFotoUsuarioPorId(long id) {
        return usuarioRepositorio.findById(id).get();
    }

    public Usuarios guardarUsuario(Usuarios usuario) {
        String encodedPassword = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(encodedPassword);
        return usuarioRepositorio.save(usuario);
    }

    public void eliminarUsuario(long id) {
        usuarioRepositorio.deleteById( id);
    }

    public Usuarios actualizarUsuario(Usuarios usuario) {
        return usuarioRepositorio.save(usuario);
    }

    public Usuarios obtenerUsuarioPorCorreo(String correo) {
        return usuarioRepositorio.findByEmail(correo);
    }

    // public Usuarios obtenerUsuarioPorNombre(String nombre) {
    //     return usuarioRepositorio.findByNombre(nombre);
    // }

    // public List<Usuarios> search(String consulta) {
    //     return usuarioRepositorio.findByNombreContainsIgnoreCaseOrCorreoContainsIgnoreCaseOrTelefonoContainsIgnoreCase(consulta, consulta, consulta);
    // }

}

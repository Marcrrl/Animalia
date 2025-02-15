package com.animalia.spring.servicios;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.animalia.spring.entidades.UsuarioDTO;
import com.animalia.spring.entidades.Usuarios;
import com.animalia.spring.entidades.Usuarios.TipoUsuario;
import com.animalia.spring.entidades.converter.UserDtoConverter;
import com.animalia.spring.repositorio.UsuarioRepositorio;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private UserDtoConverter userDtoConverter;

    public Page<UsuarioDTO> obtenerUsuariosPaginacion(Pageable pageable) {
        return usuarioRepositorio.findAll(pageable).map(userDtoConverter::convertUserEntityToUserDto);
    }

    public List<UsuarioDTO> obtenerUsuarios() {
        return usuarioRepositorio.findAll().stream()
                .map(userDtoConverter::convertUserEntityToUserDto)
                .collect(Collectors.toList());
    }

    public UsuarioDTO obtenerUsuarioDTOPorId(long id) {
        return usuarioRepositorio.findById(id)
                .map(userDtoConverter::convertUserEntityToUserDto)
                .orElse(null);
    }
    public Usuarios obtenerUsuarioPorId(long id) {
        return usuarioRepositorio.findById(id).orElse(null);
    }
    public UsuarioDTO guardarUsuario(Usuarios usuario) {
        return userDtoConverter.convertUserEntityToUserDto(usuarioRepositorio.save(usuario));
    }

    public void eliminarUsuario(long id) {
        usuarioRepositorio.deleteById(id);
    }

    public UsuarioDTO actualizarUsuario(Usuarios usuario) {
        return userDtoConverter.convertUserEntityToUserDto(usuarioRepositorio.save(usuario));
    }

    public UsuarioDTO obtenerUsuarioPorCorreo(String correo) {
        return userDtoConverter.convertUserEntityToUserDto(usuarioRepositorio.findByEmail(correo));
    }

    public List<UsuarioDTO> obtenerUsuariosPorTipo(TipoUsuario tipoUsuario) {
        return usuarioRepositorio.findByTipoUsuario(tipoUsuario).stream()
                .map(userDtoConverter::convertUserEntityToUserDto)
                .collect(Collectors.toList());
    }

    // Method to return UserDetails for authentication
    public Usuarios obtenerUsuarioPorCorreoParaAutenticacion(String correo) {
        return usuarioRepositorio.findByEmail(correo);
    }
}

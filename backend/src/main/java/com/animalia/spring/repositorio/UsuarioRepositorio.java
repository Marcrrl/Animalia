package com.animalia.spring.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.animalia.spring.entidades.Usuarios;

public interface UsuarioRepositorio  extends JpaRepository<Usuarios, Long> {
    public Usuarios findByNombre(String nombre);
    public Usuarios findByCorreo(String correo);
    List<Usuarios> findByNombreContainsIgnoreCaseOrCorreoContainsIgnoreCaseOrTelefonoContainsIgnoreCase(String nombre,String correo,String telefono);

}

package com.animalia.spring.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.animalia.spring.entidades.Usuarios;

public interface UsuarioRepositorio extends JpaRepository<Usuarios, Long> {

    Usuarios findByEmail(String email);

}

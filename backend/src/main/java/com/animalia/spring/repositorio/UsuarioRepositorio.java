package com.animalia.spring.repositorio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.animalia.spring.entidades.Usuarios;

public interface UsuarioRepositorio extends JpaRepository<Usuarios, Long> {

    Page<Usuarios> findAll(Pageable pageable);

    Usuarios findByEmail(String email);

}

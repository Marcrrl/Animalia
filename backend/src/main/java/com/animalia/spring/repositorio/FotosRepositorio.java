package com.animalia.spring.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.animalia.spring.entidades.Fotos;

public interface FotosRepositorio extends JpaRepository<Fotos, Long> {
    // List<Fotos> findByUsuarios(Usuarios usuarios);

    // List<Fotos> findByRescate(Rescates rescate);
}

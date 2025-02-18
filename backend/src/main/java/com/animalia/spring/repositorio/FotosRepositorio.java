package com.animalia.spring.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.animalia.spring.entidades.Fotos;

@Repository
public interface FotosRepositorio extends JpaRepository<Fotos, Long> {
    // List<Fotos> findByUsuarios(Usuarios usuarios);

    // List<Fotos> findByRescate(Rescates rescate);
}

package com.animalia.spring.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.animalia.spring.entidades.Fotos;
import com.animalia.spring.entidades.Rescates;
import com.animalia.spring.entidades.Usuarios;

public interface FotosRepositorio extends JpaRepository<Fotos, Long> {
    List<Fotos> findByUsuarios(Usuarios usuarios);

    List<Fotos> findByRescates(Rescates rescates);
}

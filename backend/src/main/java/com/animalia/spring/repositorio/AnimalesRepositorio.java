package com.animalia.spring.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.animalia.spring.entidades.Animales;

public interface AnimalesRepositorio extends JpaRepository<Animales, Long> {
    List<Animales> findByEstado(String estado);

    List<Animales> findEspecieContainsIgnoreCaseOrNombre_comun(String especie, String nombre);
}

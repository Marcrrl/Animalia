package com.animalia.spring.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.animalia.spring.entidades.Animales;

public interface AnimalesRepositorio extends JpaRepository<Animales, Long> {

}

package com.animalia.spring.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.animalia.spring.entidades.Fotos;

public interface FotosRepositorio extends JpaRepository<Fotos, Long> {

}

package com.animalia.spring.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.animalia.spring.entidades.Empresas;

public interface EmpresasRepositorio extends JpaRepository<Empresas, Long> {

}

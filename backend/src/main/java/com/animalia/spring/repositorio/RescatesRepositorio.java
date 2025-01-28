package com.animalia.spring.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.animalia.spring.entidades.Rescates;


public interface RescatesRepositorio extends JpaRepository<Rescates, Long> {

    // List<Rescates> findByEstado_rescate(Estado estado_rescate);

    // List<Rescates> findByFecha_rescate(String fecha_rescate);

    // List<Rescates> findByUbicacionContainsIgnoreCase(String ubicacion);

    // List<Rescates> findByEmpresaNombreContainsIgnoreCase(String nombre);

    // List<Rescates> findByUsuarioNombreContainsIgnoreCase(String nombre);

    // List<Rescates> findByAnimalEspecieContainsIgnoreCaseOrAnimalNombre_comunContainsIgnoreCase(String especie, String nombre_comun);

    // List<Rescates> findByEmpresa(Empresas empresa);

    // List<Rescates> findByUsuario(Usuarios usuario);

    // List<Rescates> findByAnimal(Animales animal);
}

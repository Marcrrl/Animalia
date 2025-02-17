package com.animalia.spring.repositorio;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.animalia.spring.entidades.Rescates;

public interface RescatesRepositorio extends JpaRepository<Rescates, Long> {

    @Query("SELECT r FROM Rescates r WHERE function('ST_DWithin', r.ubicacion, function('ST_SetSRID', function('ST_GeomFromText', :ubicacion), 4326), 5000) = true")
    List<Rescates> findByUbicacionCercana(@Param("ubicacion") String ubicacion);

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

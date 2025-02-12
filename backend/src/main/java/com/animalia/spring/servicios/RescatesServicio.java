package com.animalia.spring.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.animalia.spring.entidades.Rescates;
import com.animalia.spring.repositorio.RescatesRepositorio;

@Service
public class RescatesServicio {

    @Autowired
    private RescatesRepositorio rescatesRepositorio;
    
    public List<Rescates> obtenerRescates() {
        return rescatesRepositorio.findAll();
    }

    public Rescates obtenerRescatePorId(long id) {
        return rescatesRepositorio.findById(id).get();
    }

    public Rescates guardarRescate(Rescates rescate) {
        return rescatesRepositorio.save(rescate);
    }

    public void eliminarRescate(long id) {
        rescatesRepositorio.deleteById(id);
    }

    public Rescates actualizarRescate(Rescates rescate) {
        return rescatesRepositorio.save(rescate);
    }

    public Page<Rescates> obtenerRescatesPaginacion(Pageable pageable) {
        return rescatesRepositorio.findAll(pageable);
    }

    // public List<Rescates> obtenerRescatesPorEmpresa(Empresas empresa){
    //     return rescatesRepositorio.findByEmpresa(empresa);
    // }

    // public List<Rescates> obtenerRescatesPorUbicacion(String ubicacion){
    //     return rescatesRepositorio.findByUbicacionContainsIgnoreCase(ubicacion);
    // }

    // public List<Rescates> obtenerRescatesPorFecha(String fecha){
    //     return rescatesRepositorio.findByFecha_rescate(fecha);
    // }

    // public List<Rescates> obtenerRescatesPorEstadoRescate(Estado estado){
    //     return rescatesRepositorio.findByEstado_rescate(estado);
    // }

    // public List<Rescates> obtenerRescatesPorAnimal(String especie, String nombre_comun){
    //     return rescatesRepositorio.findByAnimalEspecieContainsIgnoreCaseOrAnimalNombre_comunContainsIgnoreCase(especie, nombre_comun);
    // }

    // public List<Rescates> obtenerRescatesPorUsuario(Usuarios usuario){
    //     return rescatesRepositorio.findByUsuario(usuario);
    // }

    // public List<Rescates> obtenerRescatesPorEmpresaNombre(String nombre){
    //     return rescatesRepositorio.findByEmpresaNombreContainsIgnoreCase(nombre);
    // }

    // public List<Rescates> obtenerRescatesPorUsuarioNombre(String nombre){
    //     return rescatesRepositorio.findByUsuarioNombreContainsIgnoreCase(nombre);
    // }

    // public List<Rescates> obtenerRescatesPorAnimal(Animales animal){
    //     return rescatesRepositorio.findByAnimal(animal);
    // }
}

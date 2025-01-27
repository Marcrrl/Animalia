package com.animalia.spring.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.animalia.spring.entidades.Animales;
import com.animalia.spring.repositorio.AnimalesRepositorio;

@Service
public class AnimalesServicio {
    @Autowired
    private AnimalesRepositorio animalesRepositorio;

    public List<Animales> obtenerAnimales() {
        return animalesRepositorio.findAll();
    }

    public Animales obtenerAnimalPorId(long id) {
        return animalesRepositorio.findById(id).get();
    }

    public Animales guardarAnimal(Animales animal) {
        return animalesRepositorio.save(animal);
    }

    public void eliminarAnimal(long id) {
        animalesRepositorio.deleteById(id);
    }

    public Animales actualizarAnimal(Animales animal) {
        return animalesRepositorio.save(animal);
    }

    // public List<Animales> buscarAnimales(String busqueda) {
    //     return animalesRepositorio.findEspecieContainsIgnoreCaseOrNombre_comun(busqueda, busqueda);
    // }
}

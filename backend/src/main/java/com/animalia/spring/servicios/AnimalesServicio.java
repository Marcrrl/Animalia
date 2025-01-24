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
}

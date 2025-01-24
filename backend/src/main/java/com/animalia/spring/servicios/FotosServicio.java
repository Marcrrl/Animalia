package com.animalia.spring.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.animalia.spring.entidades.Fotos;
import com.animalia.spring.repositorio.FotosRepositorio;

@Service
public class FotosServicio {

    @Autowired
    private FotosRepositorio fotosRepositorio;

    public List<Fotos> obtenerFotos() {
        return fotosRepositorio.findAll();
    }
    
}

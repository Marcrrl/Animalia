package com.animalia.spring.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
}

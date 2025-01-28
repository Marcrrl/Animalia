package com.animalia.spring.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.animalia.spring.entidades.Fotos;
import com.animalia.spring.entidades.Rescates;
import com.animalia.spring.entidades.Usuarios;
import com.animalia.spring.repositorio.FotosRepositorio;

@Service
public class FotosServicio {

    @Autowired
    private FotosRepositorio fotosRepositorio;

    public List<Fotos> obtenerFotos() {
        return fotosRepositorio.findAll();
    }

    public Fotos obtenerFotoPorId(long id) {
        return fotosRepositorio.findById(id).get();
    }

    public Fotos guardarFoto(Fotos foto) {
        return fotosRepositorio.save(foto);
    }

    public void eliminarFoto(long id) {
        fotosRepositorio.deleteById(id);
    }

    public Fotos actualizarFoto(Fotos foto) {
        return fotosRepositorio.save(foto);
    }

    public List<Fotos> obtenerFotosPorUsuario(Usuarios usuario) {
        return fotosRepositorio.findByUsuarios(usuario);
    }

    public List<Fotos> obtenerFotosDeRescate(Rescates rescate){
        return fotosRepositorio.findByRescates(rescate);
    }
    
}

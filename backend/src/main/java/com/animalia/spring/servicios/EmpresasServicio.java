package com.animalia.spring.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.animalia.spring.entidades.Empresas;
import com.animalia.spring.repositorio.EmpresasRepositorio;

@Service("empresasServicio")
public class EmpresasServicio {
    @Autowired
    private EmpresasRepositorio empresasRepositorio;

    public Empresas obtenerEmpresaPorId(long id) {
        return empresasRepositorio.findById(id).get();
    }

    public Empresas guardarEmpresa(Empresas empresa) {
        return empresasRepositorio.save(empresa);
    }

    public void eliminarEmpresa(int id) {
        empresasRepositorio.deleteById((long) id);
    }

    public void actualizarEmpresa(Empresas empresa) {
        empresasRepositorio.save(empresa);
    }

    public List<Empresas> obtenerEmpresaPorNombre(String nombre) {
        return empresasRepositorio.findByNombre(nombre);
    }

    public List<Empresas> obtenerEmpresas() {
        return empresasRepositorio.findAll();
    }

}

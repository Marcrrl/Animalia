package com.animalia.spring.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.animalia.spring.entidades.Empresas;
import com.animalia.spring.entidades.DTO.EmpresaDTO;
import com.animalia.spring.entidades.converter.EmpresaDtoConverter;
import com.animalia.spring.repositorio.EmpresasRepositorio;

@Service("empresasServicio")
public class EmpresasServicio {

    @Autowired
    private EmpresasRepositorio empresasRepositorio;

    @Autowired
    private EmpresaDtoConverter empresaDtoConverter;

    public Empresas obtenerEmpresaPorId(long id) {
        return empresasRepositorio.findByIdActive(id).orElse(null);
    }

    public Empresas guardarEmpresa(Empresas empresa) {
        return empresasRepositorio.save(empresa);
    }

    public void eliminarEmpresa(long id) {
        Empresas empresa = empresasRepositorio.findById(id).orElse(null);
        if (empresa != null) {
            empresa.setDeleted(true);
            empresasRepositorio.save(empresa);
        }
    }

    public Empresas actualizarEmpresa(EmpresaDTO empresaDTO) {
        Empresas existingEmpresa = empresasRepositorio.findById(empresaDTO.getId()).orElse(null);
        if (existingEmpresa != null) {
            Empresas empresa = empresaDtoConverter.convertEmpresaDtoToEmpresaEntity(empresaDTO);
            empresa.setId(existingEmpresa.getId());
            return empresasRepositorio.save(empresa);
        }
        return null;
    }

    public List<Empresas> obtenerEmpresas() {
        List<Empresas> empresas = empresasRepositorio.findAllActive();
        if (empresas.isEmpty()) {
            return List.of();
        }
        return empresas;
    }

    public List<Empresas> obtenerTodasLasEmpresas() {
        return empresasRepositorio.findAll();
    }

    public Page<Empresas> obtenerEmpresasPaginacion(Pageable pageable) {
        return empresasRepositorio.findAllActive(pageable);
    }

    public List<Empresas> obtenerEmpresaPorNombre(String nombre) {
        return empresasRepositorio.findByNombre(nombre);
    }
}

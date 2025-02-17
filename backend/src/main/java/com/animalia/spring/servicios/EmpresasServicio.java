package com.animalia.spring.servicios;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.animalia.spring.entidades.Empresas;
import com.animalia.spring.entidades.Usuarios;
import com.animalia.spring.entidades.DTO.EmpresaDTO;
import com.animalia.spring.entidades.converter.EmpresaDtoConverter;
import com.animalia.spring.repositorio.EmpresasRepositorio;
import com.animalia.spring.repositorio.UsuarioRepositorio;

@Service("empresasServicio")
public class EmpresasServicio {

    @Autowired
    private EmpresasRepositorio empresasRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private EmpresaDtoConverter empresaDtoConverter;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Empresas obtenerEmpresaPorId(long id) {
        return empresasRepositorio.findByIdActive(id).orElse(null);
    }

    public Empresas guardarEmpresa(EmpresaDTO empresaDTO) {
        Empresas empresa = empresaDtoConverter.convertEmpresaDtoToEmpresaEntity(empresaDTO);
        Empresas savedEmpresa = empresasRepositorio.save(empresa);

        Usuarios usuario = new Usuarios();
        usuario.setNombre(empresa.getNombre());
        usuario.setApellido("");
        usuario.setEmail(empresa.getEmail());
        usuario.setPassword(passwordEncoder.encode(empresaDTO.getPassword()));
        usuario.setTelefono(empresa.getTelefono());
        usuario.setDireccion(empresa.getDireccion());
        usuario.setUrl_foto_perfil("iconoBase.png");
        usuario.setTipoUsuario(Usuarios.TipoUsuario.EMPRESA);
        usuario.setFecha_registro(empresa.getFecha_creacion());
        usuario.setCantidad_rescates(0);
        usuario.setEmpresa(savedEmpresa);
        usuario.setDeleted(false);

        usuarioRepositorio.save(usuario);

        return savedEmpresa;
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

    public Empresas agregarUsuarioAEmpresa(Long empresaId, Long usuarioId) {
        Empresas empresa = empresasRepositorio.findById(empresaId).orElse(null);
        Usuarios usuario = usuarioRepositorio.findById(usuarioId).orElse(null);
        if (empresa != null && usuario != null) {
            usuario.setEmpresa(empresa);
            usuarioRepositorio.save(usuario);
            empresa.getUsuarios().add(usuario);
            return empresasRepositorio.save(empresa);
        }
        return null;
    }

    public Empresas eliminarUsuarioDeEmpresa(Long empresaId, Long usuarioId) {
        Empresas empresa = empresasRepositorio.findById(empresaId).orElse(null);
        Usuarios usuario = usuarioRepositorio.findById(usuarioId).orElse(null);
        if (empresa != null && usuario != null) {
            usuario.setEmpresa(null);
            usuarioRepositorio.save(usuario);
            empresa.getUsuarios().remove(usuario);
            return empresasRepositorio.save(empresa);
        }
        return null;
    }

    public Set<Usuarios> obtenerUsuariosDeEmpresa(Long empresaId) {
        Empresas empresa = empresasRepositorio.findById(empresaId).orElse(null);
        if (empresa != null) {
            return empresa.getUsuarios();
        }
        return Set.of();
    }
}

package com.animalia.spring.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.animalia.spring.entidades.Usuarios;
import com.animalia.spring.entidades.Usuarios.TipoUsuario;

public interface UsuarioRepositorio extends JpaRepository<Usuarios, Long> {

    Usuarios findByEmail(String email);

    

    List<Usuarios> findByTipoUsuario(TipoUsuario tipoUsuario);


}

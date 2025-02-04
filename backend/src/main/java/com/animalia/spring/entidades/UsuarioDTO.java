package com.animalia.spring.entidades;

import com.animalia.spring.entidades.Usuarios.TipoUsuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class UsuarioDTO {

    private String email;
    private TipoUsuario tipo_usuario;
}

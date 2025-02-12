package com.animalia.spring.entidades;

import com.animalia.spring.entidades.Usuarios.TipoUsuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor @Builder
public class UsuarioDTO {

    private Long id;
    private TipoUsuario tipo_usuario;
}

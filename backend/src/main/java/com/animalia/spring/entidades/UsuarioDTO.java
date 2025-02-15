package com.animalia.spring.entidades;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String direccion;
    private String url_foto_perfil;
    private Usuarios.TipoUsuario tipoUsuario;
    private LocalDate fecha_registro;
    private long cantidad_rescates;
}

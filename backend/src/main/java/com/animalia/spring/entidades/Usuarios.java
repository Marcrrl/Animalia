package com.animalia.spring.entidades;

import java.sql.Date;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class Usuarios {
    private long id;
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String telefono;
    private String direccion;
    private Date fecha_nacimiento;
    private String url_foto_perfil;
    private TipoUsuario tipo_usuario;
    private Date fecha_registro;
    private long cantidad_rescates;
}

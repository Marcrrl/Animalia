package com.animalia.spring.entidades.DTO;

import java.time.LocalDate;

import com.animalia.spring.entidades.Empresas.TipoEmpresa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpresaDTO {
    private Long id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String email;
    private TipoEmpresa tipo;
    private String url_web;
    private LocalDate fecha_creacion;
    private boolean deleted;
}

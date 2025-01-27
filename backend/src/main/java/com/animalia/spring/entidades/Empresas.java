package com.animalia.spring.entidades;

import java.sql.Date;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class Empresas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
	@NotEmpty(message = "El campo no puede estar vacío")
    private String nombre;

    @Column(nullable = false)
    @NotEmpty(message = "El campo no puede estar vacío")
    private String direccion;

    @Column(nullable = false, unique = true)
    @NotEmpty(message = "El campo no puede estar vacío")
    private String telefono;

    @Column(nullable = false, unique = true)
    @NotEmpty(message = "El campo no puede estar vacío")
    private String email;

    @Column(columnDefinition = "ENUM('VETERINARIA', 'REFUGIO', 'HOSPITAL', 'CLINICA', 'PROTECTORA', 'ASOCIACION', 'PERRERA', 'ACUARIO', 'RESIDENCIA', 'CRIADERO', 'OTRO')",nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoEmpresa tipo;

    @Column(nullable = true)
    private String url_web;

    @Column(nullable = false)
    private Date fecha_creacion;

    public enum TipoEmpresa {
        VETERINARIA, REFUGIO, HOSPITAL, CLINICA, PROTECTORA, ASOCIACION, PERRERA, ACUARIO, RESIDENCIA, CRIADERO, OTRO
    }

}

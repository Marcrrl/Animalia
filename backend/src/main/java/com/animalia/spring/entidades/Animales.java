package com.animalia.spring.entidades;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class Animales {
    private long id;
    private String especie;
    private String nombre_comun;
    private String descripcion;
}

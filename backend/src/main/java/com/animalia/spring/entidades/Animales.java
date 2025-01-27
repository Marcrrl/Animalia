package com.animalia.spring.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class Animales {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(nullable = true)
    private String especie;

    @Column(nullable = true)
    private String nombre_comun;

    @Column(nullable = true)
    private String descripcion;

    
    
    /*Estan los tres campos con posibilidad 
    de ser nulos porque peude que el usuario sepa 
    el nombre comun, o el nombre de la especie o simplemente 
    sepa hacer al descripcion de el animal.*/
}

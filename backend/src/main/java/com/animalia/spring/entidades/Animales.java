package com.animalia.spring.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "animales")
public class Animales {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String especie;

    @Column(nullable = true)
    private String nombre_comun;

    @Column(nullable = true)
    private String descripcion;
    
    @Column(nullable = true)
    private String foto;

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private EstadoConservacion estado_conservacion;

    public enum EstadoConservacion {
        EXTINTO, VULNERABLE, BAJO_RIESGO, PELIGRO_EXTINCION, AMENAZADO, SIN_RIESGO, PLAGA, DESCONOCIDO
    }

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private Familia familia;

    public enum Familia {
        MAMIFERO, REPTIL, ANFIBIO, AVES, PECES
    }

    /*
     * Estan los tres campos con posibilidad
     * de ser nulos porque peude que el usuario sepa
     * el nombre comun, o el nombre de la especie o simplemente
     * sepa hacer al descripcion de el animal.
     */
}

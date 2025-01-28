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

    @Column(columnDefinition = "ENUM( 'LEVE', 'MODERADO', 'GRAVE', 'CRITICO', 'FALLECIDO', 'DESCONOCIDO', 'NO_APLICA', 'CAPTURADO', 'LIBERADO', 'EN_PROCESO', 'HOSPITALIZADO', 'EN_ADOPCION', 'ENFERMO', 'SANO' )", nullable = true)
    @Enumerated(EnumType.STRING)
    private EstadoAnimal estado_animal;

    public enum EstadoAnimal {
        LEVE, MODERADO, GRAVE, CRITICO, FALLECIDO, DESCONOCIDO, NO_APLICA, CAPTURADO, LIBERADO, EN_PROCESO,
        HOSPITALIZADO, EN_ADOPCION, ENFERMO, SANO
    }

    /*
     * Estan los tres campos con posibilidad
     * de ser nulos porque peude que el usuario sepa
     * el nombre comun, o el nombre de la especie o simplemente
     * sepa hacer al descripcion de el animal.
     */
}

package com.animalia.spring.entidades;


import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rescates")
public class Rescates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_empresa", referencedColumnName = "id", nullable = false)
    private Empresas empresa;

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id", nullable = false)
    private Usuarios usuario;

    @ManyToOne
    @JoinColumn(name = "id_animal", referencedColumnName = "id", nullable = false)
    private Animales animal;

    @Column(nullable = true)
    private String ubicacion;

    @Column(columnDefinition = "ENUM('NO_RECIBIDO', 'RECIBIDO', 'EN_PROCESO','FINALIZADO', 'CANCELADO', 'NO_APLICA', 'DESCONOCIDO')", nullable = true)
    @Enumerated(EnumType.STRING)
    private Estado estado_rescate;

    public enum Estado {
        NO_RECIBIDO, RECIBIDO, EN_PROCESO, FINALIZADO, CANCELADO, NO_APLICA, DESCONOCIDO
    }

    @Column(nullable = true)
    private LocalDateTime fecha_rescate;
}

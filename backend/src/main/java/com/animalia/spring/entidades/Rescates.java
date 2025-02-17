package com.animalia.spring.entidades;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rescates")
public class Rescates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "id_empresa", referencedColumnName = "id", nullable = false)
    private Empresas empresa;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "id_usuario", referencedColumnName = "id", nullable = false)
    private Usuarios usuario;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "id_animal", referencedColumnName = "id", nullable = false)
    private Animales animal;

    @Column(nullable = true)
    private String ubicacion;

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private Estado estado_rescate;

    public enum Estado {
        NO_RECIBIDO, RECIBIDO, EN_PROCESO, FINALIZADO, CANCELADO, NO_APLICA, DESCONOCIDO
    }

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private EstadoAnimal estado_animal;

    public enum EstadoAnimal {
        LEVE, MODERADO, GRAVE, CRITICO, FALLECIDO, DESCONOCIDO, NO_APLICA, CAPTURADO, LIBERADO, EN_PROCESO,
        HOSPITALIZADO, EN_ADOPCION, ENFERMO, SANO
    }

    @Column(nullable = true)
    private LocalDate fecha_rescate;

    @OneToMany(mappedBy = "rescate")
    @Column(nullable = true)
    @JsonManagedReference
    private List<Fotos> fotos;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rescates rescates = (Rescates) o;
        return id.equals(rescates.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

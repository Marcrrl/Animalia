package com.animalia.spring.entidades;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "empresas")
public class Empresas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Column(columnDefinition = "ENUM('CLINICA', 'REFUGIO', 'HOSPITAL', 'PROTECTORA', 'RESERVA', 'ACUARIO' 'OTRO')", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoEmpresa tipo;

    @Column(nullable = true)
    private String url_web;

    @Column(nullable = false)
    private LocalDate fecha_creacion;

    @OneToMany
    @JoinTable(
        name = "empresa_usuarios",
        joinColumns = @JoinColumn(name = "empresa_id"),
        inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private Set<Usuarios> usuarios = new HashSet<>();

    public enum TipoEmpresa {
        CLINICA, REFUGIO, HOSPITAL, PROTECTORA, RESERVA, ACUARIO, OTRO
    }

}

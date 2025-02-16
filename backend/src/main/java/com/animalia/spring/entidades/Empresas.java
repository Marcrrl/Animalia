package com.animalia.spring.entidades;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
//@AllArgsConstructor
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

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoEmpresa tipo;

    @Column(nullable = true)
    private String url_web;

    @Column(nullable = false)
    private LocalDate fecha_creacion;

    @Column(nullable = false)
    private boolean deleted = false; // Add this line

    @OneToMany(mappedBy = "empresa")
    @JsonManagedReference
    private Set<Usuarios> usuarios = new HashSet<>();

    public enum TipoEmpresa {
        CLINICA, REFUGIO, HOSPITAL, PROTECTORA, RESERVA, ACUARIO, OTRO
    }

    public Empresas(Long id, String nombre, String direccion, String telefono, String email, TipoEmpresa tipo, String url_web, LocalDate fecha_creacion, boolean deleted, Set<Usuarios> usuarios) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.tipo = tipo;
        this.url_web = url_web;
        this.fecha_creacion = fecha_creacion;
        this.deleted = deleted;
        this.usuarios = usuarios;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Empresas empresas = (Empresas) o;
        return Objects.equals(id, empresas.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

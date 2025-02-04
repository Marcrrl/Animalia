package com.animalia.spring.entidades;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuarios")
public class Usuarios implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotEmpty(message = "El campo no puede estar vacío")
    private String nombre;

    @Column(nullable = false)
    @NotEmpty(message = "El campo no puede estar vacío")
    private String apellido;

    // El correo es unico para cada usuario
    @Column(nullable = false, unique = true)
    @NotEmpty(message = "El campo no puede estar vacío")
    private String email;

    @Column(nullable = false)
    @NotEmpty(message = "El campo no puede estar vacío")
    private String password;

    // No puede hacer dos cuentas de usuario con el mismo telefono
    @Column(nullable = false, unique = true)
    @NotEmpty(message = "El campo no puede estar vacío")
    private String telefono;

    @Column(nullable = false)
    @NotEmpty(message = "El campo no puede estar vacío")
    private String direccion;

    // Puede el usuario si quiere ponerse foto de perfil
    @Column(nullable = true)
    private String url_foto_perfil;

    // Los tipos pueden ser ADMIN o USER
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoUsuario tipo_usuario;

    // Hay que poner por codigo la fecha en la que se registra el usuario
    @Column(nullable = false)
    @NotNull
    private LocalDate fecha_registro;

    /*
     * La cantidad de rescates que ha hecho el usuario no
     * es necesario que se ponga para crearlo
     */
    @Column(nullable = true)
    private long cantidad_rescates;

    public enum TipoUsuario {
        ADMIN, USER, EMPRESA
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(tipo_usuario.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

package com.animalia.spring.entidades;

import java.time.LocalDate;

import org.springframework.data.geo.Point;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
@Table(name = "fotos")
public class Fotos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String url_foto;

    @ManyToOne
    @JoinColumn(name = "rescate_id", nullable = false)
    @JsonBackReference

    private Rescates rescate;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "id_usuario", referencedColumnName = "id", nullable = false)
    private Usuarios usuarios;

    @Column(nullable = true)
    private Point ubicacion;

    @Column(nullable = true)
    private String descripcion;

    @Column(nullable = false)
    private LocalDate fecha_captura;

    /*
     * La ubicacion y descripcion pueden ser nulables porque exite la
     * posibilidad de que no se quiera poner una ubicacion o descripcion ya que
     * exiten dos posibilidades que inflingen alguna ley de privacidad o seguridad
     * o tambien puede ser que el usuario se enceuntre en algun sitio remoto
     * donde no se pueda obtener la ubicacion exacta de donde se tomo la foto
     */

    public Fotos(Long id, String url_foto, Rescates rescate, Usuarios usuarios, Point ubicacion, String descripcion, LocalDate fecha_captura) {
        this.id = id;
        this.url_foto = url_foto;
        this.rescate = rescate;
        this.usuarios = usuarios;
        this.ubicacion = ubicacion;
        this.descripcion = descripcion;
        this.fecha_captura = fecha_captura;
    }
}

package com.animalia.spring.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "fotos")
public class Fotos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String nombreArchivo;

    @OneToOne
    @JoinColumn(name = "id_rescate", referencedColumnName = "id", nullable = true)
    private Rescates rescates;

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id", nullable = false)
    private Usuarios usuarios;

    @Column(nullable = true)
    private String ubicacion;

    @Column(nullable = true)
    private String descripcion;

    @Column(nullable = false)
    private String fecha_captura;

    /*
     * La ubicacion y descripcion pueden ser nulables porque exite la
     * posibilidad de que no se quiera poner una ubicacion o descripcion ya que
     * exiten dos posibilidades que inflingen alguna ley de privacidad o seguridad
     * o tambien puede ser que el usuario se enceuntre en algun sitio remoto
     * donde no se pueda obtener la ubicacion exacta de donde se tomo la foto
     */
}

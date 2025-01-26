package com.animalia.spring.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class Fotos {
    private long id;
    private String url_foto;

    @OneToOne
    @JoinColumn(name = "id_rescate", referencedColumnName = "id", nullable = true)
    private Rescates rescate;

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id", nullable = false)
    private Usuarios usuarios;

    @Column(nullable = true)
    private String ubicacion;

    @Column(nullable = true)
    private String descripcion;

    @Column(nullable = false)
    private String fecha_captura;

    /*La ubicacion y descripcion pueden ser nulables porque exite la 
     * posibilidad de que no se quiera poner una ubicacion o descripcion ya que 
     * exiten dos posibilidades que inflingen alguna ley de privacidad o seguridad 
     * o tambien puede ser que el usuario se enceuntre en algun sitio remoto 
     * donde no se pueda obtener la ubicacion exacta de donde se tomo la foto
     */
}

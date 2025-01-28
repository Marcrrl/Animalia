package com.animalia.spring.Excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UsuarioNoEncontrado extends RuntimeException {

    // Identificador de la clase
    private static final long serialVersionUID = 1L;

    public UsuarioNoEncontrado(Long id) {
        super("Usuario no encontrado con id: " + id);
    }
    public UsuarioNoEncontrado() {
        super("No hay usuarios registrados");
    }
}

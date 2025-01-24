package com.animalia.spring.controladores;

import org.springframework.web.bind.annotation.RestController;

import com.animalia.spring.entidades.Animales;
import com.animalia.spring.servicios.AnimalesServicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class WebControlador {
    @Autowired
    private AnimalesServicio animalesServicio;

    @GetMapping("/Animales")
    public ResponseEntity<?> getMethodName() {

        List<Animales> listaAnimales = animalesServicio.obtenerAnimales();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

        if (listaAnimales.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(converter.getObjectMapper().valueToTree(listaAnimales));
        }

    }

}

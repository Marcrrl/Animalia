package com.animalia.spring.entidades.converter;

import org.springframework.stereotype.Component;

import com.animalia.spring.entidades.Rescates;
import com.animalia.spring.entidades.DTO.RescateDetalleDTO;

@Component
public class RescateDetalleDtoConverter {

    public RescateDetalleDTO convertRescateEntityToRescateDetalleDto(Rescates rescate) {
        return new RescateDetalleDTO(
                rescate.getId(),
                rescate.getEmpresa().getNombre(),
                rescate.getUsuario().getNombre(),
                rescate.getAnimal().getNombre_comun(),
                rescate.getUbicacion(),
                rescate.getEstado_rescate(),
                rescate.getEstado_animal(),
                rescate.getFecha_rescate()
        );
    }
}

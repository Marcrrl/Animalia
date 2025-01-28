package com.animalia.spring;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.animalia.spring.entidades.Animales;
import com.animalia.spring.entidades.Usuarios;
import com.animalia.spring.entidades.Animales.EstadoConservacion;
import com.animalia.spring.repositorio.AnimalesRepositorio;
import com.animalia.spring.entidades.Animales.Familia;
import com.animalia.spring.repositorio.UsuarioRepositorio;

@SpringBootApplication
public class AnimaliaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnimaliaApplication.class, args);
	}

	@Bean
	CommandLineRunner initData(AnimalesRepositorio animalesRepositorio, UsuarioRepositorio usuariosRepositorio  ) {
		return (args) -> {
			if (animalesRepositorio.count() == 0 && usuariosRepositorio.count() == 0) {

				List<Animales> animales = Arrays.asList(
						new Animales(null, "Canis lupus familiaris", "Perro",
								"Animal domesticado y compañero del ser humano", EstadoConservacion.SIN_RIESGO,Familia.ANFIBIO),
						new Animales(null, "Felis catus", "Gato", "Animal doméstico, conocido por su agilidad",
								EstadoConservacion.SIN_RIESGO, Animales.Familia.MAMIFERO),
						new Animales(null, "Equus ferus caballus", "Caballo",
								"Animal de granja, usado en transporte y trabajo", EstadoConservacion.DESCONOCIDO,Familia.AVES),
						new Animales(null, "Panthera leo", "León", "Gran felino conocido como el rey de la selva",
								EstadoConservacion.EXTINTO, Animales.Familia.REPTIL),
						new Animales(null, "Ailuropoda melanoleuca", "Panda",
								"Oso de China, famoso por su color blanco y negro", EstadoConservacion.AMENAZADO,Familia.PECES));

				animales.forEach(animal -> {
					animalesRepositorio.save(animal);
				});

				List<Usuarios> usuarios = Arrays.asList(
				new Usuarios(null, "Juan", "Pérez", "juan.perez@example.com", "123", "123456789", "Calle Falsa 123",  null, Usuarios.TipoUsuario.USER, LocalDate.now(), 0),
				new Usuarios(null,"Ana", "García", "ana.garcia@example.com", "123", "987654321", "Avenida Siempre Viva 456",  "", Usuarios.TipoUsuario.ADMIN, LocalDate.now(), 10),
				new Usuarios(null,"Carlos", "Hernández", "carlos.hernandez@example.com", "123", "1122334455", "Calle Luna 789",  null, Usuarios.TipoUsuario.USER, LocalDate.now(), 5),
				new Usuarios(null,"Lucía", "Martínez", "lucia.martinez@example.com", "123", "5566778899", "Avenida Sol 321", "", Usuarios.TipoUsuario.USER,LocalDate.now(), 8),
				new Usuarios(null,"Pedro", "Gómez", "pedro.gomez@example.com", "123", "9988776655", "Calle Estrella 654",  null, Usuarios.TipoUsuario.ADMIN, LocalDate.now(), 15)
				);

				usuarios.forEach(usuario -> {
					usuariosRepositorio.save(usuario);
				});
			}
		
		};
	}
}

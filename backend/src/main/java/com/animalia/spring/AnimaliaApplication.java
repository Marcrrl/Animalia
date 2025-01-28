package com.animalia.spring;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.animalia.spring.entidades.Animales;
import com.animalia.spring.entidades.Animales.EstadoAnimal;
import com.animalia.spring.repositorio.AnimalesRepositorio;

@SpringBootApplication
public class AnimaliaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnimaliaApplication.class, args);
	}

	@Bean
	CommandLineRunner initData(AnimalesRepositorio animalesRepositorio) {
		return (args) -> {
			if (animalesRepositorio.count() == 0) {
				List<Animales> animales = Arrays.asList(
						new Animales(null, "Canis lupus familiaris", "Perro",
								"Animal domesticado y compañero del ser humano", EstadoAnimal.EN_PROCESO),
						new Animales(null, "Felis catus", "Gato", "Animal doméstico, conocido por su agilidad",
								EstadoAnimal.HOSPITALIZADO),
						new Animales(null, "Equus ferus caballus", "Caballo",
								"Animal de granja, usado en transporte y trabajo", EstadoAnimal.SANO),
						new Animales(null, "Panthera leo", "León", "Gran felino conocido como el rey de la selva",
								EstadoAnimal.FALLECIDO),
						new Animales(null, "Ailuropoda melanoleuca", "Panda",
								"Oso de China, famoso por su color blanco y negro", EstadoAnimal.CAPTURADO));

				animales.forEach(animal -> {
					animalesRepositorio.save(animal);
				});
			}

		};
	}
}

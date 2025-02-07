package com.animalia.spring;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.animalia.spring.entidades.Animales;
import com.animalia.spring.entidades.Empresas;
import com.animalia.spring.entidades.Usuarios;
import com.animalia.spring.entidades.Animales.EstadoConservacion;
import com.animalia.spring.entidades.Animales.Familia;
import com.animalia.spring.entidades.Empresas.TipoEmpresa;
import com.animalia.spring.repositorio.AnimalesRepositorio;
import com.animalia.spring.repositorio.EmpresasRepositorio;
import com.animalia.spring.repositorio.UsuarioRepositorio;

@SpringBootApplication
public class AnimaliaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnimaliaApplication.class, args);
	}

	@Bean
	CommandLineRunner initData(AnimalesRepositorio animalesRepositorio, UsuarioRepositorio usuariosRepositorio,
			EmpresasRepositorio empresasRepositorio,
			PasswordEncoder passwordEncoder) {
		return (args) -> {
			if (animalesRepositorio.count() == 0 && usuariosRepositorio.count() == 0) {

				List<Animales> animales = Arrays.asList(
						new Animales(null, "Canis lupus familiaris", "Perro",
								"Animal domesticado y compañero del ser humano", "perro.jpg",
								EstadoConservacion.SIN_RIESGO, Familia.ANFIBIO),
						new Animales(null, "Felis catus", "Gato", "Animal doméstico, conocido por su agilidad",
								"gato.jpg",
								EstadoConservacion.SIN_RIESGO, Animales.Familia.MAMIFERO),
						new Animales(null, "Equus ferus caballus", "Juan",
								"Animal de granja atrapado en una",
								"caballo.webp", EstadoConservacion.DESCONOCIDO,
								Familia.AVES),
						new Animales(null, "Panthera leo", "León", "Gran felino conocido como el rey de la selva",
								"leon.jpg",
								EstadoConservacion.EXTINTO, Animales.Familia.REPTIL),
						new Animales(null, "Ailuropoda melanoleuca", "Panda",
								" Oso de China, famoso por su color blanco y negro",
								"panda.jpg", EstadoConservacion.AMENAZADO, Familia.PECES));

				animales.forEach(animal -> {
					animalesRepositorio.save(animal);
				});

				Empresas e1 = new Empresas(null, "Clínica veterinaria", "Calle de la Clínica 1", "12345678",
                    "clinica@example.com", TipoEmpresa.CLINICA, "url_empresa_1.es",
                    LocalDate.of(1999, 12, 6), new HashSet<>());

            Empresas e2 = new Empresas(null, "Acuario", "Calle del Acuario 2", "87654321",
                    "acuario@example.com", TipoEmpresa.ACUARIO, "url_empresa_2.es",
                    LocalDate.of(1999, 12, 6), new HashSet<>());

            Empresas e3 = new Empresas(null, "Hospital veterinario", "Calle Hospital 3", "23456789",
                    "hospital@example.com", TipoEmpresa.HOSPITAL, "url_empresa_3.es",
                    LocalDate.of(1999, 12, 6), new HashSet<>());

            Empresas e4 = new Empresas(null, "Protectora Animales", "Avenida Protectora", "34567890",
                    "protectora@example.com", TipoEmpresa.PROTECTORA, "url_empresa_4.es",
                    LocalDate.of(1999, 12, 6), new HashSet<>());

            Empresas e5 = new Empresas(null, "Refugio Don Fabro", "Calle fabro234", "45678901",
                    "fabro234@example.com", TipoEmpresa.REFUGIO,
                    "https://www.youtube.com/channel/UCe_vi8ZY603vDSYEVMayV0A",
                    LocalDate.of(1999, 12, 6), new HashSet<>());

            Empresas e6 = new Empresas(null, "Reserva de Animales", "Calle de la Clínica 1", "56789012",
                    "reserva@example.com", TipoEmpresa.RESERVA, "url_empresa_6.es",
                    LocalDate.of(1999, 12, 6), new HashSet<>());

            // Lista de empresas
            List<Empresas> empresas = Arrays.asList(e1, e2, e3, e4, e5, e6);

            // Creación de usuarios
            Usuarios u1 = new Usuarios(null, "Juan", "Pérez", "juan.perez@example.com",
                    passwordEncoder.encode("123"), "123456789", "Calle Falsa 123",
                    "bardockNegro+.jpg", Usuarios.TipoUsuario.USER, LocalDate.now(), 0,null);

            Usuarios u2 = new Usuarios(null, "Ana", "García", "ana.garcia@example.com",
                    passwordEncoder.encode("123"), "987654321", "Avenida Siempre Viva 456",
                    "", Usuarios.TipoUsuario.ADMIN, LocalDate.now(), 10,null);

            Usuarios u3 = new Usuarios(null, "Carlos", "Hernández", "carlos.hernandez@example.com",
                    passwordEncoder.encode("123"), "1122334455", "Calle Luna 789",
                    "", Usuarios.TipoUsuario.EMPRESA, LocalDate.now(), 5,e5);

            Usuarios u4 = new Usuarios(null, "Lucía", "Martínez", "lucia.martinez@example.com",
                    passwordEncoder.encode("123"), "5566778899", "Avenida Sol 321",
                    "", Usuarios.TipoUsuario.USER, LocalDate.now(), 8,null);

            Usuarios u5 = new Usuarios(null, "Pedro", "Gómez", "pedro.gomez@example.com",
                    passwordEncoder.encode("123"), "9988776655", "Calle Estrella 654",
                    null, Usuarios.TipoUsuario.ADMIN, LocalDate.now(), 15,null);

            // Lista de usuarios
            List<Usuarios> usuarios = Arrays.asList(u1, u2, u3, u4, u5);
			
			e5.getUsuarios().add(u3);

            // Guardar empresas y usuarios
            empresasRepositorio.saveAll(empresas);
            usuariosRepositorio.saveAll(usuarios);

			}

		};
	}
}

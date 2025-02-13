package com.animalia.spring;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.animalia.spring.entidades.Animales;
import com.animalia.spring.entidades.Empresas;
import com.animalia.spring.entidades.Fotos;
import com.animalia.spring.entidades.Rescates;
import com.animalia.spring.entidades.Usuarios;
import com.animalia.spring.entidades.Animales.EstadoConservacion;
import com.animalia.spring.entidades.Animales.Familia;
import com.animalia.spring.entidades.Empresas.TipoEmpresa;
import com.animalia.spring.repositorio.AnimalesRepositorio;
import com.animalia.spring.repositorio.EmpresasRepositorio;
import com.animalia.spring.repositorio.UsuarioRepositorio;
import com.animalia.spring.repositorio.RescatesRepositorio;
import com.animalia.spring.repositorio.FotosRepositorio;

@SpringBootApplication
public class AnimaliaApplication {

        public static void main(String[] args) {
                SpringApplication.run(AnimaliaApplication.class, args);
        }

        @Bean
        CommandLineRunner initData(AnimalesRepositorio animalesRepositorio, UsuarioRepositorio usuariosRepositorio,
                        EmpresasRepositorio empresasRepositorio, RescatesRepositorio rescatesRepositorio,
                        FotosRepositorio fotosRepositorio, PasswordEncoder passwordEncoder) {
                return (args) -> {
                        if (animalesRepositorio.count() == 0 && usuariosRepositorio.count() == 0
                                        && empresasRepositorio.count() == 0 && rescatesRepositorio.count() == 0) {
                                Animales a1 = new Animales(null, "Canis lupus familiaris", "Perro",
                                                "Animal domesticado y compañero del ser humano",
                                                "perro.jpg",
                                                EstadoConservacion.SIN_RIESGO, Familia.ANFIBIO);
                                Animales a2 = new Animales(null, "Felis catus", "Gato",
                                                "Animal doméstico, conocido por su agilidad",
                                                "gato.jpg",
                                                EstadoConservacion.SIN_RIESGO,
                                                Animales.Familia.MAMIFERO);
                                Animales a3 = new Animales(null, "Equus ferus caballus", "Juan",
                                                "Animal de granja atrapado en una terraza",
                                                "caballo.webp", EstadoConservacion.DESCONOCIDO,
                                                Familia.AVES);
                                Animales a4 = new Animales(null, "Panthera leo", "León",
                                                "Gran felino conocido como el rey de la selva",
                                                "leon.jpg",
                                                EstadoConservacion.EXTINTO, Animales.Familia.REPTIL);
                                Animales a5 = new Animales(null, "Ailuropoda melanoleuca", "Panda",
                                                " Oso de China, famoso por su color blanco y negro",
                                                "panda.jpg", EstadoConservacion.AMENAZADO,
                                                Familia.PECES);
                                List<Animales> animales = Arrays.asList(a1, a2, a3, a4, a5);
                                animales.forEach(animal -> {
                                        animalesRepositorio.save(animal);
                                });

                                Empresas e1 = new Empresas(null, "Clínica veterinaria",
                                                "C. Venezuela, 12, 03010 Alicante (Alacant), Alicante",
                                                "12345678",
                                                "clinica@example.com", TipoEmpresa.CLINICA, "url_empresa_1.es",
                                                LocalDate.of(1999, 12, 6), new HashSet<>());
                                Empresas e2 = new Empresas(null, "Acuario",
                                                "Gran Vía, 1, 03010 Alicante (Alacant), Alicante", "87654321",
                                                "acuario@example.com", TipoEmpresa.ACUARIO, "url_empresa_2.es",
                                                LocalDate.of(1999, 12, 6), new HashSet<>());

                                Empresas e3 = new Empresas(null, "Hospital veterinario",
                                                "Av. Pintor Baeza, 12, 03010 Alicante (Alacant), Alicante", "23456789",
                                                "hospital@example.com", TipoEmpresa.HOSPITAL, "url_empresa_3.es",
                                                LocalDate.of(1999, 12, 6), new HashSet<>());

                                Empresas e4 = new Empresas(null, "Protectora Animales",
                                                "C. Pablo Neruda, 03011 Alicante (Alacant), Alicante",
                                                "34567890",
                                                "protectora@example.com", TipoEmpresa.PROTECTORA, "url_empresa_4.es",
                                                LocalDate.of(1999, 12, 6), new HashSet<>());

                                Empresas e5 = new Empresas(null, "Refugio Don Fabro",
                                                "C. Escritor Dámaso Alonso, 03011 Alicante (Alacant), Alicante",
                                                "45678901",
                                                "fabro234@example.com", TipoEmpresa.REFUGIO,
                                                "https://www.youtube.com/channel/UCe_vi8ZY603vDSYEVMayV0A",
                                                LocalDate.of(1999, 12, 6), new HashSet<>());

                                Empresas e6 = new Empresas(null, "Reserva de Animales", "C. Vicente Alexandre",
                                                "56789012",
                                                "reserva@example.com", TipoEmpresa.RESERVA, "url_empresa_6.es",
                                                LocalDate.of(1999, 12, 6), new HashSet<>());

                                // Lista de empresas
                                List<Empresas> empresas = Arrays.asList(e1, e2, e3, e4, e5, e6);

                                // Creación de usuarios
                                Usuarios u1 = new Usuarios(null, "Juan", "Pérez", "juan.perez@example.com",
                                                passwordEncoder.encode("123"), "123456789", "Calle Falsa 123",
                                                "bardockNegro+.jpg", Usuarios.TipoUsuario.USER, LocalDate.now(), 0,
                                                null);

                                Usuarios u2 = new Usuarios(null, "Ana", "García", "ana.garcia@example.com",
                                                passwordEncoder.encode("123"), "987654321", "Avenida Siempre Viva 456",
                                                "iconoBase.png", Usuarios.TipoUsuario.ADMIN, LocalDate.now(), 10, null);

                                Usuarios u3 = new Usuarios(null, "Carlos", "Hernández", "carlos.hernandez@example.com",
                                                passwordEncoder.encode("123"), "1122334455", "Calle Luna 789",
                                                "iconoBase.png", Usuarios.TipoUsuario.EMPRESA, LocalDate.now(), 5, e5);

                                Usuarios u4 = new Usuarios(null, "Lucía", "Martínez", "lucia.martinez@example.com",
                                                passwordEncoder.encode("123"), "5566778899", "Avenida Sol 321",
                                                "iconoBase.png", Usuarios.TipoUsuario.USER, LocalDate.now(), 8, null);

                                Usuarios u5 = new Usuarios(null, "Pedro", "Gómez", "pedro.gomez@example.com",
                                                passwordEncoder.encode("123"), "9988776655", "Calle Estrella 654",
                                                "iconoBase.png", Usuarios.TipoUsuario.ADMIN, LocalDate.now(), 15, null);

                                Usuarios u6 = new Usuarios(null, "Usuario", "6", "u6@example.com",
                                                passwordEncoder.encode("123"), "telefono6", "Calle Estrella 654",
                                                "iconoBase.png", Usuarios.TipoUsuario.ADMIN, LocalDate.now(), 15, null);

                                Usuarios u7 = new Usuarios(null, "Usuaio", "7", "u7@example.com",
                                                passwordEncoder.encode("123"), "telefono7", "Calle Estrella 654",
                                                "iconoBase.png", Usuarios.TipoUsuario.ADMIN, LocalDate.now(), 15, null);
                                // Lista de usuarios
                                List<Usuarios> usuarios = Arrays.asList(u1, u2, u3, u4, u5, u6, u7);

                                e5.getUsuarios().add(u3);

                                // Guardar empresas y usuarios
                                empresasRepositorio.saveAll(empresas);
                                usuariosRepositorio.saveAll(usuarios);

                                Rescates r1 = new Rescates(null, e5, u3, a2, "Calle Falsa 123",
                                                Rescates.Estado.CANCELADO, Rescates.EstadoAnimal.LIBERADO,
                                                LocalDate.now(), null);

                                Rescates r2 = new Rescates(null, e1, u1, a1, "Calle Verdadera 456",
                                                Rescates.Estado.EN_PROCESO, Rescates.EstadoAnimal.ENFERMO,
                                                LocalDate.now(), null);

                                Rescates r3 = new Rescates(null, e2, u2, a3, "Avenida Siempre Viva 789",
                                                Rescates.Estado.FINALIZADO, Rescates.EstadoAnimal.SANO,
                                                LocalDate.now(), null);

                                Rescates r4 = new Rescates(null, e3, u4, a4, "Calle Luna 321",
                                                Rescates.Estado.RECIBIDO, Rescates.EstadoAnimal.GRAVE,
                                                LocalDate.now(), null);

                                Rescates r5 = new Rescates(null, e4, u5, a5, "Avenida Sol 654",
                                                Rescates.Estado.NO_RECIBIDO, Rescates.EstadoAnimal.CAPTURADO,
                                                LocalDate.now(), null);

                                Rescates r6 = new Rescates(null, e6, u6, a1, "Calle Estrella 987",
                                                Rescates.Estado.NO_APLICA, Rescates.EstadoAnimal.DESCONOCIDO,
                                                LocalDate.now(), null);

                                // Guardar rescates
                                List<Rescates> rescates = Arrays.asList(r1, r2, r3, r4, r5, r6);
                                rescatesRepositorio.saveAll(rescates);

                                Fotos f1 = new Fotos(null, "perro.jpg", r1, u3, "Calle Falsa 123",
                                                "Perro en la calle", LocalDate.now());
                                Fotos f2 = new Fotos(null, "gato.jpg", r2, u1, "Calle Verdadera 456",
                                                "Gato en la calle", LocalDate.now());
                                Fotos f3 = new Fotos(null, "juan.jpg", r1, u2, "Avenida Siempre Viva 789",
                                                "Caballo en la calle", LocalDate.now());
                                Fotos f4 = new Fotos(null, "leon1.jpg", r4, u4, "Calle Luna 321",
                                                "León en la calle", LocalDate.now());
                                Fotos f5 = new Fotos(null, "panda.jpg", r5, u5, "Avenida Sol 654",
                                                "Panda en la calle", LocalDate.now());
                                Fotos f6 = new Fotos(null, "leon.jpg", r1, u6, "Calle Estrella 987",
                                                "Perro en la calle", LocalDate.now());

                                // Guardar fotos
                                List<Fotos> fotos = Arrays.asList(f1, f2, f3, f4, f5, f6);
                                fotosRepositorio.saveAll(fotos);

                                // Enlazar fotos con rescates
                                r1.setFotos(Arrays.asList(f1,f3,f6));
                                r2.setFotos(Arrays.asList(f2));
                                r3.setFotos(Arrays.asList(f3));
                                r4.setFotos(Arrays.asList(f4));
                                r5.setFotos(Arrays.asList(f5));
                                r6.setFotos(Arrays.asList(f6));

                                // Guardar rescates con fotos
                                rescatesRepositorio.saveAll(Arrays.asList(r1, r2, r3, r4, r5, r6));
                        }

                };
        }
}

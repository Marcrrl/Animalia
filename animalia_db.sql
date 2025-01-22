-- Crear la base de datos y las tablas como ya lo has hecho.
DROP DATABASE IF EXISTS intermodular;
CREATE DATABASE intermodular;
USE intermodular;

SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS Rescates;
DROP TABLE IF EXISTS Fotos;
DROP TABLE IF EXISTS Animales;
DROP TABLE IF EXISTS Empresas;
DROP TABLE IF EXISTS Usuarios;
SET FOREIGN_KEY_CHECKS = 1;

-- Tabla de Usuarios
CREATE TABLE Usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    contraseña VARCHAR(255) NOT NULL,
    cantidad_rescates INT DEFAULT 0 
);

-- Tabla de Animales
CREATE TABLE Animales (
    id_animal INT AUTO_INCREMENT PRIMARY KEY,
    especie VARCHAR(100) NOT NULL,
    nombre_comun VARCHAR(100),
    descripcion TEXT,
    ultima_ubicacion TEXT,
    ultima_fecha_visto DATE,
    estado_conservacion VARCHAR(50),
    UNIQUE (especie, nombre_comun, descripcion)
);

-- Tabla de Empresas
CREATE TABLE Empresas (
    id_empresa INT AUTO_INCREMENT PRIMARY KEY,
    nombre_empresa VARCHAR(150) NOT NULL,
    email_contacto VARCHAR(150) UNIQUE NOT NULL,
    telefono VARCHAR(15),
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de Rescates
CREATE TABLE Rescates (
    id_rescate INT AUTO_INCREMENT PRIMARY KEY,
    id_empresa INT NOT NULL,
    id_animal INT NOT NULL,
    fecha_rescate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado TEXT,
    FOREIGN KEY (id_empresa) REFERENCES Empresas(id_empresa) ON DELETE CASCADE,
    FOREIGN KEY (id_animal) REFERENCES Animales(id_animal) ON DELETE CASCADE
);

-- Tabla de Fotos
CREATE TABLE Fotos (
    id_foto INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_animal INT,
    id_rescate INT,
    url_foto TEXT NOT NULL,
    ubicacion TEXT NOT NULL,
    fecha_captura DATE NOT NULL,
    descripcion TEXT,
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_animal) REFERENCES Animales(id_animal) ON DELETE SET NULL,
    FOREIGN KEY (id_rescate) REFERENCES Rescates(id_rescate) ON DELETE CASCADE
);

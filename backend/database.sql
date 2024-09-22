CREATE DATABASE IF NOT EXISTS TodasBrillamos;

USE TodasBrillamos;

CREATE TABLE usuario (
    correo_electronico VARCHAR(255) PRIMARY KEY,
    nombre VARCHAR(30),
    apellido VARCHAR(30),
    telefono VARCHAR(15),
    contrasena VARCHAR(255) NOT NULL
);

CREATE TABLE direccion_envio_cliente (
    correo_electronico VARCHAR(255),
    direccion_envio VARCHAR(255),
    PRIMARY KEY (correo_electronico, direccion_envio),
    FOREIGN KEY (correo_electronico) REFERENCES usuario(correo_electronico)
);

CREATE TABLE catalogo (
    id_catalogo INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255),
    descripcion VARCHAR(255)
);

CREATE TABLE producto (
    id_catalogo INT,
    id_producto INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255),
    descripcion VARCHAR(255),
    precio DECIMAL(10,2),
    categoria VARCHAR(255),
    cantidad INT,
    FOREIGN KEY (id_catalogo) REFERENCES catalogo(id_catalogo)
);

CREATE TABLE compra (
    correo_electronico VARCHAR(255),
    id_producto INT,
    fecha_compra DATETIME,
    monto DECIMAL(10,2),
    numero_transaccion VARCHAR(255),
    cantidad INT,
    PRIMARY KEY (correo_electronico, id_producto, fecha_compra, numero_transaccion),
    FOREIGN KEY (correo_electronico) REFERENCES usuario(correo_electronico),
    FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
);

CREATE TABLE usuario_admin (
    id_admin INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(30),
    apellido VARCHAR(30),
    contrasena VARCHAR(255) NOT NULL
);

CREATE TABLE administra (
    id_admin INT,
    id_producto INT,
    PRIMARY KEY (id_admin, id_producto),
    FOREIGN KEY (id_admin) REFERENCES usuario_admin(id_admin),
    FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
);

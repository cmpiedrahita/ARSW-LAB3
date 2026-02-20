-- Script de inicialización para PostgreSQL
-- Ejecutar este script para crear la base de datos

CREATE DATABASE blueprintsdb;

-- Conectarse a la base de datos blueprintsdb y ejecutar:

-- Las tablas se crean automáticamente con JPA (spring.jpa.hibernate.ddl-auto=update)
-- pero aquí está el esquema de referencia:

-- CREATE TABLE blueprints (
--     id BIGSERIAL PRIMARY KEY,
--     author VARCHAR(255) NOT NULL,
--     name VARCHAR(255) NOT NULL,
--     UNIQUE(author, name)
-- );

-- CREATE TABLE blueprint_points (
--     blueprint_id BIGINT NOT NULL,
--     x INTEGER NOT NULL,
--     y INTEGER NOT NULL,
--     FOREIGN KEY (blueprint_id) REFERENCES blueprints(id) ON DELETE CASCADE
-- );

-- Datos de ejemplo (opcional)
-- INSERT INTO blueprints (author, name) VALUES ('john', 'house');
-- INSERT INTO blueprint_points (blueprint_id, x, y) VALUES (1, 0, 0), (1, 10, 0), (1, 10, 10), (1, 0, 10);

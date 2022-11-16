CREATE TABLE  IF NOT EXISTS Pacientes
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(80) NOT NULL,
    apellido VARCHAR(80) NOT NULL,
    domicilio VARCHAR(255) NOT NULL,
    dni VARCHAR(15) NOT NULL unique,
    FechaAlta Date
);
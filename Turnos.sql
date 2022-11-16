CREATE TABLE  IF NOT EXISTS Turnos
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    id_Odontologo int ,
    id_Paciente int ,
    fecha DATE  ,
    FOREIGN KEY (id_Odontologo) REFERENCES Odontologos(id),
    FOREIGN KEY (id_Paciente) REFERENCES Pacientes(id)

);
package com.example.ProyectoIntegrador.entidades;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class Turnos {

    private int id;
    private int id_Odontologo;
    private int id_Paciente;
    private LocalDate fecha;
}

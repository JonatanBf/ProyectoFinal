package com.example.ProyectoIntegrador.entidades;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class Paciente {

    private int id;
    private String nombre;
    private String apellido;
    private String domicilio;
    private String dni;
    private LocalDate fechaAlta;
}

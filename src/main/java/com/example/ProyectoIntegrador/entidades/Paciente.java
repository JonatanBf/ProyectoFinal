package com.example.ProyectoIntegrador.entidades;

import java.time.LocalDate;


public record Paciente(int id, String nombre, String apellido, String domicilio, String dni, LocalDate fechaAlta) {
}

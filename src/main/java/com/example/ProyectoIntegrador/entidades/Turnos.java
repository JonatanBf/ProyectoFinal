package com.example.ProyectoIntegrador.entidades;

import java.time.LocalDate;

public record Turnos(int id, int id_Odontologo, int id_Paciente, LocalDate fecha) {
}

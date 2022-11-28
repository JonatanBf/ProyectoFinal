package com.example.ProyectoIntegrador.entidades;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TurnoDto {

    private Long id_paciente;
    private Long id_odontologo;
    private LocalDate fecha;

}

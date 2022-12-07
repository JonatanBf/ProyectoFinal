package com.example.ProyectoIntegrador.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class TurnoDto {

    private Long id_paciente;
    private Long id_odontologo;
    private LocalDateTime fecha;

}

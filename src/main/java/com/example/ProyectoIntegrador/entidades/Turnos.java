package com.example.ProyectoIntegrador.entidades;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter

@Entity
@Table(name = "Turno")
public class Turnos {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id_Turno")
    private Long id;

    @Column(name="id_Odontologo")
    private Long id_Odontologo;

    @Column(name="id_Paciente")
    private Long id_Paciente;

    @Column(name="Fecha")
    private LocalDate fecha;
}

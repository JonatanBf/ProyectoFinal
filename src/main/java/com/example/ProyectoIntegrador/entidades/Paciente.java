package com.example.ProyectoIntegrador.entidades;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;


@Getter
@Setter

@Entity
@Table(name = "Paciente")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id_Paciente")
    private Long id;

    @Column(name="Nombre")
    private String nombre;

    @Column(name="Apellido")
    private String apellido;

    @Column(name="Domicilio")
    private String domicilio;

    @Column(name="DNI")
    private String dni;

    @Column(name="FechaAlta")
    private LocalDate fechaAlta;
}

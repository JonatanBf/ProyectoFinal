package com.example.ProyectoIntegrador.entidades;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter

@Entity
@Table(name = "Odontologo")
public class Odontologo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id_Odontologo")
    private Long id;

    @Column(name="Nombre")
    private String nombre;

    @Column(name="Apellido")
    private String apellido;

    @Column(name="Matricula")
    private String matricula;

}

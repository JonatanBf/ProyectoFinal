package com.example.ProyectoIntegrador.entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;

import java.util.Set;

@Getter
@Setter

@Entity
@Table(name = "paciente")
public class Paciente  {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String nombre;
    private String apellido;
    private String domicilio;

    @Column(unique = true)
    private String dni;

    private LocalDate fechaAlta;


    @OneToMany(mappedBy = "paciente", fetch = FetchType.EAGER)
    private Set<Turno> turnos = new HashSet<>();
}

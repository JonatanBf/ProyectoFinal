package com.example.ProyectoIntegrador.entidades;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Entity
@Table(name = "odontologo")
public class Odontologo  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;

    @Column(unique = true)
    private String matricula;

    @OneToMany(mappedBy = "odontologo", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Turno> turnos = new HashSet<>();

}

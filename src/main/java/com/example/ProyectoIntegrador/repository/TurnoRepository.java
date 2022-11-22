package com.example.ProyectoIntegrador.repository;

import com.example.ProyectoIntegrador.entidades.Paciente;
import com.example.ProyectoIntegrador.entidades.Turnos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TurnoRepository extends JpaRepository<Turnos, Long> {
}

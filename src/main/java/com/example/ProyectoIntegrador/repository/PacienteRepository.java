package com.example.ProyectoIntegrador.repository;

import com.example.ProyectoIntegrador.entidades.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
}

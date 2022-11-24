package com.example.ProyectoIntegrador.repository;

import com.example.ProyectoIntegrador.entidades.Odontologo;
import com.example.ProyectoIntegrador.entidades.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    @Query("select o FROM Paciente o where o.dni = ?1")
    public List<Paciente> findByDni(String dni);

}

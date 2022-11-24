package com.example.ProyectoIntegrador.repository;

import com.example.ProyectoIntegrador.entidades.Odontologo;
import com.example.ProyectoIntegrador.entidades.Turnos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OdontologoRepository extends JpaRepository<Odontologo, Long> {

    @Query("select o FROM Odontologo o where o.matricula = ?1")
    public List<Odontologo> findByMatricula(String matricula);
}

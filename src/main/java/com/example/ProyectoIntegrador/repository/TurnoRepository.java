package com.example.ProyectoIntegrador.repository;

import com.example.ProyectoIntegrador.entidades.Paciente;
import com.example.ProyectoIntegrador.entidades.Turnos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TurnoRepository extends JpaRepository<Turnos, Long> {

    @Query("select t FROM Turnos t where t.id_Odontologo = ?1")
    public List<Turnos> findByOdontologo(Long idOdontologo);

    @Query("select t from Turnos t where t.id_Paciente = ?1")
    public List<Turnos>  findByPaciente(Long idPaciente);


    @Modifying
    @Transactional
    @Query("DELETE Turnos t where t.id_Odontologo = ?1")
    int deleteByOdontologo(Long idOdontologo);

    @Modifying
    @Transactional
    @Query("DELETE Turnos t where t.id_Paciente = ?1")
    int deleteByPaciente(Long idPaciente);
}


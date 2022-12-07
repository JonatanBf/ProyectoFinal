package com.example.ProyectoIntegrador.repository;

import com.example.ProyectoIntegrador.entidades.Turno;
import com.example.ProyectoIntegrador.dto.TurnoDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public interface TurnoRepository extends JpaRepository<Turno, Long> {

    @Query("select t FROM Turno t where t.odontologo.id = ?1")
    public List<Turno> findByOdontologo(Long idOdontologo);

    @Query("select t from Turno t where t.paciente.id= ?1")
    public List<Turno>  findByPaciente(Long idPaciente);


    @Modifying
    @Transactional
    @Query("DELETE Turno t where t.odontologo.id = ?1")
    int deleteByOdontologo(Long idOdontologo);

    @Modifying
    @Transactional
    @Query("DELETE from Turno")
    void deleteAll();

    @Modifying
    @Transactional
    @Query("DELETE Turno t where t.paciente.id= ?1")
    int deleteByPaciente(Long idPaciente);

    @Modifying
    @Transactional
    @Query("update Turno t set t.paciente.id = ?4, t.odontologo= ?3, t.fecha = ?2 where t.id= ?1")
    Turno updateAll(TurnoDto turnoDto, Long idTurno);

}


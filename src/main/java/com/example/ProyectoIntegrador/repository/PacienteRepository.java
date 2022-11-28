package com.example.ProyectoIntegrador.repository;

import com.example.ProyectoIntegrador.entidades.Odontologo;
import com.example.ProyectoIntegrador.entidades.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    @Query("select o FROM Paciente o where o.dni= ?1")
    public Paciente findByDni(String dni);

    @Modifying
    @Transactional
    @Query("update Paciente p set p.fechaAlta=?6, p.domicilio=?5, p.dni=?4 ,p.apellido=?3 ,p.nombre=?2 where p.id= ?1")
    void updateAll(Paciente paciente, Long idPaciente);
}

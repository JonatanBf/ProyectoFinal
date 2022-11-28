package com.example.ProyectoIntegrador.repository;

import com.example.ProyectoIntegrador.entidades.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OdontologoRepository extends JpaRepository<Odontologo, Long> {

    @Query("select o FROM Odontologo o where o.matricula= ?1")
    public Odontologo findByMatricula(String matricula);

    @Modifying
    @Transactional
    @Query("DELETE from Odontologo")
    void deleteAll();

    @Modifying
    @Transactional
    @Query("update Odontologo o set o.matricula=?4, o.apellido=?3, o.nombre=?2 where o.id= ?1")
    void updateAll(Odontologo odontologo, Long idOdontologo);
}

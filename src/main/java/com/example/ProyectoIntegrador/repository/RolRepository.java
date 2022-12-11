package com.example.ProyectoIntegrador.repository;

import com.example.ProyectoIntegrador.entidades.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {

    Rol findByNombre(String nombre);
}

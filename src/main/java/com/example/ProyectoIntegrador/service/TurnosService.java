package com.example.ProyectoIntegrador.service;

import com.example.ProyectoIntegrador.entidades.Paciente;
import com.example.ProyectoIntegrador.entidades.Turnos;
import com.example.ProyectoIntegrador.repository.TurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TurnosService {

    @Autowired
    TurnoRepository turnoRepository;


    public void agregar(Turnos t){
        turnoRepository.save(t);
    }

    public Optional<List<Turnos>> listar() {
        return Optional.of(turnoRepository.findAll());
    }


    public void modificar(Turnos t, Long id) {
        Optional<Turnos> turno = turnoRepository.findById(id);
        Turnos turnoNew = turno.get();
        turnoNew.setId_Odontologo(t.getId_Odontologo());
        turnoNew.setId_Paciente(t.getId_Paciente());
        turnoNew.setFecha(t.getFecha());
        turnoRepository.save(turnoNew);
    }


    public void eliminar(Long  id) {
        turnoRepository.deleteById(id);

    }

    public Optional<Turnos> buscarPorId(Long id) {
        return turnoRepository.findById(id);
    }
}

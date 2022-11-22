package com.example.ProyectoIntegrador.service;

import com.example.ProyectoIntegrador.entidades.Odontologo;
import com.example.ProyectoIntegrador.entidades.Paciente;
import com.example.ProyectoIntegrador.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {
    @Autowired
    PacienteRepository pacienteRepository;


    public void agregar(Paciente p){
        pacienteRepository.save(p);
    }

    public Optional<List<Paciente>> listar() {
        return Optional.of(pacienteRepository.findAll());
    }


    public void modificar(Paciente p, Long id) {
        Optional<Paciente> paciente = pacienteRepository.findById(id);
        Paciente pacienteNew = paciente.get();
        pacienteNew.setNombre(p.getNombre());
        pacienteNew.setApellido(p.getApellido());
        pacienteNew.setDomicilio(p.getDomicilio());
        pacienteNew.setDni(p.getDni());
        pacienteNew.setFechaAlta(p.getFechaAlta());
        pacienteRepository.save(pacienteNew);
    }


    public void eliminar(Long  id) {
        pacienteRepository.deleteById(id);

    }

    public Optional<Paciente> buscarPorId(Long id) {
        return pacienteRepository.findById(id);
    }
}

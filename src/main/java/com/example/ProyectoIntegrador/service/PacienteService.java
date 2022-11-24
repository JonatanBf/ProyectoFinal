package com.example.ProyectoIntegrador.service;

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

    public List<Paciente> listar() {
        return pacienteRepository.findAll();
    }


    public void modificar(Paciente p, Long id) {
        Optional<Paciente> paciente = pacienteRepository.findById(id);
        Paciente pacienteNew = paciente.get();
        if(p.getNombre() != null) pacienteNew.setNombre(p.getNombre());
        if(p.getApellido() != null) pacienteNew.setApellido(p.getApellido());
        if(p.getDomicilio() != null) pacienteNew.setDomicilio(p.getDomicilio());
        if(p.getDni() != null) pacienteNew.setDni(p.getDni());
        if(p.getFechaAlta() != null) pacienteNew.setFechaAlta(p.getFechaAlta());
        pacienteRepository.save(pacienteNew);
    }

    public void eliminar(Long  id) {
        pacienteRepository.deleteById(id);
    }
    public Optional<Paciente> buscarPorId(Long id) {
        return pacienteRepository.findById(id);
    }
    public List<Paciente> buscarDNI(String dni) {
        return pacienteRepository.findByDni(dni);
    }
}

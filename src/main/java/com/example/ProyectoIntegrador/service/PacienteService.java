package com.example.ProyectoIntegrador.service;

import com.example.ProyectoIntegrador.entidades.Paciente;
import com.example.ProyectoIntegrador.repository.PacienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PacienteService {

    private final PacienteRepository pacienteRepository;
    public void agregar(Paciente p){
        pacienteRepository.save(p);
    }

    public List<Paciente> listar() {
        return pacienteRepository.findAll();
    }

    public void modificar(Paciente p, Long id) {
        var pacienteNew = pacienteRepository.findById(id).get();
        if(p.getNombre() != null & !p.getNombre().equals("")) pacienteNew.setNombre(p.getNombre());
        if(p.getApellido() != null & !p.getApellido().equals("")) pacienteNew.setApellido(p.getApellido());
        if(p.getDomicilio() != null & !p.getDomicilio().equals("")) pacienteNew.setDomicilio(p.getDomicilio());
        if(p.getDni() != 0 ) pacienteNew.setDni(p.getDni());
        if(p.getFechaAlta() != null ) pacienteNew.setFechaAlta(p.getFechaAlta());
        pacienteRepository.save(pacienteNew);
    }

    public void eliminar(Long  id) {
        pacienteRepository.deleteById(id);
    }
    public Optional<Paciente> buscarPorId(Long id) {
        return pacienteRepository.findById(id);
    }
    public Paciente buscarDNI(int dni) {
        return pacienteRepository.findByDni(dni);
    }

    public void borrarTodos(){
        pacienteRepository.deleteAll();
    }

}

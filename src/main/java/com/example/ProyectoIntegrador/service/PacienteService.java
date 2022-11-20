package com.example.ProyectoIntegrador.service;

import com.example.ProyectoIntegrador.daos.PacienteDAOH2;
import com.example.ProyectoIntegrador.entidades.Paciente;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@AllArgsConstructor
public class PacienteService {
    private final static Logger logger = LogManager.getLogger(PacienteService.class);
    private final PacienteDAOH2 pacienteDAOH2;

    public void agregar(Paciente p) {
        pacienteDAOH2.agregar(p);
    }

    public List<Paciente> listar() {
        return pacienteDAOH2.listar();
    }


    public void modificar(Paciente p, int id) {
        pacienteDAOH2.modificar(p, id);
    }


    public void eliminar(int id) {
        pacienteDAOH2.eliminar(id);

    }

    public Paciente buscarPorId(int id) {
        return pacienteDAOH2.buscarPorId(id);
    }
}

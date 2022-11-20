package com.example.ProyectoIntegrador.service;

import com.example.ProyectoIntegrador.daos.TurnoDAOH2;
import com.example.ProyectoIntegrador.entidades.Turnos;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@AllArgsConstructor
public class TurnosService {

    private final static Logger logger = LogManager.getLogger(TurnosService.class);
    private final TurnoDAOH2 turnoDAOH2;

    public void agregar(Turnos t) {
        turnoDAOH2.agregar(t);
    }

    public List<Turnos> listar() {
        return turnoDAOH2.listar();
    }


    public void modificar(Turnos t, int id) {
        turnoDAOH2.modificar(t, id);
    }


    public void eliminar(int id) {
        turnoDAOH2.eliminar(id);

    }

    public Turnos buscarPorId(int id) {
        return turnoDAOH2.buscarPorId(id);
    }
}

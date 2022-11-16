package com.example.ProyectoIntegrador.service;

import com.example.ProyectoIntegrador.daos.OdontologoDAOH2;
import com.example.ProyectoIntegrador.entidades.Odontologo;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@AllArgsConstructor
public class OdontologoService {

    private final static Logger logger = LogManager.getLogger(OdontologoService.class);
    private final OdontologoDAOH2 odontologoDAOH2;

    public void agregar(Odontologo o) {
        odontologoDAOH2.agregar(o);
    }

    public List<Odontologo> listar() {
        return odontologoDAOH2.listar();
    }


    public void modificar(Odontologo o, int id) {
        odontologoDAOH2.modificar(o, id);
    }


    public void eliminar(int id) {
       odontologoDAOH2.eliminar(id);

    }

    public Odontologo buscarPorId(int id) {
        return odontologoDAOH2.buscarPorId(id);
    }
}

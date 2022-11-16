package com.example.ProyectoIntegrador.controllers;

import com.example.ProyectoIntegrador.daos.OdontologoDAOH2;
import com.example.ProyectoIntegrador.daos.PacienteDAOH2;
import com.example.ProyectoIntegrador.entidades.Odontologo;
import com.example.ProyectoIntegrador.entidades.Paciente;
import com.example.ProyectoIntegrador.service.OdontologoService;
import com.example.ProyectoIntegrador.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.List;

@RestController
@RequestMapping("/Pacientes")
public class PacienteController {

    private final PacienteService pacienteService= new PacienteService(new PacienteDAOH2());



    @PostMapping("/agregar")
    public Paciente agregar(@RequestBody Paciente paciente){
        pacienteService.agregar(paciente);
        return paciente;
    }


    @GetMapping("/listar")
    public List<Paciente> listar(){
        return pacienteService.listar();
    }


    @PutMapping("/modificar/{id}")
    public Paciente modificar(@RequestBody Paciente paciente, @PathVariable int id){
        pacienteService.modificar(paciente, id);
        return paciente;
    }

    @DeleteMapping("/eliminar/{id}")
    public Paciente eliminar(@PathVariable int id){
        if (pacienteService.buscarPorId(id)!= null){
            pacienteService.eliminar(id);
        }

        return null;
    }

    @GetMapping("/buscar/{id}")
    public Paciente buscarPorId(@PathVariable int id){
        return pacienteService.buscarPorId(id);
    }

}

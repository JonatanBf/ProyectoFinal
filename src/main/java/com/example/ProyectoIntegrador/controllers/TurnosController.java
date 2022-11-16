package com.example.ProyectoIntegrador.controllers;


import com.example.ProyectoIntegrador.daos.TurnoDAOH2;
import com.example.ProyectoIntegrador.entidades.Paciente;
import com.example.ProyectoIntegrador.entidades.Turnos;
import com.example.ProyectoIntegrador.service.TurnosService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Turnos")
public class TurnosController {

    private final TurnosService turnosService= new TurnosService(new TurnoDAOH2());

    @PostMapping("/agregar")
    public Turnos agregar(@RequestBody Turnos turnos){
        turnosService.agregar(turnos);
        return turnos;
    }

    @GetMapping("/listar")
    public List<Turnos> listar(){
        return turnosService.listar();
    }

    @PutMapping("/modificar/{id}")
    public Turnos modificar(@RequestBody Turnos turnos, @PathVariable int id){
        turnosService.modificar(turnos, id);
        return turnos;
    }

    @DeleteMapping("/eliminar/{id}")
    public Turnos eliminar(@PathVariable int id){
        if (turnosService.buscarPorId(id)!= null){
            turnosService.eliminar(id);
        }
        return null;
    }

    @GetMapping("/buscar/{id}")
    public Turnos buscarPorId(@PathVariable int id){
        return turnosService.buscarPorId(id);
    }

}

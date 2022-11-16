package com.example.ProyectoIntegrador.controllers;

import com.example.ProyectoIntegrador.daos.OdontologoDAOH2;
import com.example.ProyectoIntegrador.entidades.Odontologo;
import com.example.ProyectoIntegrador.service.OdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/Odontologos")
public class OdontologoController {

    private final OdontologoService odontologoService= new OdontologoService(new OdontologoDAOH2());



    @PostMapping("/agregar")
    public  Odontologo agregar(@RequestBody Odontologo odontologo){
    odontologoService.agregar(odontologo);
    return odontologo;
}


    @GetMapping("/listar")
    public List<Odontologo> listar(){
        return odontologoService.listar();
    }


    @PutMapping("/modificar/{id}")
    public Odontologo modificar(@RequestBody Odontologo odontologo, @PathVariable int id){
        odontologoService.modificar(odontologo, id);
        return odontologo;
    }

    @DeleteMapping("/eliminar/{id}")
    public Odontologo eliminar(@PathVariable int id){
        if (odontologoService.buscarPorId(id)!= null){
            odontologoService.eliminar(id);
        }

        return null;
    }

    @GetMapping("/buscar/{id}")
    public Odontologo buscarPorId(@PathVariable int id){
        return odontologoService.buscarPorId(id);
    }

}

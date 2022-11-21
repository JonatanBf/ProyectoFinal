package com.example.ProyectoIntegrador.controllers;

import com.example.ProyectoIntegrador.daos.OdontologoDAOH2;
import com.example.ProyectoIntegrador.daos.PacienteDAOH2;
import com.example.ProyectoIntegrador.daos.TurnoDAOH2;
import com.example.ProyectoIntegrador.entidades.Odontologo;
import com.example.ProyectoIntegrador.entidades.Paciente;
import com.example.ProyectoIntegrador.entidades.Turnos;
import com.example.ProyectoIntegrador.service.OdontologoService;
import com.example.ProyectoIntegrador.service.PacienteService;
import com.example.ProyectoIntegrador.service.TurnosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


@RestController
@CrossOrigin
@RequestMapping("/Odontologos")
public class OdontologoController {
    private final OdontologoService odontologoService = new OdontologoService(new OdontologoDAOH2());


    @PostMapping("/agregar")
    public String agregar(@RequestBody Odontologo odontologo) {
        String respuesta = "";
        var nombre = odontologo.getNombre();
        var apellido = odontologo.getApellido();
        var matricula = odontologo.getMatricula();
        if (nombre == null) respuesta += "{Nombre} no puede ser nulo" + "\n";
        if (apellido == null) respuesta += "{Apellido} no puede ser nulo" + "\n";
        if (matricula == null) respuesta += "{Matricula} no puede ser nulo" + "\n";
        if (nombre == null | apellido == null | matricula == null) respuesta += "\n" + "Metedo Agregar: Fallido" + "\n";
        if (nombre == null && apellido == null && matricula == null)
            respuesta = "\n" + "No se registran datos de Odontologo para agregar" + "\n" +
                    "Metedo Agregar: Fallido" + "\n";
        else if (apellido != null && nombre != null && matricula != null) {
            odontologoService.agregar(odontologo);
            respuesta += "\n" + "Se agrego correctamente Odontologo: " + "\n" + "\n" +
                    "{" + "\n" +
                    "Nombre: " + nombre + "," + "\n" +
                    "Apellido: " + apellido + "," + "\n" +
                    "Matricula: " + matricula + "\n" +
                    "}";
        }
        return respuesta;
    }

    @GetMapping("/listar")
    public List<Odontologo> listar() {
        return odontologoService.listar();
    }

    @PutMapping("/modificar/{id}")
    public String modificar(@RequestBody Odontologo odontologo, @PathVariable int id) {
        var nombre = odontologo.getNombre();
        var apellido = odontologo.getApellido();
        var matricula = odontologo.getMatricula();
        String respuesta = "";
        if (odontologoService.buscarPorId(id) != null) {
            if (nombre == null) respuesta += "No se registran datos para {Nombre}" + "\n";
            if (apellido == null) respuesta += "\n" + "No se registran datos para {Apellido}" + "\n";
            if (matricula == null) respuesta += "\n" + "No se registran datos para {Matricula}" + "\n";
            if (apellido == null | nombre == null | matricula == null) respuesta += "\n" + "Update: Fallido" + "\n";
            if (apellido == null && nombre == null && matricula == null)
                respuesta = "\n" + "No se registran datos a Actualizar " + "\n" +
                        "Update Fallido para Odontologo ID: " + id + "\n";
            else if (apellido != null && nombre != null && matricula != null) {
                odontologoService.modificar(odontologo, id);
                respuesta += "Se Actualizo correctamente Odontologo: " + "\n" +
                        "{" + "\n" +
                        "Id: " + id + "," + "\n" +
                        "Nombre: " + nombre + "," + "\n" +
                        "Apellido: " + apellido + "," + "\n" +
                        "Matricula: " + matricula + "\n" +
                        "}";
            }
        } else {
            respuesta += "No existe Odontologo con ID: " + id;
        }
        return respuesta;
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminar(@PathVariable int id) {
        String respuesta = "";
        if (odontologoService.buscarPorId(id) == null) respuesta += "Id: {"+ id + "} no corresponde a ningun Odontologo";
        else {
            var nombre = odontologoService.buscarPorId(id).getNombre();
            var apellido = odontologoService.buscarPorId(id).getApellido();
            var matricula = odontologoService.buscarPorId(id).getMatricula();
            odontologoService.eliminar(id);
            respuesta += "Se elimino correctamente Ddontologo:" + "\n" +
                    "{" + "\n" +
                    "Id: " + id + "," + "\n" +
                    "Nombre: " + nombre + "," + "\n" +
                    "Apellido: " + apellido + "," + "\n" +
                    "Matricula: " + matricula + "\n" +
                    "}";
        }
        return respuesta;
    }

    @GetMapping("/buscar/{id}")
    public String buscarPorId(@PathVariable int id){
        String respuesta = "";
        if (odontologoService.buscarPorId(id) == null) respuesta += "Id: {"+ id + "} no corresponde a ningun Odontologo";
        else {
            var nombre = odontologoService.buscarPorId(id).getNombre();
            var apellido = odontologoService.buscarPorId(id).getApellido();
            var matricula = odontologoService.buscarPorId(id).getMatricula();
            odontologoService.eliminar(id);
            respuesta +=
                    "{" + "\n" +
                    "Id: " + id + "," + "\n" +
                    "Nombre: " + nombre + "," + "\n" +
                    "Apellido: " + apellido + "," + "\n" +
                    "Matricula: " + matricula + "\n" +
                    "}";
        }
        return respuesta;
    }
    }



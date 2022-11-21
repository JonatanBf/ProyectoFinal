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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/Turnos")
public class TurnosController {

    private final TurnosService turnosService= new TurnosService(new TurnoDAOH2());

    @PostMapping("/agregar")
    public String agregar(@RequestBody Turnos turnos){
        String respuesta = "";

        var id_odontologo = turnos.getId_Odontologo();
        var id_paciente = turnos.getId_Odontologo();
        var fecha = turnos.getFecha();

        var oService = new OdontologoService(new OdontologoDAOH2());
        var pService = new PacienteService(new PacienteDAOH2());

        if (oService.buscarPorId(id_odontologo)== null && turnos.getId_Odontologo() != 0) respuesta += "Id {"+id_odontologo+"} no corresponde a ningun Odontologo"+"\n";
        if (pService.buscarPorId(id_paciente)== null && turnos.getId_Paciente() != 0) respuesta += "\n"+"Id {"+id_paciente+"} no corresponde a ningun Paciente"+"\n";
        if (turnos.getId_Odontologo() == 0) respuesta += "\n"+"{Id_Odontologo} no puede ser nulo"+"\n";
        if (turnos.getId_Paciente() == 0) respuesta += "\n"+"{Id_Paciente} no puede ser nulo"+"\n";
        if (turnos.getFecha() == null) respuesta += "\n"+"{Fecha} no puede ser nulo"+"\n";
        if(id_odontologo == 0 | id_paciente == 0 | fecha == null | oService.buscarPorId(id_odontologo)== null | pService.buscarPorId(id_paciente)== null) respuesta += "\n"+"Metedo Agregar: Fallido"+"\n";
        if(id_odontologo == 0 & id_paciente == 0 & fecha == null & oService.buscarPorId(id_odontologo)== null & pService.buscarPorId(id_paciente)== null) respuesta = "\n"+"No se registran datos de Turno para agregar"+"\n"+
                "Metedo Agregar: Fallido"+"\n";
        else if(id_paciente != 0 & id_odontologo != 0 & fecha != null & oService.buscarPorId(id_odontologo)!= null & pService.buscarPorId(id_paciente) != null){
            turnosService.agregar(turnos);
            respuesta = "Se agrego correctamente Turno: "+"\n"+
                    "{" +"\n"+
                    "Id_Odontologo: "+id_odontologo+","+"\n"+
                    "Id_Paciente: "+id_paciente+","+"\n"+
                    "Fecha: "+fecha+"\n"+
                    "}";
        }
        return respuesta;
    }

    @GetMapping("/listar")
    public List<Turnos> listar(){
        return turnosService.listar();
    }

    @PutMapping("/modificar/{id}")
    public String modificar(@RequestBody Turnos turnos, @PathVariable int id){
        String respuesta = "";
        var id_odontologo = turnos.getId_Odontologo();
        var id_paciente = turnos.getId_Odontologo();
        var fecha = turnos.getFecha();
        if (turnosService.buscarPorId(id) != null ){

            var oService = new OdontologoService(new OdontologoDAOH2());
            var pService = new PacienteService(new PacienteDAOH2());

            if (oService.buscarPorId(id_odontologo)== null && id_odontologo != 0) respuesta += "Id {"+id_odontologo+"} no corresponde a ningun Odontologo"+"\n";
            if (pService.buscarPorId(id_paciente)== null && id_paciente != 0) respuesta += "Id {"+id_paciente+"} no corresponde a ningun Paciente"+"\n";
            if (id_odontologo == 0) respuesta += "No se registran datos para {Id_Odontologo}"+"\n";
            if (id_paciente == 0) respuesta += "No se registran datos para {Id_Paciente}"+"\n";
            if (fecha == null) respuesta += "No se registran datos para {FechaAlta}"+"\n";
            if(id_odontologo == 0 | id_paciente == 0 | fecha == null | oService.buscarPorId(id_odontologo)== null | pService.buscarPorId(id_paciente)== null) respuesta += "\n"+"Update: Fallido"+"\n";
            if(id_odontologo == 0 & id_paciente == 0 & fecha == null & oService.buscarPorId(id_odontologo)== null & pService.buscarPorId(id_paciente)== null ) respuesta = "\n"+"No se registran datos a Actualizar "+"\n"+
                    "Update Fallido para Turno ID: "+id+"\n";
            else if(id_paciente != 0 & id_odontologo != 0 & fecha != null & oService.buscarPorId(id_odontologo)!= null & pService.buscarPorId(id_paciente) != null) {
                    turnosService.modificar(turnos, id);
                    respuesta += "\n"+"Se Actualizo correctamente Turno: " + "\n" +"\n" +
                            "{" + "\n" +
                            "Id: " + id + "," + "\n" +
                            "Id_Odontologo: " +id_odontologo + "," + "\n" +
                            "Id_Paciente: " + id_paciente+ "," + "\n" +
                            "Fecha: " +fecha + "\n" +
                            "}";
            }
        }else {
            respuesta += "No existe Turno con ID: "+id;
        }
        return respuesta;
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminar(@PathVariable int id){
        String respuesta = "";
        if (turnosService.buscarPorId(id) == null) respuesta +=  "Id: {"+id+"} no corresponde a ningun Turno";
        else {
            var paciente = turnosService.buscarPorId(id).getId_Paciente();
            var odontologo = turnosService.buscarPorId(id).getId_Odontologo();
            var fecha = turnosService.buscarPorId(id).getFecha();
            turnosService.eliminar(id);
            respuesta += "Se elimino correctamente Turno:"+ "\n"+
                    "{" + "\n" +
                    "Id: " + id + "," + "\n" +
                    "Id_Odontologo: " +odontologo + "," + "\n" +
                    "Id_Paciente: " + paciente+ "," + "\n" +
                    "Fecha: " +fecha + "\n" +
                    "}";
        }
        return respuesta;
    }

    @GetMapping("/buscar/{id}")
    public String buscarPorId(@PathVariable int id){
        String respuesta = "";
        if (turnosService.buscarPorId(id) == null) respuesta += "Id: {"+ id + "} no corresponde a ningun Turno";
        else {
            var paciente = turnosService.buscarPorId(id).getId_Paciente();
            var odontologo = turnosService.buscarPorId(id).getId_Odontologo();
            var fecha = turnosService.buscarPorId(id).getFecha();
            turnosService.eliminar(id);
            respuesta +=
                    "{" + "\n" +
                    "Id: " + id + "," + "\n" +
                    "Id_Odontologo: " +odontologo + "," + "\n" +
                    "Id_Paciente: " + paciente+ "," + "\n" +
                    "Fecha: " +fecha + "\n" +
                    "}";
        }
        return respuesta;
    }
}



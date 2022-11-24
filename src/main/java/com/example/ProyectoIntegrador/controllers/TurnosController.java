package com.example.ProyectoIntegrador.controllers;


import com.example.ProyectoIntegrador.entidades.Turnos;
import com.example.ProyectoIntegrador.service.OdontologoService;
import com.example.ProyectoIntegrador.service.PacienteService;
import com.example.ProyectoIntegrador.service.TurnosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/Turnos")
public class TurnosController {

    @Autowired
    TurnosService turnosService;
    @Autowired
    PacienteService pacienteService;
    @Autowired
    OdontologoService odontologoService;

    @PostMapping("/agregar")
    public ResponseEntity<String> agregar(@RequestBody Turnos turnos){

        String respuesta = "";
        ResponseEntity<String> respuestaHttp = null;

        var id_odontologo = turnos.getId_Odontologo();
        var id_paciente = turnos.getId_Paciente();
        var fecha = turnos.getFecha();

        var oService = new OdontologoService().buscarPorId(id_odontologo);
        var pService = new PacienteService().buscarPorId(id_paciente);

        if (oService.isEmpty() && id_odontologo != 0) respuesta += "Id {"+id_odontologo+"} no corresponde a ningun Odontologo"+"\n";
        if (pService.isEmpty() && id_paciente != 0) respuesta += "\n"+"Id {"+id_paciente+"} no corresponde a ningun Paciente"+"\n";
        if (id_odontologo == 0) respuesta += "\n"+"{Id_Odontologo} no puede ser nulo"+"\n";
        if (id_paciente == 0) respuesta += "\n"+"{Id_Paciente} no puede ser nulo"+"\n";
        if (fecha == null) respuesta += "\n"+"{Fecha} no puede ser nulo"+"\n";
        if(id_odontologo == 0 | id_paciente == 0 | fecha == null | oService.isEmpty() | pService.isEmpty()) {
            respuesta += "\n"+"Metedo Agregar: Fallido"+"\n";
            respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
        if(id_odontologo == 0 & id_paciente == 0 & fecha == null & oService.isEmpty() & pService.isEmpty()) {
            respuesta = "\n"+"No se registran datos de Turno para agregar"+"\n"+
                    "Metedo Agregar: Fallido"+"\n";
            respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
        else if(id_paciente != 0 & id_odontologo != 0 & fecha != null & !oService.isEmpty() & !pService.isEmpty()){
            turnosService.agregar(turnos);
            respuesta = "Se agrego correctamente Turno: "+"\n"+
                    "{" +"\n"+
                    "Id_Odontologo: "+id_odontologo+","+"\n"+
                    "Id_Paciente: "+id_paciente+","+"\n"+
                    "Fecha: "+fecha+"\n"+
                    "}";
            respuestaHttp = ResponseEntity.ok(respuesta);
        }
        return respuestaHttp;
    }

    @GetMapping("/listar")
    public List<Turnos> listar(){
        return turnosService.listar();
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<String> modificar(@RequestBody Turnos turnos, @PathVariable Long id){

        String respuesta = "";
        ResponseEntity<String> respuestaHttp = null;

        var id_odontologo = turnos.getId_Odontologo();
        var id_paciente = turnos.getId_Paciente();
        var fecha = turnos.getFecha();
        if (turnosService.buscarPorId(id).isPresent()){

            var oService = new OdontologoService().buscarPorId(id_odontologo);
            var pService = new PacienteService().buscarPorId(id_paciente);

            if (oService == null && id_odontologo != 0) respuesta += "Id {"+id_odontologo+"} no corresponde a ningun Odontologo"+"\n";
            if (pService.isEmpty() && id_paciente != 0) respuesta += "Id {"+id_paciente+"} no corresponde a ningun Paciente"+"\n";
            if (id_odontologo == 0) respuesta += "No se registran datos para {Id_Odontologo}"+"\n";
            if (id_paciente == 0) respuesta += "No se registran datos para {Id_Paciente}"+"\n";
            if (fecha == null) respuesta += "No se registran datos para {FechaAlta}"+"\n";
            if(id_odontologo == 0 | id_paciente == 0 | fecha == null | oService.isEmpty() | pService.isEmpty()) {
                respuesta += "\n"+"Update: Fallido"+"\n";
                respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
            if(id_odontologo == 0 & id_paciente == 0 & fecha == null & oService.isEmpty() & pService.isEmpty()) {
                respuesta = "\n"+"No se registran datos a Actualizar "+"\n"+
                        "Update Fallido para Turno ID: "+id+"\n";
                respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
            else if(id_paciente != 0 & id_odontologo != 0 & fecha != null & !oService.isEmpty() & !pService.isEmpty()) {
                turnosService.modificar(turnos, id);
                respuesta = "\n"+"Se Actualizo correctamente Turno: " + "\n" +"\n" +
                        "{" + "\n" +
                        "Id: " + id + "," + "\n" +
                        "Id_Odontologo: " + id_odontologo + "," + "\n" +
                        "Id_Paciente: " + id_paciente+ "," + "\n" +
                        "Fecha: " +fecha + "\n" +
                        "}";
                respuestaHttp = ResponseEntity.ok(respuesta);
            }
        }else {
            respuesta += "No existe Turno con ID: "+id;
            respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
        return respuestaHttp;
    }


    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id){

        String respuesta = "";
        ResponseEntity<String> respuestaHttp = null;

        if (turnosService.buscarPorId(id).isEmpty()) {
            respuesta +=  "Id: {"+id+"} no corresponde a ningun Turno";
            respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
        else {
            var paciente = turnosService.buscarPorId(id).get().getId_Paciente();
            var odontologo = turnosService.buscarPorId(id).get().getId_Odontologo();
            var fecha = turnosService.buscarPorId(id).get().getFecha();
            turnosService.eliminar(id);
            respuesta = "Se elimino correctamente Turno:"+ "\n"+
                    "{" + "\n" +
                    "Id: " + id + "," + "\n" +
                    "Odontologo: " +odontologo + "," + "\n" +
                    "Paciente: " + paciente+ "," + "\n" +
                    "Fecha: " +fecha + "\n" +
                    "}";
            respuestaHttp = ResponseEntity.ok(respuesta);
        }
        return respuestaHttp;
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id){

        String respuesta = "";
        ResponseEntity<?> respuestaHttp = null;

        if (turnosService.buscarPorId(id).isEmpty()) {
            respuesta += "Id: {"+ id + "} no corresponde a ningun Turno";
            respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
        else {
            turnosService.buscarPorId(id);
            respuestaHttp = ResponseEntity.ok(turnosService.buscarPorId(id));
        }
        return respuestaHttp;
    }

    @GetMapping("/buscarOdontologo/{id}")
    public ResponseEntity<?> buscarOdontologo(@PathVariable Long id){

        String respuesta = "";
        ResponseEntity<?> respuestaHttp = null;

        if (turnosService.buscarOdontologo(id).isEmpty()){
            respuesta += "Id: {"+ id + "} Odontologo no tiene asignado ningun Turno";
            respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
        else {
            turnosService.buscarOdontologo(id);
            respuestaHttp = ResponseEntity.ok(turnosService.buscarOdontologo(id));
        }
        return respuestaHttp;
    }

    @GetMapping("/buscarPaciente/{id}")
    public ResponseEntity<?> buscarPaciente(@PathVariable Long id){
        String respuesta = "";
        ResponseEntity<?> respuestaHttp = null;

        if (turnosService.buscarPaciente(id).isEmpty()){
            respuesta += "Id: {"+ id + "} Paciente no tiene asignado ningun Turno";
            respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
        else {
            turnosService.buscarPaciente(id);
            respuestaHttp = ResponseEntity.ok(turnosService.buscarPaciente(id));
        }
        return respuestaHttp;
    }

    @DeleteMapping("/eliminarOdontologo/{id}")
    public ResponseEntity<?> eliminarOdontologo(@PathVariable Long id){

        String respuesta = "";
        ResponseEntity<?> respuestaHttp = null;

        if (turnosService.buscarOdontologo(id).isEmpty()) {
            respuesta +=  "Id: {"+id+"} Odontologo no registra ningun Turno"+ "\n"+
                    "Delete Fallido";
            respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
        else {
            respuesta = "Se elimino correctamente Turno:"+ "\n";
            var turnos = turnosService.buscarOdontologo(id);
            for (Turnos t : turnos){
                respuesta +=
                        "{" + "\n" +
                                "Id: " + t.getId() + "," + "\n" +
                                "Id_Odontologo: " +t.getId_Odontologo() + "," + "\n" +
                                "Id_Paciente: " + t.getId_Paciente()+ "," + "\n" +
                                "FechaAlta: " +t.getFecha() + "\n" +
                                "}";
            }
            turnosService.deleteOdontologo(id);
            respuestaHttp = ResponseEntity.ok(respuesta);
        }
        return respuestaHttp;
    }

    @DeleteMapping("/eliminarPaciente/{id}")
    public ResponseEntity<?> eliminarPaciente(@PathVariable Long id){

        String respuesta = "";
        ResponseEntity<?> respuestaHttp = null;

        if (turnosService.buscarPaciente(id).isEmpty()) {
            respuesta +=  "Id: {"+id+"} Paciente no registra ningun Turno"+ "\n"+
                            "Delete Fallido";
            respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
        else {
            respuesta = "Se elimino correctamente Turno:"+ "\n";
            var turnos = turnosService.buscarPaciente(id);
            for (Turnos t : turnos){
                respuesta +=
                        "{" + "\n" +
                        "Id: " + t.getId() + "," + "\n" +
                        "Id_Odontologo: " +t.getId_Odontologo() + "," + "\n" +
                        "Id_Paciente: " + t.getId_Paciente()+ "," + "\n" +
                        "FechaAlta: " +t.getFecha() + "\n" +
                        "}";
            }
            turnosService.deletePaciente(id);
            respuestaHttp = ResponseEntity.ok(respuesta);
        }
        return respuestaHttp;
    }
}



package com.example.ProyectoIntegrador.controllers;

import com.example.ProyectoIntegrador.entidades.TurnoDto;
import com.example.ProyectoIntegrador.entidades.Turno;
import com.example.ProyectoIntegrador.service.OdontologoService;
import com.example.ProyectoIntegrador.service.PacienteService;
import com.example.ProyectoIntegrador.service.TurnoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/turnos")
public class TurnosController {
    TurnoService turnoService;
    PacienteService pacienteService;
    OdontologoService odontologoService;

    @PostMapping("/agregar")
    public ResponseEntity<?> agregar(@RequestBody TurnoDto turnoDto){

        String respuesta = "";
        ResponseEntity<?> respuestaHttp = null;

        var id_odontologo = turnoDto.getId_odontologo();
        var id_paciente = turnoDto.getId_paciente();
        var fecha = turnoDto.getFecha();

        var odontologo = odontologoService.buscarPorId(id_odontologo);
        var paciente = pacienteService.buscarPorId(id_paciente);

        if (odontologo.isEmpty()) respuesta += "\n"+"Id {"+id_odontologo+"} no corresponde a ningun Odontologo"+"\n";
        if (paciente.isEmpty()) respuesta += "\n"+"Id {"+id_paciente+"} no corresponde a ningun Paciente"+"\n";
        if (fecha == null) respuesta += "\n"+"Fecha: no puede ser nulo"+"\n";
        if (fecha == null | id_paciente == 0 | id_odontologo == 0 | odontologo.isEmpty() | paciente.isEmpty() ) {
            respuesta += "\n"+"Metedo Agregar: Fallido"+"\n";
            respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
        else {
            var turno= turnoService.agregarDTO(turnoDto);
            respuestaHttp = ResponseEntity.ok(turno);
        }

        return respuestaHttp;
    }

    @GetMapping("/listar")
    public List<Turno> listar(){
        return turnoService.listar();
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<?> modificar(@RequestBody TurnoDto turnoDto, @PathVariable Long id){

        String respuesta = "";
        ResponseEntity<?> respuestaHttp;

        var id_odontologo = turnoDto.getId_odontologo();
        var id_paciente = turnoDto.getId_paciente();
        var fecha = turnoDto.getFecha();

        if (turnoService.buscarPorId(id).isPresent()){

            var oService = odontologoService.buscarPorId(id_odontologo);
            var pService = pacienteService.buscarPorId(id_paciente);

            if (id_odontologo == null & id_paciente == null & fecha == null )  {
                respuesta = "\n"+"No se registran datos a Actualizar " + "\n" +
                        "Update Fallido para Turno ID: " + id + "\n";
                respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }

            if(oService.isEmpty() | pService.isEmpty()) {
                respuesta += "\n"+"Update: Fallido"+"\n";
                respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);

            }
            else {
                turnoService.modificar(turnoDto, id);
                respuestaHttp = ResponseEntity.ok(turnoService.buscarPorId(id));
            }
        }else {
            respuesta = "\n"+"No existe Turno con ID: "+id;
            respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
        return respuestaHttp;
    }


    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id){

        String respuesta = "";
        ResponseEntity<String> respuestaHttp;

        if (turnoService.buscarPorId(id).isEmpty()) {
            respuesta +=  "\n"+"Id: {"+id+"} no corresponde a ningun Turno";
            respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
        else {
            var paciente = turnoService.buscarPorId(id).get().getPaciente();
            var odontologo = turnoService.buscarPorId(id).get().getOdontologo();
            var fecha = turnoService.buscarPorId(id).get().getFecha();
            turnoService.eliminar(id);
            respuesta = "\n"+"Se elimino correctamente Turno:"+ "\n"+
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
        ResponseEntity<?> respuestaHttp;

        if (turnoService.buscarPorId(id).isEmpty()) {
            respuesta += "\n"+"Id: {"+ id + "} no corresponde a ningun Turno";
            respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
        else {
            turnoService.buscarPorId(id);
            respuestaHttp = ResponseEntity.ok(turnoService.buscarPorId(id));
        }
        return respuestaHttp;
    }

    @GetMapping("/buscarOdontologo/{id}")
    public ResponseEntity<?> buscarOdontologo(@PathVariable Long id){

        String respuesta = "";
        ResponseEntity<?> respuestaHttp;

        if (turnoService.buscarOdontologo(id).isEmpty()){
            respuesta += "\n"+"Id: {"+ id + "} Odontologo no tiene asignado ningun Turno";
            respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
        else {
            turnoService.buscarOdontologo(id);
            respuestaHttp = ResponseEntity.ok(turnoService.buscarOdontologo(id));
        }
        return respuestaHttp;
    }

    @GetMapping("/buscarPaciente/{id}")
    public ResponseEntity<?> buscarPaciente(@PathVariable Long id){
        String respuesta = "";
        ResponseEntity<?> respuestaHttp;

        if (turnoService.buscarPaciente(id).isEmpty()){
            respuesta += "\n"+"Id: {"+ id + "} Paciente no tiene asignado ningun Turno";
            respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
        else {
            turnoService.buscarPaciente(id);
            respuestaHttp = ResponseEntity.ok(turnoService.buscarPaciente(id));
        }
        return respuestaHttp;
    }

    @DeleteMapping("/eliminarOdontologo/{id}")
    public ResponseEntity<?> eliminarOdontologo(@PathVariable Long id){

        String respuesta = "";
        ResponseEntity<?> respuestaHttp;

        if (turnoService.buscarOdontologo(id).isEmpty()) {
            respuesta +=  "\n"+"Id: {"+id+"} Odontologo no registra ningun Turno"+ "\n"+
                    "Delete Fallido";
            respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
        else {
            respuesta = "Se elimino correctamente Turno:"+ "\n"+ "\n";
            var turnos = turnoService.buscarOdontologo(id);
            for (Turno t : turnos){
                respuesta +=
                        "{" + "\n" +
                                "Id: " + t.getId() + "," + "\n" +
                                "Odontologo: " +t.getOdontologo().getId()+ "," + "\n" +
                                "Paciente: " + t.getPaciente().getId()+ "," + "\n" +
                                "FechaAlta: " +t.getFecha() + "\n" +
                                "}"+ "\n" +"\n" ;
            }
            turnoService.deleteOdontologo(id);
            respuestaHttp = ResponseEntity.ok(respuesta);
        }
        return respuestaHttp;
    }

    @DeleteMapping("/eliminarPaciente/{id}")
    public ResponseEntity<?> eliminarPaciente(@PathVariable Long id){

        String respuesta = "";
        ResponseEntity<?> respuestaHttp;

        if (turnoService.buscarPaciente(id).isEmpty()) {
            respuesta +=  "\n"+"Id: {"+id+"} Paciente no registra ningun Turno"+ "\n"+
                            "Delete Fallido";
            respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
        else {
            respuesta = "Se elimino correctamente Turno:"+ "\n"+ "\n";
            var turnos = turnoService.buscarPaciente(id);
            for (Turno t : turnos){
                respuesta +=
                        "{" + "\n" +
                        "Id: " + t.getId() + "," + "\n" +
                        "Odontologo: " +t.getOdontologo().getId() + "," + "\n" +
                        "Id_Paciente: " + t.getPaciente().getId()+ "," + "\n" +
                        "FechaAlta: " +t.getFecha() + "\n" +
                        "}"+ "\n" +"\n" ;
            }
            turnoService.deletePaciente(id);
            respuestaHttp = ResponseEntity.ok(respuesta);
        }
        return respuestaHttp;
    }

    @DeleteMapping("/eliminarRegistros")
    public ResponseEntity<?> eliminarTodos(){
        turnoService.borrarTodos();
        String respuesta ="\n"+"Se eliminaron correctamente todos los registros de Turno";
        ResponseEntity<?> respuestaHttp = ResponseEntity.ok(respuesta);;
        return respuestaHttp;

    }

}



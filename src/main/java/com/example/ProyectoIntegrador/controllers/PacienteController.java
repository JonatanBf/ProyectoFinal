package com.example.ProyectoIntegrador.controllers;


import com.example.ProyectoIntegrador.entidades.Paciente;
import com.example.ProyectoIntegrador.service.PacienteService;
import com.example.ProyectoIntegrador.service.TurnosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/Pacientes")
public class PacienteController {

    @Autowired
    PacienteService pacienteService;

    @Autowired
    TurnosService turnosService;

    @PostMapping("/agregar")
    public ResponseEntity<?> agregar(@RequestBody Paciente paciente){

        String respuesta = "";
        ResponseEntity<?> respuestaHttp = null;

        var nombre = paciente.getNombre();
        var apellido = paciente.getApellido();
        var domicilio = paciente.getDomicilio();
        var dni = paciente.getDni();
        var fechaAlta = paciente.getFechaAlta();

        var getDni = pacienteService.buscarDNI(dni);

        if(nombre == null) respuesta += "{Nombre} no puede ser nulo"+"\n";
        if(apellido == null) respuesta += "{Apellido} no puede ser nulo"+"\n";
        if(domicilio == null) respuesta += "{Domicilio} no puede ser nulo"+"\n";
        if(dni == null) respuesta += "{DNI} no puede ser nulo"+"\n";
        if(fechaAlta == null) respuesta += "{FechaAlta} no puede ser nulo"+"\n";
        if (!getDni.isEmpty() && dni != null) respuesta += "DNI: "+dni+" ya existente" + "\n";
        if(nombre == null | apellido == null | domicilio == null | dni == null | fechaAlta == null | !getDni.isEmpty()){
            respuesta += "\n"+"Metedo Agregar: Fallido"+"\n";
            respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
        if(nombre == null & apellido == null & domicilio == null & dni == null & fechaAlta == null) {
            respuesta = "\n"+"No se registran datos de Paciente para agregar"+"\n"+
                    "Metedo Agregar: Fallido"+"\n";
            respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
        else if(nombre != null & apellido != null & domicilio != null & dni != null & fechaAlta != null && getDni.isEmpty()){
            pacienteService.agregar(paciente);
            respuestaHttp = ResponseEntity.ok(paciente);
        }
        return respuestaHttp ;
    }


    @GetMapping("/listar")
    public List<Paciente> listar(){
        return pacienteService.listar();
    }


    @PutMapping("/modificar/{id}")
    public ResponseEntity<?> modificar(@RequestBody Paciente paciente, @PathVariable Long id){

        String respuesta = "";
        ResponseEntity<?> respuestaHttp = null;

        var nombre = paciente.getNombre();
        var apellido = paciente.getApellido();
        var domicilio = paciente.getDomicilio();
        var dni = paciente.getDni();
        var fechaAlta = paciente.getFechaAlta();

        var getDni = pacienteService.buscarDNI(dni);

        if (pacienteService.buscarPorId(id).isPresent()){
            if (!getDni.isEmpty() && dni != null) respuesta += "DNI: "+dni+" ya existente" + "\n"+
                    "Update Fallido para Paciente ID: "+id+"\n";
            respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            if(apellido == null & nombre == null & domicilio== null & dni == null & fechaAlta == null ) {
                respuesta += "No se registran datos a Actualizar "+"\n"+
                        "Update Fallido para Paciente ID: "+id+"\n";
                respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }else if(getDni.isEmpty()){
                pacienteService.modificar(paciente, id);
                respuestaHttp = ResponseEntity.ok(pacienteService.buscarPorId(id));
            }
        }else {
            respuesta += "No existe Paciente con ID: "+id;
            respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
        return respuestaHttp;
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id){

        String respuesta = "";
        ResponseEntity<String> respuestaHttp = null;

        if (pacienteService.buscarPorId(id).isEmpty()) {
            respuesta += "Id: {"+id+"} no corresponde a ningun Paciente";
            return respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        } if (!turnosService.buscarPaciente(id).isEmpty()){
            respuesta +=  "No es posible eliminar Paciente Id: {" + id + "} "+ "\n" +
                    "Ya que registra un Turno asignado";
            return respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
        else {
            var nombre = pacienteService.buscarPorId(id).get().getNombre();
            var apellido = pacienteService.buscarPorId(id).get().getApellido();
            var domicilio = pacienteService.buscarPorId(id).get().getDomicilio();
            var dni = pacienteService.buscarPorId(id).get().getDni();
            var fechaAlta = pacienteService.buscarPorId(id).get().getFechaAlta();
            pacienteService.eliminar(id);
            respuesta = "Se elimino correctamente Paciente:"+ "\n"+
                    "{" + "\n" +
                    "Nombre: "+nombre+","+"\n"+
                    "Apellido: "+apellido+","+"\n"+
                    "Domicilio: "+domicilio+","+"\n"+
                    "DNI: "+dni+","+","+"\n"+
                    "FechaAlta: "+fechaAlta+"\n"+
                    "}";
            return respuestaHttp = ResponseEntity.ok(respuesta);
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id){

        String respuesta = "";
        ResponseEntity<?> respuestaHttp = null;

        if (pacienteService.buscarPorId(id).isEmpty()) {
            respuesta += "Id: {"+ id + "} no corresponde a ningun Paciente";
            respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
        else {
            pacienteService.buscarPorId(id);
            respuestaHttp = ResponseEntity.ok(pacienteService.buscarPorId(id));
        }
        return respuestaHttp;
    }

    @GetMapping("/buscarDni/{dni}")
    public ResponseEntity<?> buscarDni(@PathVariable String dni){

        String respuesta = "";
        ResponseEntity<?> respuestaHttp = null;

        if (pacienteService.buscarDNI(dni).isEmpty()){
            respuesta += "No se existe ningun registro con {D.N.I} : "+ dni ;
            respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
        else {
            pacienteService.buscarDNI(dni);
            respuestaHttp = ResponseEntity.ok(pacienteService.buscarDNI(dni));
        }
        return respuestaHttp;
    }
}

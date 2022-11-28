package com.example.ProyectoIntegrador.controllers;

import com.example.ProyectoIntegrador.entidades.Paciente;
import com.example.ProyectoIntegrador.service.PacienteService;
import com.example.ProyectoIntegrador.service.TurnoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    PacienteService pacienteService;
    TurnoService turnoService;

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

        if(nombre == null) respuesta += "\n"+"Nombre: no puede ser nulo"+"\n";
        if(apellido == null) respuesta += "\n"+"Apellido: no puede ser nulo"+"\n";
        if(domicilio == null) respuesta += "\n"+"Domicilio: no puede ser nulo"+"\n";
        if(dni == null) respuesta += "\n"+"DNI: no puede ser nulo"+"\n";
        if(fechaAlta == null) respuesta += "\n"+"FechaAlta: no puede ser nulo"+"\n";
        if (getDni != null) respuesta += "\n"+"DNI: {"+dni+"} ya existente" + "\n";
        if(nombre == null | apellido == null | domicilio == null | dni == null | fechaAlta == null | getDni != null){
            respuesta += "\n"+"Metedo Agregar: Fallido "+"\n";
            respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
        if(nombre == null & apellido == null & domicilio == null & dni == null & fechaAlta == null) {
            respuesta = "\n"+"No se registran datos de Paciente para agregar"+"\n"+"\n"+
                    "Metedo Agregar: Fallido "+"\n";
            respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
        else if(nombre != null & apellido != null & domicilio != null & dni != null & fechaAlta != null && getDni == null){
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
            if (getDni != null) {
                respuesta +="\n"+"DNI: {"+dni+"} ya existente" + "\n"+"\n"+
                        "Update Fallido para Paciente ID: "+id+"\n";
                respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
            if(apellido == null & nombre == null & domicilio== null & dni == null & fechaAlta == null ) {
                respuesta +=  "\n"+"No se registran datos a Actualizar "+"\n"+"\n"+
                        "Update Fallido para Paciente ID: "+id+"\n";
                respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }else if(getDni == null){
                pacienteService.modificar(paciente, id);
                respuestaHttp = ResponseEntity.ok(pacienteService.buscarPorId(id));
            }
        }else {
            respuesta = "\n"+"No existe Paciente con ID: "+id;
            respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
        return respuestaHttp;
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id){

        String respuesta = "";
        ResponseEntity<String> respuestaHttp = null;

        if (pacienteService.buscarPorId(id).isEmpty()) {
            respuesta = "\n"+"Id: {"+id+"} no corresponde a ningun Paciente"+"\n";
            return respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        } if (!turnoService.buscarPaciente(id).isEmpty()){
            respuesta =  "\n"+"No es posible eliminar Paciente Id: {" + id + "} "+ "\n" +"\n"+
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
            respuesta = "\n"+"Se elimino correctamente Paciente:"+ "\n"+
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

        if (pacienteService.buscarPorId(id).isEmpty()){
            respuesta = "\n"+"Id: {"+ id + "} no corresponde a ningun Paciente";
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

        if (pacienteService.buscarDNI(dni) == null){
            respuesta += "\n"+"No se existe ningun Paciente con D.N.I : "+ dni ;
            respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
        else {
            pacienteService.buscarDNI(dni);
            respuestaHttp = ResponseEntity.ok(pacienteService.buscarDNI(dni));
        }
        return respuestaHttp;
    }

    @DeleteMapping("/eliminarRegistros")
    public ResponseEntity<?> eliminarTodos(){
        pacienteService.borrarTodos();
        String respuesta ="\n"+"Se eliminaron correctamente todos los registros de Paciente";
        ResponseEntity<?> respuestaHttp = ResponseEntity.ok(respuesta);;
        return respuestaHttp;
    }
}

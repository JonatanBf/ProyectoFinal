package com.example.ProyectoIntegrador.controllers;

import com.example.ProyectoIntegrador.entidades.Odontologo;
import com.example.ProyectoIntegrador.service.OdontologoService;
import com.example.ProyectoIntegrador.service.TurnosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin
@RequestMapping("/Odontologos")
public class OdontologoController {

    @Autowired
    OdontologoService odontologoService;

    @Autowired
    TurnosService turnosService;

    @PostMapping("/agregar")
    public ResponseEntity<?> agregar(@RequestBody Odontologo odontologo) {

        String respuesta = "";
        ResponseEntity<?> respuestaHttp = null;

        var nombre = odontologo.getNombre();
        var apellido = odontologo.getApellido();
        var matricula = odontologo.getMatricula();

        var getMatricula = odontologoService.buscarMatricula(matricula);

        if (nombre == null) respuesta += "{Nombre} no puede ser nulo" + "\n";
        if (apellido == null) respuesta += "{Apellido} no puede ser nulo" + "\n";
        if (matricula == null) respuesta += "{Matricula} no puede ser nulo" + "\n";
        if (!getMatricula.isEmpty() && matricula != null) respuesta += "Matricula: "+matricula+" ya existente" + "\n";
        if (nombre == null | apellido == null | matricula == null | !getMatricula.isEmpty()) {
            respuesta += "\n" + "Metedo Agregar: Fallido" + "\n";
            respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
        if (nombre == null && apellido == null && matricula == null ){
            respuesta = "\n" + "No se registran datos de Odontologo para agregar" + "\n" +
                    "Metedo Agregar: Fallido" + "\n";
            respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
        else if (apellido != null && nombre != null && matricula != null && getMatricula.isEmpty()) {
            odontologoService.agregar(odontologo);
            respuestaHttp = ResponseEntity.ok(odontologo);
        }
        return respuestaHttp ;
    }

    @GetMapping("/listar")
    public List<Odontologo> listar() {
        return odontologoService.listar();
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<?> modificar(@RequestBody Odontologo odontologo, @PathVariable Long id) {

        String respuesta = "";
        ResponseEntity<?> respuestaHttp = null;

        var nombre = odontologo.getNombre();
        var apellido = odontologo.getApellido();
        var matricula = odontologo.getMatricula();

        var getMatricula = odontologoService.buscarMatricula(matricula);

        if (odontologoService.buscarPorId(id).isPresent()) {
            if (!getMatricula.isEmpty() && matricula != null) respuesta += "Matricula: "+matricula+" ya existente" + "\n"+
                    "Update Fallido para Odontologo ID: " + id + "\n";
            respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            if (apellido == null && nombre == null && matricula == null){
                respuesta += "No se registran datos a Actualizar " + "\n" +
                        "Update Fallido para Odontologo ID: " + id + "\n";
                respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }else if(getMatricula.isEmpty()){
                odontologoService.modificar(odontologo, id);
                respuestaHttp = ResponseEntity.ok(odontologoService.buscarPorId(id));
            }
        } else {
            respuesta += "No existe Odontologo con ID: " + id;
            respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
        return respuestaHttp;
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {

        String respuesta = "";
        ResponseEntity<?> respuestaHttp = null;

        if (odontologoService.buscarPorId(id).isEmpty()) {
            respuesta += "Id: {" + id + "} no corresponde a ningun Odontologo";
            return respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        } if (!turnosService.buscarOdontologo(id).isEmpty()){
            respuesta +=  "No es posible eliminar Odontologo Id: {" + id + "} "+ "\n" +
                    "Ya que registra un Turno asignado";
             return respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        } else {
            var nombre = odontologoService.buscarPorId(id).get().getNombre();
            var apellido = odontologoService.buscarPorId(id).get().getApellido();
            var matricula = odontologoService.buscarPorId(id).get().getMatricula();
            odontologoService.eliminar(id);
            respuesta += "Se elimino correctamente Ddontologo:" + "\n" +
                    "{" + "\n" +
                    "Id: " + id + "," + "\n" +
                    "Nombre: " + nombre + "," + "\n" +
                    "Apellido: " + apellido + "," + "\n" +
                    "Matricula: " + matricula + "\n" +
                    "}";
            return respuestaHttp = ResponseEntity.ok(respuesta);
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id){

        String respuesta = "";
        ResponseEntity<?> respuestaHttp = null;

        if (odontologoService.buscarPorId(id).isEmpty()){
            respuesta += "Id: {"+ id + "} no corresponde a ningun Odontologo";
            respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
        else {
            var nombre = odontologoService.buscarPorId(id).get().getNombre();
            var apellido = odontologoService.buscarPorId(id).get().getApellido();
            var matricula = odontologoService.buscarPorId(id).get().getMatricula();
            odontologoService.buscarPorId(id);
            respuestaHttp = ResponseEntity.ok(odontologoService.buscarPorId(id));
        }
        return respuestaHttp;
    }

    @GetMapping("/buscarMatricula/{matricula}")
    public ResponseEntity<?> buscarMatricula(@PathVariable String matricula){

        String respuesta = "";
        ResponseEntity<?> respuestaHttp = null;

        if (odontologoService.buscarMatricula(matricula).isEmpty()){
            respuesta += "No se existe ningun registro con {Matricula} : "+ matricula ;
            respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
        else {
            odontologoService.buscarMatricula(matricula);
            respuestaHttp = ResponseEntity.ok(odontologoService.buscarMatricula(matricula));
        }
        return respuestaHttp;
    }

}



package com.example.ProyectoIntegrador.controllers;

import com.example.ProyectoIntegrador.entidades.Odontologo;
import com.example.ProyectoIntegrador.service.OdontologoService;
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

    @PostMapping("/agregar")
    public ResponseEntity<?> agregar(@RequestBody Odontologo odontologo) {

        String respuesta = "";
        ResponseEntity<?> respuestaHttp = null;

        var nombre = odontologo.getNombre();
        var apellido = odontologo.getApellido();
        var matricula = odontologo.getMatricula();

        if (nombre == null) respuesta += "{Nombre} no puede ser nulo" + "\n";
        if (apellido == null) respuesta += "{Apellido} no puede ser nulo" + "\n";
        if (matricula == null) respuesta += "{Matricula} no puede ser nulo" + "\n";
        if (nombre == null | apellido == null | matricula == null) {
            respuesta += "\n" + "Metedo Agregar: Fallido" + "\n";
            respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
        if (nombre == null && apellido == null && matricula == null){
            respuesta = "\n" + "No se registran datos de Odontologo para agregar" + "\n" +
                    "Metedo Agregar: Fallido" + "\n";
            respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
        else if (apellido != null && nombre != null && matricula != null) {
            odontologoService.agregar(odontologo);
            respuestaHttp = ResponseEntity.ok(odontologo);
        }
        return respuestaHttp ;
    }

    @GetMapping("/listar")
    public Optional<List<Odontologo>> listar() {
        return odontologoService.listar();
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<?> modificar(@RequestBody Odontologo odontologo, @PathVariable Long id) {

        String respuesta = "";
        ResponseEntity<?> respuestaHttp = null;

        var nombre = odontologo.getNombre();
        var apellido = odontologo.getApellido();
        var matricula = odontologo.getMatricula();

        if (odontologoService.buscarPorId(id).isPresent()) {
            if (nombre == null) respuesta += "No se registran datos para {Nombre}" + "\n";
            if (apellido == null) respuesta += "\n" + "No se registran datos para {Apellido}" + "\n";
            if (matricula == null) respuesta += "\n" + "No se registran datos para {Matricula}" + "\n";
            if (apellido == null | nombre == null | matricula == null) {
                respuesta += "\n" + "Update: Fallido" + "\n";
                respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
            if (apellido == null && nombre == null && matricula == null){
                respuesta = "\n" + "No se registran datos a Actualizar " + "\n" +
                        "Update Fallido para Odontologo ID: " + id + "\n";
                respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }

            else if (apellido != null && nombre != null && matricula != null) {
                odontologoService.modificar(odontologo, id);
                respuestaHttp = ResponseEntity.ok(odontologo);
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
            respuesta = "Id: {"+ id + "} no corresponde a ningun Odontologo";
            respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
        else {
            var nombre = odontologoService.buscarPorId(id).get().getNombre();
            var apellido = odontologoService.buscarPorId(id).get().getApellido();
            var matricula = odontologoService.buscarPorId(id).get().getMatricula();
            odontologoService.eliminar(id);
            respuesta = "Se elimino correctamente Ddontologo:" + "\n" +
                    "{" + "\n" +
                    "Id: " + id + "," + "\n" +
                    "Nombre: " + nombre + "," + "\n" +
                    "Apellido: " + apellido + "," + "\n" +
                    "Matricula: " + matricula + "\n" +
                    "}";
            respuestaHttp = ResponseEntity.ok(respuesta);
        }
        return respuestaHttp;
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
    }



package com.example.ProyectoIntegrador.controllers;

import com.example.ProyectoIntegrador.entidades.Odontologo;
import com.example.ProyectoIntegrador.service.OdontologoService;
import com.example.ProyectoIntegrador.service.TurnoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/odontologos")
public class OdontologoController {

    OdontologoService odontologoService;
    TurnoService turnoService;

    @PostMapping("/agregar")
    public ResponseEntity<?> agregar(@RequestBody Odontologo odontologo) {

        String respuesta = "";
        ResponseEntity<?> respuestaHttp = null;

        var nombre = odontologo.getNombre();
        var apellido = odontologo.getApellido();
        var matricula = odontologo.getMatricula();

        var getMatricula = odontologoService.buscarMatricula(matricula);

        if (nombre == null) respuesta += "\n"+"Nombre: no puede ser nulo" + "\n";
        if (apellido == null) respuesta += "\n"+"Apellido: no puede ser nulo" + "\n";
        if (matricula == null) respuesta += "\n"+"Matricula: no puede ser nulo" + "\n";
        if (getMatricula != null & matricula!= null ) respuesta += "\n"+"Matricula: "+matricula+" ya existente" + "\n";
        if (nombre == null | apellido == null | matricula == null | getMatricula != null) {
            respuesta += "\n" +  "Metedo Agregar: Fallido " + "\n";
            respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
        if (nombre == null & apellido == null & matricula == null ){
            respuesta = "\n" + "No se registran datos de Odontologo para agregar" + "\n" +
                    "Metedo Agregar: Fallido " + "\n";
            respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }else if (getMatricula == null  & nombre != null & apellido != null & matricula!= null){
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
        ResponseEntity<?> respuestaHttp= null;

        var nombre = odontologo.getNombre();
        var apellido = odontologo.getApellido();
        var matricula = odontologo.getMatricula();

        var getMatricula = odontologoService.buscarMatricula(matricula);

        if (odontologoService.buscarPorId(id).isPresent()) {
            if (getMatricula != null) {
                respuesta ="\n"+ "Matricula: {"+matricula+"} ya existe en nuestros registros" + "\n"+ "\n"+
                        "Update Fallido para Odontologo ID: " + id + "\n";
                respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
            if (apellido == null & nombre == null & matricula == null){
                respuesta ="\n" + "No se registran datos a Actualizar " + "\n" +"\n" +
                        "Update Fallido para Odontologo ID: " + id + "\n";
                respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }else if (getMatricula == null){
                odontologoService.modificar(odontologo, id);
                respuestaHttp = ResponseEntity.ok(odontologoService.buscarPorId(id));
            }
        } else {
            respuesta = "\n"+"No existe Odontologo con ID: " + id;
            respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
        return respuestaHttp;
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {

        String respuesta = "";
        ResponseEntity<?> respuestaHttp = null;

        if (odontologoService.buscarPorId(id).isEmpty()) {
            respuesta = "\n"+"Id: {" + id + "} no corresponde a ningun Odontologo";
            return respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        } if (!turnoService.buscarOdontologo(id).isEmpty() ){
            respuesta += "\n"+ "No es posible eliminar Odontologo Id: {" + id + "} "+ "\n" +"\n"+
                    "Ya que registra un Turno asignado";
             return respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        } else {
            var nombre = odontologoService.buscarPorId(id).get().getNombre();
            var apellido = odontologoService.buscarPorId(id).get().getApellido();
            var matricula = odontologoService.buscarPorId(id).get().getMatricula();
            odontologoService.eliminar(id);
            respuesta = "\n"+"Se elimino correctamente Ddontologo:" + "\n" +
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

        if (odontologoService.buscarPorId(id).isEmpty()) {
            respuesta = "\n"+"Id: {"+ id + "} no corresponde a ningun Odontologo";
            respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
        else {
            odontologoService.buscarPorId(id);
            respuestaHttp = ResponseEntity.ok(odontologoService.buscarPorId(id));
        }
        return respuestaHttp;
    }

    @GetMapping("/buscarMatricula/{matricula}")
    public ResponseEntity<?> buscarMatricula(@PathVariable String matricula){

        String respuesta = "";
        ResponseEntity<?> respuestaHttp = null;

        if (odontologoService.buscarMatricula(matricula) == null){
            respuesta = "\n" +"No se existe ningun Odontologo con Matricula : "+ matricula ;
            respuestaHttp = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
        else {
            odontologoService.buscarMatricula(matricula);
            respuestaHttp = ResponseEntity.ok(odontologoService.buscarMatricula(matricula));
        }
        return respuestaHttp;
    }

    @DeleteMapping("/eliminarRegistros")
    public ResponseEntity<?> eliminarTodos(){
        odontologoService.borrarTodos();
        String respuesta ="\n"+"Se eliminaron correctamente todos los registros de Odontologo";
        ResponseEntity<?> respuestaHttp = ResponseEntity.ok(respuesta);;
        return respuestaHttp;
    }

}



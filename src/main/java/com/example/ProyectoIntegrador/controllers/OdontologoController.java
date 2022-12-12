package com.example.ProyectoIntegrador.controllers;

import com.example.ProyectoIntegrador.entidades.Odontologo;
import com.example.ProyectoIntegrador.exception.RequestException;
import com.example.ProyectoIntegrador.service.OdontologoService;
import com.example.ProyectoIntegrador.service.TurnoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/odontologos")
public class OdontologoController {

    private final OdontologoService odontologoService;
    private final TurnoService turnoService;

    @PostMapping("/agregar")
    public ResponseEntity<?> agregar(@RequestBody Odontologo odontologo) {

        var nombre = odontologo.getNombre();
        var apellido = odontologo.getApellido();
        var matricula = odontologo.getMatricula();

        var getMatricula = odontologoService.buscarMatricula(matricula);

        if (getMatricula!= null){
            throw new RequestException("400 Bad Request","Matricula ya existente");
        }
        if (nombre != null & apellido != null & matricula != null & !nombre.equals("") & !apellido.equals("") & !matricula.equals("")){
            odontologoService.agregar(odontologo);
            return new ResponseEntity<>("El Odontologo se guardo con exito", null, HttpStatus.CREATED);
       }else{
            throw new RequestException("400 Bad Request","Sintaxis Invalida");
    }
    }

    @GetMapping("/listar")
    public List<Odontologo> listar() {
        return odontologoService.listar();
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<?> modificar(@RequestBody Odontologo odontologo, @PathVariable Long id) {

        var nombre = odontologo.getNombre();
        var apellido = odontologo.getApellido();
        var matricula = odontologo.getMatricula();

        var getId = odontologoService.buscarPorId(id);
        var getMatricula = odontologoService.buscarMatricula(matricula);

        if (getId.isPresent()) {
            if (getMatricula != null){
                throw new RequestException("400 Bad Request","Matricula ya existente");
            }
            if (nombre != null & apellido != null & matricula != null & !nombre.equals("") & !apellido.equals("") & !matricula.equals("")){
                odontologoService.modificar(odontologo,id);
                return new ResponseEntity<>("El Odontologo se Actualizo con exito", null, HttpStatus.CREATED);
            }else{
                throw new RequestException("400 Bad Request","Sintaxis Invalida");
            }
        } else {
            throw new RequestException("400 Bad Request","No existe Odontologo para el Id: "+id);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {

        var getId = odontologoService.buscarPorId(id);

        if (getId.isEmpty()) {
            throw new RequestException("400 Bad Request","No existe Odontologo para el Id: "+id);
        } if (!turnoService.buscarOdontologo(id).isEmpty() ){
            throw new RequestException("400 Bad Request",("No es posible eliminar Odontologo Id: " + id + ", ya que registra un Turno asignado"));
        } else {
            odontologoService.eliminar(id);
            return new ResponseEntity<>(("El Odontologo con Id: "+id+" se elimino con exito"), null, HttpStatus.CREATED);
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id){

        ResponseEntity<?> respuestaHttp = null;
        var getId = odontologoService.buscarPorId(id);

        if (getId.isEmpty()) {
            throw new RequestException("400 Bad Request","Id: {"+ id + "} no corresponde a ningun Odontologo");
        }
        else {
            respuestaHttp = ResponseEntity.ok(getId);
        }
        return respuestaHttp;
    }

    @GetMapping("/buscarMatricula/{matricula}")
    public ResponseEntity<?> buscarMatricula(@PathVariable String matricula){

        ResponseEntity<?> respuestaHttp = null;
        var getMatricula = odontologoService.buscarMatricula(matricula);

        if (getMatricula == null){
            throw new RequestException("400 Bad Request","No existe ningun Odontologo con Matricula : "+ matricula );
        }
        else {
            respuestaHttp = ResponseEntity.ok(getMatricula);
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



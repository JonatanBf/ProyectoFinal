package com.example.ProyectoIntegrador.controllers;

import com.example.ProyectoIntegrador.entidades.Paciente;
import com.example.ProyectoIntegrador.exception.RequestException;
import com.example.ProyectoIntegrador.service.PacienteService;
import com.example.ProyectoIntegrador.service.TurnoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@CrossOrigin
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

        if (getDni!= null){
            throw new RequestException("400 Bad Request","Matricula ya existente");
        }
        if (nombre != null & !nombre.equals("") & apellido != null & !apellido.equals("") & domicilio != null & !domicilio.equals("") & dni != 0  & fechaAlta !=null ){
            pacienteService.agregar(paciente);
            return new ResponseEntity<>("El paciente se guardo con exito", null, HttpStatus.CREATED);
        }else{
            throw new RequestException("400 Bad Request","Sintaxis Invalida");
        }
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

        var getPacienteId = pacienteService.buscarPorId(id);
        var getPacienteDni = pacienteService.buscarDNI(dni);


        if (getPacienteId.isPresent()) {
            if (getPacienteDni != null){
                throw new RequestException("400 Bad Request","DNI ya existente");
            }
            if (nombre != null & !nombre.equals("") & apellido != null & !apellido.equals("") & domicilio != null & !domicilio.equals("") & dni != 0 & fechaAlta !=null ){
                pacienteService.modificar(paciente,id);
                return new ResponseEntity<>("El Paciente se Actualizo con exito", null, HttpStatus.CREATED);
            }else{
                throw new RequestException("400 Bad Request","Sintaxis Invalida");
            }
        } else {
            throw new RequestException("400 Bad Request","No existe Paciente para el Id: "+id);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){

        var getId = pacienteService.buscarPorId(id);

        if (getId.isEmpty()) {
            throw new RequestException("400 Bad Request","No existe Paciente para el Id: "+id);
        } if (!turnoService.buscarPaciente(id).isEmpty() ){
            throw new RequestException("400 Bad Request",("No es posible eliminar Paciente Id: " + id + ", ya que registra un Turno asignado"));
        } else {
            pacienteService.eliminar(id);
            return new ResponseEntity<>(("El Paciente con Id: "+id+" se elimino con exito"), null, HttpStatus.CREATED);
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id){

        ResponseEntity<?> respuestaHttp = null;
        var getId = pacienteService.buscarPorId(id);

        if (getId.isEmpty()) {
            throw new RequestException("400 Bad Request","Id: {"+ id + "} no corresponde a ningun Paciente");
        }
        else {
            respuestaHttp = ResponseEntity.ok(getId);
        }
        return respuestaHttp;
    }

    @GetMapping("/buscarDni/{dni}")
    public ResponseEntity<?> buscarDni(@PathVariable int dni){

        ResponseEntity<?> respuestaHttp = null;
        var getDni = pacienteService.buscarDNI(dni);

        if (getDni == null){
            throw new RequestException("400 Bad Request","No existe ningun Odontologo con DNI : "+ dni );
        }
        else {
            respuestaHttp = ResponseEntity.ok(getDni);
        }
        return respuestaHttp;
    }

    @DeleteMapping("/eliminarRegistros")
    public ResponseEntity<?> eliminarTodos(){
        pacienteService.borrarTodos();
        String respuesta ="\n"+"Se eliminaron correctamente todos los registros de Paciente";
        return ResponseEntity.ok(respuesta);
    }


}

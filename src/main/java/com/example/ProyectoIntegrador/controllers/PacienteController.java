package com.example.ProyectoIntegrador.controllers;

import com.example.ProyectoIntegrador.daos.PacienteDAOH2;
import com.example.ProyectoIntegrador.entidades.Odontologo;
import com.example.ProyectoIntegrador.entidades.Paciente;
import com.example.ProyectoIntegrador.service.PacienteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/Pacientes")
public class PacienteController {

    private final PacienteService pacienteService= new PacienteService(new PacienteDAOH2());



    @PostMapping("/agregar")
    public String agregar(@RequestBody Paciente paciente){
        String respuesta = "";
        var nombre = paciente.getNombre();
        var apellido = paciente.getApellido();
        var domicilio = paciente.getDomicilio();
        var dni = paciente.getDni();
        var fechaAlta = paciente.getFechaAlta();
        if(nombre == null) respuesta += "{Nombre} no puede ser nulo"+"\n";
        if(apellido == null) respuesta += "{Apellido} no puede ser nulo"+"\n";
        if(domicilio == null) respuesta += "{Domicilio} no puede ser nulo"+"\n";
        if(dni == null) respuesta += "{DNI} no puede ser nulo"+"\n";
        if(fechaAlta == null) respuesta += "{FechaAlta} no puede ser nulo"+"\n";
        if(nombre == null | apellido == null | domicilio == null | dni == null | fechaAlta == null) respuesta += "\n"+"Metedo Agregar: Fallido"+"\n";
        if(nombre == null & apellido == null & domicilio == null & dni == null & fechaAlta == null) respuesta = "\n"+"No se registran datos de Paciente para agregar"+"\n"+
                "Metedo Agregar: Fallido"+"\n";
        else if(nombre != null & apellido != null & domicilio != null & dni != null & fechaAlta != null){
            pacienteService.agregar(paciente);
            respuesta += "\n"+"Se agrego correctamente Paciente: "+"\n"+"\n" +
                    "{" +"\n"+
                    "Nombre: "+nombre+","+"\n"+
                    "Apellido: "+apellido+","+"\n"+
                    "Domicilio: "+domicilio+","+"\n"+
                    "DNI: "+dni+","+"\n"+
                    "FechaAlta: "+fechaAlta+"\n"+
                    "}";
        }
        return respuesta;
    }


    @GetMapping("/listar")
    public List<Paciente> listar(){
        return pacienteService.listar();
    }


    @PutMapping("/modificar/{id}")
    public String modificar(@RequestBody Paciente paciente, @PathVariable int id){
        var nombre = paciente.getNombre();
        var apellido = paciente.getApellido();
        var domicilio = paciente.getDomicilio();
        var dni = paciente.getDni();
        var fechaAlta = paciente.getFechaAlta();
        String respuesta = "";
        if (pacienteService.buscarPorId(id) != null ){
            if(nombre == null) respuesta += "No se registran datos para {Nombre}" + "\n";
            if(apellido == null) respuesta += "\n"+"No se registran datos para {Apellido}" + "\n";
            if(domicilio== null) respuesta += "\n"+"No se registran datos para {Domicilio}"+"\n";
            if(dni== null) respuesta += "\n"+"No se registran datos para {DNI}"+"\n";
            if(fechaAlta== null) respuesta += "\n"+"No se registran datos para {FechaAlta}"+"\n";
            if(apellido == null | nombre == null | domicilio== null | dni == null | fechaAlta == null) respuesta += "\n"+"Update: Fallido"+"\n";
            if(apellido == null & nombre == null & domicilio== null & dni == null & fechaAlta == null ) respuesta = "\n"+"No se registran datos a Actualizar "+"\n"+
                    "Update Fallido para Paciente ID: "+id+"\n";
            else if(apellido != null & nombre != null & domicilio != null & dni != null & fechaAlta != null) {
                pacienteService.modificar(paciente, id);
                respuesta += "\n"+"Se Actualizo correctamente Paciente: " + "\n" +
                        "{" +"\n"+
                        "Nombre: "+nombre+","+"\n"+
                        "Apellido: "+apellido+","+"\n"+
                        "Domicilio: "+domicilio+","+"\n"+
                        "DNI: "+dni+","+"\n"+
                        "FechaAlta: "+fechaAlta+"\n"+
                        "}";
            }
        }else {
            respuesta += "No existe Paciente con ID: "+id;
        }
        return respuesta;
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminar(@PathVariable int id){
        String respuesta = "Id: {"+id+"} no corresponde a ningun Paciente";
        var nombre = pacienteService.buscarPorId(id).getNombre();
        var apellido = pacienteService.buscarPorId(id).getApellido();
        var domicilio = pacienteService.buscarPorId(id).getDomicilio();
        var dni = pacienteService.buscarPorId(id).getDni();
        var fechaAlta = pacienteService.buscarPorId(id).getFechaAlta();
        if (pacienteService.buscarPorId(id)!= null){
            pacienteService.eliminar(id);
            respuesta = "Se elimino correctamente Paciente:"+ "\n"+
                    "{" + "\n" +
                    "Nombre: "+nombre+","+"\n"+
                    "Apellido: "+apellido+","+"\n"+
                    "Domicilio: "+domicilio+","+"\n"+
                    "DNI: "+dni+","+","+"\n"+
                    "FechaAlta: "+fechaAlta+"\n"+
                    "}";
        }
        return respuesta;
    }

    @GetMapping("/buscar/{id}")
    public String buscarPorId(@PathVariable int id){
        String respuesta = "Id: {"+id+"} no corresponde a ningun Paciente";
        var nombre = pacienteService.buscarPorId(id).getNombre();
        var apellido = pacienteService.buscarPorId(id).getApellido();
        var domicilio = pacienteService.buscarPorId(id).getDomicilio();
        var dni = pacienteService.buscarPorId(id).getDni();
        var fechaAlta = pacienteService.buscarPorId(id).getFechaAlta();
        if (pacienteService.buscarPorId(id) == null){
            return respuesta;
        }
        return respuesta = "Se elimino correctamente Paciente:"+ "\n"+
                "{" + "\n" +
                "Nombre: "+nombre+","+"\n"+
                "Apellido: "+apellido+","+"\n"+
                "Domicilio: "+domicilio+","+"\n"+
                "DNI: "+dni+","+","+"\n"+
                "FechaAlta: "+fechaAlta+"\n"+
                "}";
    }
}

package com.example.ProyectoIntegrador.controllers;


import com.example.ProyectoIntegrador.entidades.Rol;
import com.example.ProyectoIntegrador.exception.RequestException;
import com.example.ProyectoIntegrador.service.RolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
public class RolController {

   RolService rolService;

    @PostMapping("/agregar")
    public ResponseEntity<?> agregar(@RequestBody Rol rol) {

        var nombre = rol.getNombre();

        var buscarPorNombre = rolService.buscarPorNombre(nombre);

        if (buscarPorNombre!= null){
            throw new RequestException("400 Bad Request","Rol ya existente");
        }
        if (nombre != null &  !nombre.equals("")  ){
            rolService.agregar(rol);
            return new ResponseEntity<>("El Rol se guardo con exito", null, HttpStatus.CREATED);
        }else{
            throw new RequestException("400 Bad Request","Sintaxis Invalida");
        }
    }

    @GetMapping("/listar")
    public List<Rol> listar(){
        return rolService.listar();
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<?> modificar(@RequestBody Rol rol, @PathVariable Long id) {

        var nombre = rol.getNombre();

        var buscarPorNombre = rolService.buscarPorNombre(nombre);
        var getId = rolService.buscarPorId(id);


        if (getId.isPresent()) {
            if (buscarPorNombre!= null){
                throw new RequestException("400 Bad Request","Rol ya existente");
            }
            if (nombre != null &  !nombre.equals("")  ){
                rolService.modificar(rol, id);
                return new ResponseEntity<>("El Rol se actualizo con exito", null, HttpStatus.CREATED);
            }else{
                throw new RequestException("400 Bad Request","Sintaxis Invalida");
            }
        } else {
            throw new RequestException("400 Bad Request","No existe Rol para el Id: "+id);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){

        var getId = rolService.buscarPorId(id);

        if (getId.isEmpty()) {
            throw new RequestException("400 Bad Request","No existe Rol para el Id: "+id);
        } else {
           rolService.eliminar(id);
            return new ResponseEntity<>(("El Rol con Id: "+id+" se elimino con exito"), null, HttpStatus.CREATED);
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id){

        ResponseEntity<?> respuestaHttp = null;
        var getId = rolService.buscarPorId(id);

        if (getId.isEmpty()) {
            throw new RequestException("400 Bad Request","Id: {"+ id + "} no corresponde a ningun Rol");
        }
        else {
            respuestaHttp = ResponseEntity.ok(getId);
        }
        return respuestaHttp;
    }
}

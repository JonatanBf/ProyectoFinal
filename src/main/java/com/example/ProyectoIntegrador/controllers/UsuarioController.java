package com.example.ProyectoIntegrador.controllers;

import com.example.ProyectoIntegrador.dto.RolUsuarioDto;
import com.example.ProyectoIntegrador.entidades.Usuario;
import com.example.ProyectoIntegrador.exception.RequestException;
import com.example.ProyectoIntegrador.service.RolService;
import com.example.ProyectoIntegrador.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    private RolService rolService;

    @PostMapping("/agregar")
    public ResponseEntity<?> agregar(@RequestBody Usuario usuario) {
        var email = usuario.getUserName();
        var password = usuario.getPassword();

        var buscarEmail = usuarioService.buscarPorEmail(email);
            if (buscarEmail!= null){
                throw new RequestException("400 Bad Request","Email ya existente");
            }
            if (email != null & password != null & !email.equals("") & !password.equals("") ){
                usuarioService.agregar(usuario);
                return new ResponseEntity<>("El Usuario se guardo con exito", null, HttpStatus.CREATED);
            }else{
                throw new RequestException("400 Bad Request","Sintaxis Invalida");
            }

    }

    @PostMapping("/agregarRol")
    public ResponseEntity<?> agregarRol(@RequestBody RolUsuarioDto formRol) {
        var email = formRol.getUserName();
        var nameRol = formRol.getNombreRol();
        var buscarRol = rolService.buscarPorNombre(nameRol);
        var buscarEmail = usuarioService.buscarPorEmail(email);
        if ( buscarEmail == null){
            throw new RequestException("400 Bad Request","No existe Email: "+email);
        }if (buscarRol == null){
            throw new RequestException("400 Bad Request","No existe Rol: "+nameRol);
        }else{
            usuarioService.agregarRol(formRol);
            return new ResponseEntity<>("El Rol se agrego con exito", null, HttpStatus.CREATED);
        }

    }

    @GetMapping("/listar")
    public List<Usuario> listar(){
        return usuarioService.listarUsuario();
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<?> modificar(@RequestBody Usuario usuario, @PathVariable Long id) {

        var email = usuario.getUserName();
        var password = usuario.getPassword();

        var getId = usuarioService.buscarPorId(id);
        var buscarPorEmail = usuarioService.buscarPorEmail(email);

        if (getId.isPresent()) {
            if (buscarPorEmail!= null){
                throw new RequestException("400 Bad Request","Email ya existente");
            }
            if (email != null & password != null & !email.equals("") & !password.equals("") ){
                usuarioService.modificar(usuario, id);
                return new ResponseEntity<>("El Usuario se actualizo con exito", null, HttpStatus.CREATED);
            }else{
                throw new RequestException("400 Bad Request","Sintaxis Invalida");
            }
        } else {
            throw new RequestException("400 Bad Request","No existe Usuario para el Id: "+id);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){

        var getId = usuarioService.buscarPorId(id);

        if (getId.isEmpty()) {
            throw new RequestException("400 Bad Request","No existe Usuario para el Id: "+id);
        } else {
            usuarioService.eliminar(id);
            return new ResponseEntity<>(("El Usuario con Id: "+id+" se elimino con exito"), null, HttpStatus.CREATED);
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id){

        ResponseEntity<?> respuestaHttp = null;
        var getId = usuarioService.buscarPorId(id);

        if (getId.isEmpty()) {
            throw new RequestException("400 Bad Request","Id: {"+ id + "} no corresponde a ningun Usuario");
        }
        else {
            respuestaHttp = ResponseEntity.ok(getId);
        }
        return respuestaHttp;
    }

    @GetMapping("/buscarEmail/{email}")
    public ResponseEntity<?> buscarEmail(@PathVariable String email){

        ResponseEntity<?> respuestaHttp = null;
        var getCorreo = usuarioService.buscarPorEmail(email);

        if (getCorreo == null){
            throw new RequestException("400 Bad Request","No existe ningun Usuario con Email : "+ email);
        }
        else {
            respuestaHttp = ResponseEntity.ok(getCorreo);
        }
        return respuestaHttp;
    }

    @DeleteMapping("/eliminarRegistros")
    public ResponseEntity<?> eliminarTodos(){
        usuarioService.borrarTodos();
        String respuesta ="\n"+"Se eliminaron correctamente todos los registros de Usuario";
        ResponseEntity<?> respuestaHttp = ResponseEntity.ok(respuesta);;
        return respuestaHttp;
    }



}

package com.example.ProyectoIntegrador.service;

import com.example.ProyectoIntegrador.dto.RolUsuarioDto;
import com.example.ProyectoIntegrador.entidades.Usuario;
import com.example.ProyectoIntegrador.repository.RolRepository;
import com.example.ProyectoIntegrador.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j //Similar al Logger
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    public Usuario agregar(Usuario user) {
        log.info("Guardar nuevo Usuario en la base de datos: "+user.getUserName());
        return usuarioRepository.save(user);
    }
    public Usuario agregarRol(RolUsuarioDto form ) {
        var email = form.getUserName();
        var rolName = form.getNombreRol();
        log.info("Agregando Rol al usuario:  "+" Email: "+email+" Rol:"+rolName);
        var user = usuarioRepository.findByUserName(email);
        var rol = rolRepository.findByNombre(rolName);
        if ( user != null & rol != null) user.getRoles().add(rol);
        return user;
    }
    public Usuario buscarPorEmail(String email) {
        log.info("Obteniendo Usuario:  "+email);
        return usuarioRepository.findByUserName(email);
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public void eliminar(Long  id) {
        usuarioRepository.deleteById(id);
    }
    public List<Usuario> listarUsuario() {
        log.info("Listando Usuarios");
        return usuarioRepository.findAll();
    }

    public void modificar(Usuario u, Long id) {
        var usuarioNew = usuarioRepository.findById(id).get();
        if(u.getUserName() != null & !u.getUserName().equals("")) usuarioNew.setUserName(u.getUserName());
        if(u.getPassword() != null & !u.getPassword().equals("")) usuarioNew.setPassword(u.getPassword());
        usuarioRepository.save(usuarioNew);
    }

    public void borrarTodos(){
        usuarioRepository.deleteAll();
    }


}

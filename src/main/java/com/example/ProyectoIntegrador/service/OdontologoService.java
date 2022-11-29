package com.example.ProyectoIntegrador.service;

import com.example.ProyectoIntegrador.entidades.Odontologo;
import com.example.ProyectoIntegrador.repository.OdontologoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService {

    @Autowired
    OdontologoRepository odontologoRepository;


    public void agregar(Odontologo o){
        odontologoRepository.save(o);
    }

    public List<Odontologo> listar() {
        return (odontologoRepository.findAll());
    }

    public void modificar(Odontologo o, Long id) {
        var odontoNew = odontologoRepository.findById(id).get();
        if(o.getNombre() != null) odontoNew.setNombre(o.getNombre());
        if(o.getApellido() != null)odontoNew.setApellido(o.getApellido());
        if(o.getMatricula() != null)odontoNew.setMatricula(o.getMatricula());
        odontologoRepository.save(odontoNew);
    }

    public void eliminar(Long  id) {
       odontologoRepository.deleteById(id);
    }

    public Optional<Odontologo> buscarPorId(Long id) {
        return odontologoRepository.findById(id);
    }

    public Odontologo buscarMatricula(String matricula) {
        return odontologoRepository.findByMatricula(matricula);
    }

    public void borrarTodos(){
        odontologoRepository.deleteAll();
    }


}

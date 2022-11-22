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

    public Optional<List<Odontologo>> listar() {
        return Optional.of(odontologoRepository.findAll());
    }


    public void modificar(Odontologo o, Long id) {
        Optional<Odontologo> odontologo = odontologoRepository.findById(id);
        var odontologoNew = odontologo.get();
        odontologoNew.setNombre(o.getNombre());
        odontologoNew.setApellido(o.getApellido());
        odontologoNew.setMatricula(o.getMatricula());
        odontologoRepository.save(odontologoNew);
    }


    public void eliminar(Long  id) {
       odontologoRepository.deleteById(id);

    }

    public Optional<Odontologo> buscarPorId(Long id) {
        return odontologoRepository.findById(id);
    }
}

package com.example.ProyectoIntegrador.service;

import com.example.ProyectoIntegrador.entidades.Odontologo;
import com.example.ProyectoIntegrador.repository.OdontologoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OdontologoService {
    private final OdontologoRepository odontologoRepository;
    public void agregar(Odontologo o){
        odontologoRepository.save(o);
    }
    public List<Odontologo> listar() {
        return (odontologoRepository.findAll());
    }

    public void modificar(Odontologo o, Long id) {
        var odontoNew = odontologoRepository.findById(id).get();
        if(o.getNombre() != null & !o.getNombre().equals("")) odontoNew.setNombre(o.getNombre());
        if(o.getApellido() != null & !o.getApellido().equals(""))odontoNew.setApellido(o.getApellido());
        if(o.getMatricula() != null & !o.getMatricula().equals(""))odontoNew.setMatricula(o.getMatricula());
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

package com.example.ProyectoIntegrador.daos;

import java.util.List;

public interface Dao<T> {

    public void agregar(T t) ;

    public List<T> listar();

    public void modificar(T t, int id);

    public void eliminar(int id);

    public T buscarPorId(int id);

}

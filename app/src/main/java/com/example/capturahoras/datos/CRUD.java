package com.example.capturahoras.datos;

import java.util.List;

public interface CRUD<T> {

    List<T> listarActivos();
    T leerPorId (int id);
    void guardar(T t);
    void actualizar(T t);
    void eliminar(long id);
}

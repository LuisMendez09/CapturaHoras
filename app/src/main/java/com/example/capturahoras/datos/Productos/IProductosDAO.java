package com.example.capturahoras.datos.Productos;

import com.example.capturahoras.datos.CRUD;
import com.example.capturahoras.modelo.Productos;

public interface IProductosDAO extends CRUD<Productos> {
    String TABLE_PRODUCTOS="Productos";
    String CLAVE="clave";
    String DESCRIPCION="Descripcion";

    void reiniciarTabla();
    Productos leerPorDescripcion(String descripcion);
}

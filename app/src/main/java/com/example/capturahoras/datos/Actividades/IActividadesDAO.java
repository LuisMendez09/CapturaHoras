package com.example.capturahoras.datos.Actividades;


import com.example.capturahoras.datos.CRUD;
import com.example.capturahoras.modelo.Actividades;

public interface IActividadesDAO extends CRUD<Actividades> {
    String TABLE_ACTIVIDADES="Actividades";

    String CLAVE="Clave";
    String DESCRIPCION="Descripcion";
    String PRECIO="Precio";

    void reiniciarTabla();
    Actividades leerPorDescripcion(String descripcion);
    int ContarRegistros();

}

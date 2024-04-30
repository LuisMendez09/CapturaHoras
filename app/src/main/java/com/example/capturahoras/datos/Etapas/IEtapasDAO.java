package com.example.capturahoras.datos.Etapas;


import com.example.capturahoras.datos.CRUD;
import com.example.capturahoras.modelo.Etapas;

public interface IEtapasDAO extends CRUD<Etapas> {
    String TABLE_ETAPAS="Etapas";
    String CLAVE="clave";
    String DESCRIPCION="Descripcion";

    void reiniciarTabla();
    Etapas leerPorDescripcion(String descripcion);
    int ContarRegistros();
}

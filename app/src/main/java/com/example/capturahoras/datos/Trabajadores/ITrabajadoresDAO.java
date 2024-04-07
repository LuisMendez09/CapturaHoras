package com.example.capturahoras.datos.Trabajadores;


import com.example.capturahoras.datos.CRUD;
import com.example.capturahoras.modelo.Trabajadores;

public interface ITrabajadoresDAO extends CRUD<Trabajadores> {
    String TABLE_TRABAJADORES="Trabajandores";
    String CLAVE="numero";
    String NOMBRE="Nombre";
    String STATUS="Status";

    void reiniciarTabla();
}

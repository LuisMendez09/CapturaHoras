package com.example.capturahoras.datos.campos;

import com.example.capturahoras.datos.CRUD;
import com.example.capturahoras.modelo.Campos;


public interface ICamposDAO extends CRUD<Campos> {

    String TABLE_CAMPOS="Campos";
    String CLAVE="clave";
    String DESCRIPCION="Descripcion";
    String SUPERFICIE="Superficie";
    String PRODUCTO="idProducto";
    String CCE="idCce";
    String ETAPA="idEtapa";

    void reiniciarTabla();
    Campos leerPorDescripcion(String descripcion);
    int ContarRegistros();
}

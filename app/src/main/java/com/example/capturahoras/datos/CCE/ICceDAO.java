package com.example.capturahoras.datos.CCE;

import com.example.capturahoras.datos.CRUD;
import com.example.capturahoras.modelo.CCE;

public interface ICceDAO extends CRUD<CCE> {
    String TABLE_CCE="CCE";
    String CLAVE="clave";
    String DESCRIPCION="Descripcion";

    void reiniciarTabla();
    CCE leerPorDescripcion(String descripcion);
}

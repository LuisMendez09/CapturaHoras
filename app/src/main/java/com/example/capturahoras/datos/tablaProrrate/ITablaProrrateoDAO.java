package com.example.capturahoras.datos.tablaProrrate;

import com.example.capturahoras.datos.CRUD;
import com.example.capturahoras.modelo.TablasProrrateo;

public interface ITablaProrrateoDAO extends CRUD<TablasProrrateo> {
    String TABLE_TABLAPRORRATEO="TablaProrrate";
    String CLAVE="clave";
    String DESCRIPCION="Descripcion";

    void reiniciarTabla();
    TablasProrrateo leerPorDescripcion(String descripcion);
    int ContarRegistros();
}

package com.example.capturahoras.datos.GrupoCampos;

import com.example.capturahoras.datos.CRUD;
import com.example.capturahoras.modelo.GrupoCampos;

import java.util.ArrayList;

public interface IGrupoCamposDAO extends CRUD<GrupoCampos> {
    String TABLE_GRUPOPRORRATEO="GrupoCampos";
    String IDCAMPO="ClaveCampo";
    String IDTABLAPRORRATEO="idTablaProrrateo";

    public ArrayList<GrupoCampos> getCampos(int idTablaProrrateo);
}

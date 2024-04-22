package com.example.capturahoras.datos.CamposTrabajados;

import com.example.capturahoras.datos.CRUD;
import com.example.capturahoras.modelo.CamposTrabajados;

import java.util.ArrayList;
import java.util.HashMap;

public interface ICamposTrabajadosDAO extends CRUD<CamposTrabajados> {
    String TABLE_CAMPOSTRABAJADOS="camposTrabajados";
    String ID ="Id";
    String ID_CAMPO ="idCampo";
    String ID_TABLAPRORRATEO="idTablaProrrateo";
    String ID_ASISTENCIA ="idAsistencia";
    String ID_ACTIVIDAD ="idActividad";
    String ENVIADO ="enviado";
    String HORAS ="horas";

    String PRODUCTO="idProducto";
    String CCE="idCce";
    String ETAPA="idEtapa";

    HashMap<String,CamposTrabajados> listarCamposTrabajados(long idAsistencia);
}

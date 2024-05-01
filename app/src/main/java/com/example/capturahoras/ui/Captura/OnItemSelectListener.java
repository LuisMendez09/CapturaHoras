package com.example.capturahoras.ui.Captura;

import android.widget.TextView;

import com.example.capturahoras.modelo.Actividades;
import com.example.capturahoras.modelo.Campos;
import com.example.capturahoras.modelo.CamposTrabajados;
import com.example.capturahoras.modelo.TablasProrrateo;

import java.util.HashMap;
import java.util.List;

public interface OnItemSelectListener {
    void onItemsSelectActividad(List<Actividades> actividades);
    void onItemsSelectCampo(List<Campos> campos);
    void onItemsSelectTabla(TablasProrrateo tablasProrrateo);
    void onItemsSelectCampo(Campos campo);
    void onItemsSelectCamposTrabajados(HashMap<String, CamposTrabajados> hct);
}

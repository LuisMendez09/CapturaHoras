package com.example.capturahoras.controlador;

import android.content.Context;

import com.example.capturahoras.datos.Etapas.DatosEtapaDAO;
import com.example.capturahoras.modelo.Etapas;

import java.util.List;

public class EtapasControl {
    public static List<Etapas> getEtapas(Context c){

        return  new DatosEtapaDAO(c).listarActivos();
    }

    public static CharSequence[] getEtapasArray(Context c){
        List<Etapas> etapas = new DatosEtapaDAO(c).listarActivos();
        CharSequence[] etapa = new CharSequence[etapas.size()];
        for (int i = 0; i < etapas.size(); i++) {
            etapa[i] = etapas.get(i).getDescripcion();        }
        return  etapa;
    }

    public static Etapas getEtapas(Context c,String nombreEtapa){
        return new DatosEtapaDAO(c).leerPorDescripcion(nombreEtapa);
    }

    public static int getTotalEtapas(Context c){
        return new DatosEtapaDAO(c).listarActivos().size();
    }
}

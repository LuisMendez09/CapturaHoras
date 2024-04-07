package com.example.capturahoras.controlador;

import android.content.Context;

import com.example.capturahoras.datos.campos.DatosCampoDAO;
import com.example.capturahoras.modelo.Campos;

import java.util.List;

public class CamposControl {
    public static List<Campos> getCampos(Context c){

        return new DatosCampoDAO(c).listarActivos();
    }

    public static CharSequence[] getCamposArray(Context c){
        List<Campos> campos = new DatosCampoDAO(c).listarActivos();
        CharSequence[] camp = new CharSequence[campos.size()];
        for (int i = 0; i < campos.size(); i++) {
            camp[i] = campos.get(i).getDescripcion();
        }
        return  camp;
    }

    public static Campos getCampo(Context c,String descripcion){
        return new DatosCampoDAO(c).leerPorDescripcion(descripcion);
    }

    public static int getTotalCampos(Context c){
        return new DatosCampoDAO(c).listarActivos().size();
    }
}

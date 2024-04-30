package com.example.capturahoras.controlador;

import android.content.Context;


import com.example.capturahoras.datos.CCE.DatosCceDAO;
import com.example.capturahoras.modelo.CCE;

import java.util.List;

public class CceControl {
    public static List<CCE> getCce(Context c){

        return  new DatosCceDAO(c).listarActivos();
    }

    public static CharSequence[] getCceArray(Context c){
        List<CCE> cceList = new DatosCceDAO(c).listarActivos();
        CharSequence[] cce = new CharSequence[cceList.size()];
        for (int i = 0; i < cceList.size(); i++) {
            cce[i] = cceList.get(i).getDescripcion();        }
        return  cce;
    }

    public static CCE getCce(Context c,String nombreCce){
        return new DatosCceDAO(c).leerPorDescripcion(nombreCce);
    }

    public static int getTotalCce(Context c){
        return new DatosCceDAO(c).ContarRegistros();
    }
}

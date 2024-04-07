package com.example.capturahoras.controlador;

import android.content.Context;

import com.example.capturahoras.datos.Actividades.DatosActividadesDAO;
import com.example.capturahoras.modelo.Actividades;

import java.util.List;

public class ActividadesControl {
    public static List<Actividades> getActividades(Context c){

        return  new DatosActividadesDAO(c).listarActivos();
    }

    public static CharSequence[] getActividadesArray(Context c){
        List<Actividades> actividades = new DatosActividadesDAO(c).listarActivos();
        CharSequence[] acti = new CharSequence[actividades.size()];
        for (int i = 0; i < actividades.size(); i++) {
            acti[i] = actividades.get(i).getDescripcion();
        }
        return  acti;
    }

    public static Actividades getActividad(Context c,String nombreActividad){
        return new DatosActividadesDAO(c).leerPorDescripcion(nombreActividad);
    }

    public static int getTotalActividades(Context c){
        return new DatosActividadesDAO(c).listarActivos().size();
    }
}

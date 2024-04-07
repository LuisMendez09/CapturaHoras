package com.example.capturahoras.controlador;

import android.content.Context;

import com.example.capturahoras.datos.tablaProrrate.TablaProrrateoDAO;
import com.example.capturahoras.modelo.TablasProrrateo;

import java.util.List;

public class TablasControl {
    public static List<TablasProrrateo> getTablas(Context c){
        return new TablaProrrateoDAO(c).listarActivos();
    }

    public static CharSequence[] getTablasArray(Context c){
        List<TablasProrrateo> tablas = new TablaProrrateoDAO(c).listarActivos();
        CharSequence[] tab = new CharSequence[tablas.size()];
        for (int i = 0; i < tablas.size(); i++) {
            tab[i] = tablas.get(i).getDescripcion();
        }
        return  tab;
    }

    public static TablasProrrateo getTabla(Context c, String descripcion){
        return new TablaProrrateoDAO(c).leerPorDescripcion(descripcion);
    }

    public static int getTotalTablas(Context c){
        return new TablaProrrateoDAO(c).listarActivos().size();
    }
}

package com.example.capturahoras.controlador;

import android.content.Context;

import com.example.capturahoras.datos.Actividades.DatosActividadesDAO;
import com.example.capturahoras.datos.Trabajadores.DatosTrabajadoresDAO;


public class Controlador {
    private static Context context;

    public Controlador(Context context){
        this.context = context;
        //new DatosActividadesDAO(context);
        //new DatosTrabajadoresDAO(context);
    }
}

package com.example.capturahoras.controlador;

import android.content.Context;

import com.example.capturahoras.datos.Trabajadores.DatosTrabajadoresDAO;
import com.example.capturahoras.modelo.Trabajadores;


public class TrabajadoresControl {
    public static void getTrabajadores(Context c){
        //new DatosTrabajadoresDAO(c).listarActivos();
        return;
    }

    public static Trabajadores getTrabajadoresPorId(Context c, int numero){
        return new DatosTrabajadoresDAO(c).leerPorId(numero);
    }

    public static int getTotalTrabajadores(Context c){
        return new DatosTrabajadoresDAO(c).ContarRegistros();
    }
}

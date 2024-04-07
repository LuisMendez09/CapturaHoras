package com.example.capturahoras.controlador;

import android.content.Context;

import com.example.capturahoras.datos.Asistencia.AsistenciaDAO;
import com.example.capturahoras.datos.CamposTrabajados.CamposTrabajadosDAO;
import com.example.capturahoras.modelo.Asistencia;
import com.example.capturahoras.modelo.Campos;
import com.example.capturahoras.modelo.CamposTrabajados;
import com.example.capturahoras.modelo.Settings;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class AsistenciaControl {
    public static List<Asistencia> getAsistenciaDia(Context c){
        List<Asistencia> asistencias = new AsistenciaDAO(c).getAsistenciaDia();
        return asistencias;
    }

    public static List<Asistencia> getAsistenciaSinEnviar(Context c){
        if (!SesionControl.validarSesion())
            return new AsistenciaDAO(c).getAsistenciaSinEnviar();
        else
            return  new AsistenciaDAO(c).getAsistenciaSinEnviarSesionActiva();
    }

    public static Asistencia getAsistenciaTrabajador(Context c,int idTrabajdor){
        Asistencia asistencias = new AsistenciaDAO(c).getAsistenciaTrabajadorDia(idTrabajdor);
        return asistencias;
    }

    public static void guardar(Context c, Asistencia asistencia){
        asistencia.setDispositivo(Settings.USUARIO);
        new AsistenciaDAO(c).guardar(asistencia);

        HashMap<String, CamposTrabajados> ct = asistencia.getCamposTrabajados();
        Set<String> strings = ct.keySet();

        for (String act : strings ) {
            CamposTrabajados camtra = ct.get(act);
            camtra.setAsistencia(asistencia);
            new CamposTrabajadosDAO(c).guardar(camtra);
        }
    }

    public static void actualizarEnvio(Context c,Asistencia asistencia){
        new AsistenciaDAO(c).actualizar(asistencia);

        CamposTrabajadosDAO camposTrabajadosDAO = new CamposTrabajadosDAO(c);
        HashMap<String, CamposTrabajados> ct = asistencia.getCamposTrabajados();
        for (String act : ct.keySet()) {
            camposTrabajadosDAO.actualizar(ct.get(act));
        }
    }

    public static void actualizar(Context c,Asistencia asistencia){
        new AsistenciaDAO(c).actualizar(asistencia);

        CamposTrabajadosDAO camposTrabajadosDAO = new CamposTrabajadosDAO(c);
        camposTrabajadosDAO.eliminar(asistencia.getId());
        HashMap<String, CamposTrabajados> ct = asistencia.getCamposTrabajados();
        for (String act : ct.keySet()) {
            if(ct.get(act).getAsistencia() == null)
                ct.get(act).setAsistencia(asistencia);
            camposTrabajadosDAO.guardar(ct.get(act));
        }
    }

    public static void eliminar(Context c,long asistencia){
        new CamposTrabajadosDAO(c).eliminar(asistencia);
        new AsistenciaDAO(c).eliminar(asistencia);
    }
}

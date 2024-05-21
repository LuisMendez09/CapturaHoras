package com.example.capturahoras.controlador;

import android.content.Context;

import com.example.capturahoras.complemento.Complementos;
import com.example.capturahoras.complemento.FileLog;
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
        FileLog.e(Complementos.TAG_ASISTENCIA, "getAsistenciaDia ");
        List<Asistencia> asistencias = new AsistenciaDAO(c).getAsistenciaDia();
        return asistencias;
    }

    public static List<Asistencia> getAsistenciaSinEnviar(Context c){
        FileLog.e(Complementos.TAG_ASISTENCIA, "getAsistenciaSinEnviar ");
        if (!SesionControl.validarSesion())
            return new AsistenciaDAO(c).getAsistenciaSinEnviar();
        else
            return  new AsistenciaDAO(c).getAsistenciaSinEnviarSesionActiva();
    }

    public static Asistencia getAsistenciaTrabajadorDia(Context c, int idTrabajdor){
        FileLog.e(Complementos.TAG_ASISTENCIA, "getAsistenciaTrabajadorDia idTrabajdor="+idTrabajdor);
        Asistencia asistencias = new AsistenciaDAO(c).getAsistenciaTrabajadorDia(idTrabajdor);
        return asistencias;
    }

    public static void guardar(Context c, Asistencia asistencia){
        FileLog.e(Complementos.TAG_ASISTENCIA, "guardar "+asistencia.toString());
        asistencia.setDispositivo(Settings.getInstanacia().getUSUARIO());
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
        FileLog.e(Complementos.TAG_ASISTENCIA, "actualizarEnvio "+asistencia.toString());

        new AsistenciaDAO(c).actualizar(asistencia);

        CamposTrabajadosDAO camposTrabajadosDAO = new CamposTrabajadosDAO(c);
        HashMap<String, CamposTrabajados> ct = asistencia.getCamposTrabajados();
        for (String act : ct.keySet()) {
            camposTrabajadosDAO.actualizar(ct.get(act));
        }
    }

    public static void actualizar(Context c,Asistencia asistencia){
        FileLog.e(Complementos.TAG_ASISTENCIA, "actualizar asistencia "+asistencia.toString());
        new AsistenciaDAO(c).actualizar(asistencia);

        CamposTrabajadosDAO camposTrabajadosDAO = new CamposTrabajadosDAO(c);
        camposTrabajadosDAO.eliminar(asistencia.getId());
        HashMap<String, CamposTrabajados> ct = asistencia.getCamposTrabajados();
        for (String act : ct.keySet()) {
            //if(ct.get(act).getAsistencia() == null)
            ct.get(act).setAsistencia(asistencia);
            camposTrabajadosDAO.guardar(ct.get(act));
        }
    }

    public static void eliminar(Context c,long asistencia){
        FileLog.e(Complementos.TAG_ASISTENCIA, "actualizar eliminar "+asistencia);
        new CamposTrabajadosDAO(c).eliminar(asistencia);
        new AsistenciaDAO(c).eliminar(asistencia);
    }
}

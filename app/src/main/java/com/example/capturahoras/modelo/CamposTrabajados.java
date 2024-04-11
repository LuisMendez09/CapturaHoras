package com.example.capturahoras.modelo;

import android.os.Build;

import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CamposTrabajados implements Serializable{
    private int id;
    private Asistencia asistencia;
    private Actividades actividades;
    private TablasProrrateo tablasProrrateo;

    private List<Campos> campos;

    private int enviado;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Asistencia getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(Asistencia asistencia) {
        this.asistencia = asistencia;
    }

    public int getEnviado() {
        return enviado;
    }

    public void setEnviado(int enviado) {
        this.enviado = enviado;
    }

    //public List<ActividadesASistencia> getActividadesASistencias() {
    //    return actividadesASistencias;
    //}

    //public void setActividadesASistencias(ActividadesASistencia actividadesASistencias) {
    //    this.actividadesASistencias.add(actividadesASistencias);
    //}

    public List<Campos> getCampo(){
        return campos;
    }

    public void setCampos(List<Campos> c) {
        campos=c;
    }

    public TablasProrrateo getTablaProrrateo() {
        return tablasProrrateo;
    }

    public void setTablaProrrateo(TablasProrrateo TablasProrrateo) {
      this.tablasProrrateo = TablasProrrateo;
    }

    public Actividades getActividades() {
        return actividades;
    }

    public void setActividades(Actividades actividades) {
        this.actividades = actividades;
    }

    public List<JsonObject> getJson(int idAsistencia){
        List<JsonObject> values = new ArrayList<>();

        int index = 1;
        if(campos != null)
            if(campos.size()>0)
                index = campos.size();

        for (int i= 0; i<index;i++){
            JsonObject v = new JsonObject();

            v.addProperty("id_asistencia",idAsistencia);
            v.addProperty("id_actividad",actividades.getClave());
            if(tablasProrrateo != null)
                v.addProperty("id_tablaProrrateo",tablasProrrateo.getId());

            if(campos != null){
                if(campos.size()>0){
                    v.addProperty("id_campo",campos.get(i).getClave());
                    v.addProperty("idProducto",campos.get(i).getProductoSeleccionado().getClave());
                    v.addProperty("etapa",campos.get(i).getEtapaSeleccionada().getClave());
                    v.addProperty("cce",campos.get(i).getCceSeleccionada().getClave());
                    v.addProperty("ccg",31);
                }
            }



            values.add(v);
        }

       return values;
    }
}

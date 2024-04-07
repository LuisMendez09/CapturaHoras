package com.example.capturahoras.modelo;

import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Campos implements Serializable {
    private int clave;
    private String descripcion;
    private double superficie;
    private List<TablasProrrateo> tablas_Prorateo;
    private int id_canposTrabajados;
    private Productos productoSeleccionado;
    private Etapas etapaSeleccionada;
    private CCE cceSeleccionada;

    public int getId_canposTrabajados() {
        return id_canposTrabajados;
    }

    public void setId_canposTrabajados(int id_canposTrabajados) {
        this.id_canposTrabajados = id_canposTrabajados;
    }

    private boolean select = false;

    public int getClave() {
        return clave;
    }

    public void setClave(int clave) {
        this.clave = clave;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getSuperficie() {
        return superficie;
    }

    public void setSuperficie(double superficie) {
        this.superficie = superficie;
    }

    public List<TablasProrrateo> getTablas_Prorateo() {
        return tablas_Prorateo;
    }

    public void setTablas_Prorateo(List<TablasProrrateo> tablas_Prorateo) {
        this.tablas_Prorateo = tablas_Prorateo;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public Productos getProductoSeleccionado() {
        return productoSeleccionado;
    }

    public void setProductoSeleccionado(Productos productoSeleccionado) {
        this.productoSeleccionado = productoSeleccionado;
    }

    public Etapas getEtapaSeleccionada() {
        return etapaSeleccionada;
    }

    public void setEtapaSeleccionada(Etapas etapaSeleccionada) {
        this.etapaSeleccionada = etapaSeleccionada;
    }

    public CCE getCceSeleccionada() {
        return cceSeleccionada;
    }

    public void setCceSeleccionada(CCE cceSeleccionada) {
        this.cceSeleccionada = cceSeleccionada;
    }

    @Override
    public String toString() {
        return "Campos{" +
                "clave=" + clave +
                ", desipcion='" + descripcion + '\'' +
                ", superficie=" + superficie +
                '}';
    }

    public JsonObject getJson(){


            JsonObject v = new JsonObject();
            v.addProperty("clave",getClave());
            v.addProperty("descripcion",getDescripcion());
            v.addProperty("superficie",getSuperficie());
            v.addProperty("Id_prodicto",getProductoSeleccionado().getClave());
            v.addProperty("etapa",getEtapaSeleccionada().getClave());
            v.addProperty("cce",getCceSeleccionada().getClave());
            v.addProperty("ccg",31);

        return v;
    }
}

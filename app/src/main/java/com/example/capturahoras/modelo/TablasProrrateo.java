package com.example.capturahoras.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TablasProrrateo implements Serializable {
    @SerializedName("id")
    @Expose
    private String Id;
    @SerializedName("descripcion")
    @Expose
    private String Descripcion;

    private float horas_trabajadas;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.Descripcion = descripcion;
    }

    public float getHoras_trabajadas() {
        return horas_trabajadas;
    }

    public void setHoras_trabajadas(float horas_trabajadas) {
        this.horas_trabajadas = horas_trabajadas;
    }

    /*public boolean isSelect() {
        return Select;
    }

    public void setSelect(boolean select) {
        Select = select;
    }*/
}

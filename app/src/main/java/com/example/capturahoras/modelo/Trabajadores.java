package com.example.capturahoras.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Struct;

public class Trabajadores implements Serializable {
    @SerializedName("numero")
    @Expose
    private int numero;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("estatus")
    @Expose
    private boolean estatus;


    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean getEstatus() {
        return estatus;
    }
    public int getEstatusInt() {
        return estatus?1:0;
    }
    public String getStatusString() {
        return !estatus?"BAJA":"ALTA";
    }

    public void setEstatus(boolean estatus) {
        this.estatus = estatus;
    }

    public void setEstatusInt(int estatus) {
        this.estatus = estatus==0?false: true;
    }

    @Override
    public String toString() {
        return "Trabajadores{" +
                "clave=" + numero +
                ", nombre='" + nombre + '\'' +
                ", status=" + estatus +
                '}';
    }
}

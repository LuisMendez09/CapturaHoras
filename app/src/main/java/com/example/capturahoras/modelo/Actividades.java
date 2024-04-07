package com.example.capturahoras.modelo;

import java.io.Serializable;

public class Actividades implements Serializable {
    private int clave;
    private String descripcion;
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

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    @Override
    public String toString() {
        return "Actividades{" +
                "clave=" + clave +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}

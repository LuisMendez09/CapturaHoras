package com.example.capturahoras.modelo;

import com.example.capturahoras.complemento.Complementos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Asistencia implements Serializable {

    private long id;
    private long fecha;
    private Trabajadores trabajadores;
    //private Actividades Actividad;
    private float totalHoras;
    private String dispositivo;
    private long horaInicial;
    private long horaFinal;
    private int enviado;
    private HashMap<String,CamposTrabajados> camposTrabajados =new HashMap<>();

    public long getFecha() {
        return fecha;
    }
    public String getFechaString()
    {
        return Complementos.convertirDateAstring2(new Date(fecha));
    }

    public void setFecha(long fecha) {
        this.fecha = fecha;
    }

    public Trabajadores getTrabajador() {
        return trabajadores;
    }

    public void setTrabajador(Trabajadores idTrabajador) {
        trabajadores = idTrabajador;
    }

    //public Actividades getActividad() {
    //    return Actividad;
    //}

    //public void setActividad(Actividades actividad) {
    //    Actividad = actividad;
    //}

    public float getTotalHoras() {
        return totalHoras;
    }

    public void setTotalHoras(float totalHoras) {
        this.totalHoras = totalHoras;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(String dispositivo) {
        this.dispositivo = dispositivo;
    }

    public long getHoraInicial() {
        return horaInicial;
    }

    public void setHoraInicial(long horaInicial) {
        this.horaInicial = horaInicial;
    }

    public long getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(long horaFinal) {
        this.horaFinal = horaFinal;
    }

    public HashMap<String,CamposTrabajados> getCamposTrabajados() {
        return camposTrabajados;
    }

    public void setCamposTrabajados(HashMap<String,CamposTrabajados> camposTrabajados) {
        this.camposTrabajados = camposTrabajados;
    }

    public int getEnviado() {
        return enviado;
    }

    public void setEnviado(int enviado) {
        this.enviado = enviado;
    }
}

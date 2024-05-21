package com.example.capturahoras.modelo;

import android.content.Context;

import java.util.Date;

public final class Settings {
    public static Context CONTEXT;
    private static Settings settings = null;
    private Date DATE =null;
    private String FECHA="";
    private String URL="";
    private String MAILS="";
    private String USUARIO="";
    private int FIN_JORNADA =0;

    private Settings(){

    }

    public static Settings getInstanacia(){
        if(settings == null)
            settings = new Settings();

        return settings;
    }


    public Date getDATE() {
        return DATE;
    }

    public void setDATE(Date DATE) {
        this.DATE = DATE;
    }

    public String getFECHA() {
        return FECHA;
    }

    public void setFECHA(String FECHA) {
        this.FECHA = FECHA;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getMAILS() {
        return MAILS;
    }

    public void setMAILS(String MAILS) {
        this.MAILS = MAILS;
    }

    public String getUSUARIO() {
        return USUARIO;
    }

    public void setUSUARIO(String USUARIO) {
        this.USUARIO = USUARIO;
    }

    public int getFIN_JORNADA() {
        return FIN_JORNADA;
    }

    public void setFIN_JORNADA(int FIN_JORNADA) {
        this.FIN_JORNADA = FIN_JORNADA;
    }

    public String valor() {
        return "Settings{" +
                "DATE=" + DATE+
                ", FECHA='" + FECHA + '\'' +
                ", URL='" + URL + '\'' +
                ", MAILS='" + MAILS + '\'' +
                ", USUARIO='" + USUARIO + '\'' +
                ", FIN_JORNADA=" + FIN_JORNADA +
                '}';
    }
}

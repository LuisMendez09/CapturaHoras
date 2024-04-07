package com.example.capturahoras.modelo.ModelAPI;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AsistrnciaPOST {
    @SerializedName("fecha")
    @Expose
    String fecha;
    @SerializedName("id_trabajador")
    @Expose
    int id_trabajador;
    @SerializedName("total_horas")
    @Expose
    float total_horas;
    @SerializedName("dispositivo")
    @Expose
    String dispositivo;
    @SerializedName("HoraInicio")
    @Expose
    String HoraInicio;
    @SerializedName("HoraFinal")
    @Expose
    String HoraFinal;
    @SerializedName("id")
    @Expose
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getId_trabajador() {
        return id_trabajador;
    }

    public void setId_trabajador(int id_trabajador) {
        this.id_trabajador = id_trabajador;
    }

    public float getTotal_horas() {
        return total_horas;
    }

    public void setTotal_horas(float total_horas) {
        this.total_horas = total_horas;
    }

    public String getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(String dispositivo) {
        this.dispositivo = dispositivo;
    }

    public String getHoraInicio() {
        return HoraInicio;
    }

    public void setHoraInicio(String horaInicio) {
        HoraInicio = horaInicio;
    }

    public String getHoraFinal() {
        return HoraFinal;
    }

    public void setHoraFinal(String horaFinal) {
        HoraFinal = horaFinal;
    }

    @Override
    public String toString() {
        return "Postasistencia{" +
                "fecha='" + fecha + '\'' +
                ", id_trabajador=" + id_trabajador +
                ", total_horas=" + total_horas +
                ", dispositivo='" + dispositivo + '\'' +
                ", HoraInicio='" + HoraInicio + '\'' +
                ", HoraFinal='" + HoraFinal + '\'' +
                '}';
    }
}

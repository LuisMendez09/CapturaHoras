package com.example.capturahoras.datos.Asistencia;

import com.example.capturahoras.datos.CRUD;
import com.example.capturahoras.modelo.Asistencia;

import java.util.ArrayList;

public interface IAsistenciaDAO extends CRUD<Asistencia> {
    String TABLE_ASISTENCIA="Asistencia";
    String FECHA ="fecha";
    String FECHA_TEXTO ="fechaTexto";
    String ID_TRABAJADOR ="idTrabajador";
    String ID_ACTIVIDAD="idActividad";
    String TOTAL_HORAS="totalHoras";
    String ID = "id";
    String DISPOSITIVO="DISPOSITIVO";
    String HORA_INICIO="hora_inicial";
    String HORA_FINAL="hora_final";
    String ENVIADO="enviado";

    ArrayList<Asistencia> getAsistenciaDia();
    Asistencia getAsistenciaTrabajadorDia(int idTrabajador);
    ArrayList<Asistencia> getAsistenciaSinEnviar();
    ArrayList<Asistencia> getAsistenciaSinEnviarSesionActiva();
}

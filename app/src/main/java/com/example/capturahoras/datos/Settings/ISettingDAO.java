package com.example.capturahoras.datos.Settings;


import com.example.capturahoras.datos.CRUD;
import com.example.capturahoras.modelo.Settings;

public interface ISettingDAO extends CRUD<Settings> {
    String TABLA_SETTINGS = "Settings";

    String SETTINGS_ID="id";
    String SETTINGS_FECHA="fecha";
    String SETTINGS_DATE="date";
    String SETTINGS_URL="url";
    String SETTINGS_MAILS ="mails";
    String SETTINGS_USUARIO ="usuario";
    String SETTINGS_JORNADA_FINALIZADA="finJornada";

    public String getUsuario();
    public boolean updateUsuario();
    public boolean updateMail();
    public boolean updateUrl();
    public boolean updateFecha();
    public boolean updateJornada();

}

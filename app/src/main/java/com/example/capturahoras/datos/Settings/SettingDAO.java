package com.example.capturahoras.datos.Settings;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;


import com.example.capturahoras.complemento.Exceptions;
import com.example.capturahoras.datos.DBHandler;
import com.example.capturahoras.modelo.Settings;

import java.util.Date;
import java.util.List;

public class SettingDAO implements ISettingDAO{
    private DBHandler db;
    private Context context;

    public  SettingDAO(Context c) {
        db = DBHandler.getInstancia(c);
        context = c;
    }

    @Override
    public List<Settings> listarActivos() {
        return null;
    }

    @Override
    public Settings leerPorId(int id) {
        String selectQuery = "SELECT * FROM " + TABLA_SETTINGS + " WHERE " + SETTINGS_ID + " = " + 1 + " ";

        try {
            SQLiteDatabase data = db.getWritableDatabase();
            Cursor cursor = data.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            int rows = cursor.getCount();

            if (cursor.moveToFirst()) {

                do {
                    Settings.FECHA = cursor.getString(1);
                    Settings.DATE = new Date(cursor.getLong(2));
                    Settings.URL = cursor.getString(3);
                    Settings.MAILS = cursor.getString(4);
                    Settings.USUARIO = cursor.getString(5);
                    Settings.FIN_JORNADA = cursor.getInt(6);

                } while (cursor.moveToNext());
            }
            // return contact list
            cursor.close();
            return null;
        }catch (SQLiteException ex){
            Exceptions.exception = ex;
            return null;
        }
    }

    @Override
    public void guardar(Settings settings) {
        int insert;

        try{
            SQLiteDatabase data = db.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(SETTINGS_ID,1);
            values.put(SETTINGS_DATE,Settings.DATE.getTime());
            values.put(SETTINGS_FECHA,Settings.FECHA);
            values.put(SETTINGS_URL,Settings.URL);
            values.put(SETTINGS_MAILS,Settings.MAILS);
            values.put(SETTINGS_JORNADA_FINALIZADA,Settings.FIN_JORNADA);

            insert = (int) data.insert(TABLA_SETTINGS, null, values);
            if(insert ==-1)
                Exceptions.exception = new Exception("Error al guardar los datos");
        }catch (SQLiteException ex){
            Exceptions.exception = ex;
        }

        return;
    }

    @Override
    public void actualizar(Settings settings) {
        try{
            SQLiteDatabase data = db.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(SETTINGS_DATE,Settings.DATE.getTime());
            values.put(SETTINGS_FECHA,Settings.FECHA);
            values.put(SETTINGS_USUARIO,Settings.USUARIO);
            values.put(SETTINGS_JORNADA_FINALIZADA,Settings.FIN_JORNADA);

            String[] args = new String []{ "1"};
            long insert = data.update(TABLA_SETTINGS,  values,SETTINGS_ID+"=?",args);
            return;
        }catch (Exception ex){
            Exceptions.exception = ex;
            return;
        }
    }

    @Override
    public void eliminar(long id) {

    }

    @Override
    public String getUsuario() {
        String selectQuery = "SELECT "+SETTINGS_USUARIO+" FROM " + TABLA_SETTINGS + " WHERE " + SETTINGS_ID + " = " + 1 + " ";

        try {
            SQLiteDatabase data = db.getWritableDatabase();
            Cursor cursor = data.rawQuery(selectQuery, null);

            int rows = cursor.getCount();

            if (cursor.moveToFirst()) {
                do {
                    Settings.USUARIO = cursor.getString(0);

                } while (cursor.moveToNext());
            }
            // return contact list
            cursor.close();
            return Settings.USUARIO;
        }catch (SQLiteException ex){
            Exceptions.exception = ex;
            return null;
        }
    }
    @Override
    public boolean updateUsuario(){
        try{
            SQLiteDatabase data = db.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(SETTINGS_USUARIO,Settings.USUARIO);

            String[] args = new String []{ "1"};
            long insert = data.update(TABLA_SETTINGS,  values,SETTINGS_ID+"=?",args);
            return true;
        }catch (SQLiteException ex){
            Exceptions.exception = ex;
            return false;
        }
    }

    @Override
    public boolean updateMail(){
        try{
            SQLiteDatabase data = db.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(SETTINGS_MAILS,Settings.MAILS);

            String[] args = new String []{ "1"};
            long insert = data.update(TABLA_SETTINGS,  values,SETTINGS_ID+"=?",args);
            return true;
        }catch (SQLiteException ex){
            Exceptions.exception = ex;
            return false;
        }
    }

    @Override
    public boolean updateUrl(){
        try{
            SQLiteDatabase data = db.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(SETTINGS_URL,Settings.URL);

            String[] args = new String []{ "1"};
            long insert = data.update(TABLA_SETTINGS,  values,SETTINGS_ID+"=?",args);
            return true;
        }catch (SQLiteException ex){
            Exceptions.exception = ex;
            return false;
        }
    }

    @Override
    public boolean updateFecha(){
        try{
            SQLiteDatabase data = db.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(SETTINGS_DATE,Settings.DATE.getTime());
            values.put(SETTINGS_FECHA,Settings.FECHA);

            String[] args = new String []{ "1"};
            long insert = data.update(TABLA_SETTINGS,  values,SETTINGS_ID+"=?",args);
            return true;
        }catch (Exception ex){
            Exceptions.exception = ex;
            return false;
        }
    }

    @Override
    public boolean updateJornada() {
        try{
            SQLiteDatabase data = db.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(SETTINGS_JORNADA_FINALIZADA,Settings.FIN_JORNADA);

            String[] args = new String []{ "1"};
            long insert = data.update(TABLA_SETTINGS,  values,SETTINGS_ID+"=?",args);
            return true;
        }catch (Exception ex){
            Exceptions.exception = ex;
            return false;
        }
    }
}

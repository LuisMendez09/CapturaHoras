package com.example.capturahoras.datos.Settings;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;


import com.example.capturahoras.complemento.Complementos;
import com.example.capturahoras.complemento.Exceptions;
import com.example.capturahoras.complemento.FileLog;
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
        Settings settings=Settings.getInstanacia();
        try {
            SQLiteDatabase data = db.getWritableDatabase();
            Cursor cursor = data.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            int rows = cursor.getCount();

            if (cursor.moveToFirst()) {
                do {
                    settings.setFECHA(cursor.getString(1));
                    settings.setDATE(new Date(cursor.getLong(2)));
                    settings.setURL(cursor.getString(3));
                    settings.setMAILS(cursor.getString(4));
                    settings.setUSUARIO(cursor.getString(5));
                    settings.setFIN_JORNADA(cursor.getInt(6));

                } while (cursor.moveToNext());
            }
            // return contact list
            cursor.close();
            FileLog.e(Complementos.TAG_SETTINGS, "setting "+settings.valor());
            return settings;
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
            values.put(SETTINGS_DATE,settings.getDATE().getTime());
            values.put(SETTINGS_FECHA,settings.getFECHA());
            values.put(SETTINGS_URL,settings.getURL());
            values.put(SETTINGS_MAILS,settings.getMAILS());
            values.put(SETTINGS_JORNADA_FINALIZADA,settings.getFIN_JORNADA());

            insert = (int) data.insert(TABLA_SETTINGS, null, values);
            if(insert ==-1){
                Exceptions.exception = new Exception("Error al guardar los datos");
                FileLog.e(Complementos.TAG_SETTINGS, "setting Error al guardar los datos");
            }
        }catch (SQLiteException ex){
            Exceptions.exception = ex;
            FileLog.e(Complementos.TAG_SETTINGS, "setting "+ex.getMessage());
        }
        FileLog.e(Complementos.TAG_SETTINGS, "setting "+settings.valor());
        return;
    }

    @Override
    public void actualizar(Settings settings) {
        try{
            SQLiteDatabase data = db.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(SETTINGS_DATE,settings.getDATE().getTime());
            values.put(SETTINGS_FECHA,settings.getFECHA());
            values.put(SETTINGS_USUARIO,settings.getUSUARIO());
            values.put(SETTINGS_JORNADA_FINALIZADA,settings.getFIN_JORNADA());

            String[] args = new String []{ "1"};
            long insert = data.update(TABLA_SETTINGS,  values,SETTINGS_ID+"=?",args);

            FileLog.e(Complementos.TAG_SETTINGS, "setting "+settings.valor());
            return;
        }catch (Exception ex){
            Exceptions.exception = ex;
            FileLog.e(Complementos.TAG_SETTINGS, "setting "+ex.getMessage());
            return;
        }
    }

    @Override
    public void eliminar(long id) {

    }

    @Override
    public String getUsuario() {
        String selectQuery = "SELECT "+SETTINGS_USUARIO+" FROM " + TABLA_SETTINGS + " WHERE " + SETTINGS_ID + " = " + 1 + " ";
        Settings settings = Settings.getInstanacia();
        try {
            SQLiteDatabase data = db.getWritableDatabase();
            Cursor cursor = data.rawQuery(selectQuery, null);

            int rows = cursor.getCount();

            if (cursor.moveToFirst()) {
                do {
                    settings.setUSUARIO( cursor.getString(0));

                } while (cursor.moveToNext());
            }
            // return contact list
            cursor.close();
            FileLog.e(Complementos.TAG_SETTINGS, "setting get usuario "+settings.getUSUARIO());
            return settings.getUSUARIO();
        }catch (SQLiteException ex){
            Exceptions.exception = ex;
            FileLog.e(Complementos.TAG_SETTINGS, "setting "+ex.getMessage());
            return null;
        }
    }
    @Override
    public boolean updateUsuario(String usuario){
        try{

            SQLiteDatabase data = db.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(SETTINGS_USUARIO,usuario);

            String[] args = new String []{ "1"};
            long insert = data.update(TABLA_SETTINGS,  values,SETTINGS_ID+"=?",args);
            FileLog.e(Complementos.TAG_SETTINGS, "setting update usuario "+usuario);
            return true;
        }catch (SQLiteException ex){
            Exceptions.exception = ex;
            FileLog.e(Complementos.TAG_SETTINGS, "setting "+ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateMail(String mail){
        try{
            SQLiteDatabase data = db.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(SETTINGS_MAILS,mail);

            String[] args = new String []{ "1"};
            long insert = data.update(TABLA_SETTINGS,  values,SETTINGS_ID+"=?",args);
            FileLog.e(Complementos.TAG_SETTINGS, "setting update MAIL "+mail);
            return true;
        }catch (SQLiteException ex){
            Exceptions.exception = ex;
            FileLog.e(Complementos.TAG_SETTINGS, "setting "+ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateUrl(String url){
        try{
            SQLiteDatabase data = db.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(SETTINGS_URL,url);

            String[] args = new String []{ "1"};
            long insert = data.update(TABLA_SETTINGS,  values,SETTINGS_ID+"=?",args);
            FileLog.e(Complementos.TAG_SETTINGS, "setting update URL "+url);
            return true;
        }catch (SQLiteException ex){
            Exceptions.exception = ex;
            FileLog.e(Complementos.TAG_SETTINGS, "setting "+ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateFecha(Date date,String fecha){
        try{
            SQLiteDatabase data = db.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(SETTINGS_DATE,date.getTime());
            values.put(SETTINGS_FECHA,fecha);

            String[] args = new String []{ "1"};
            long insert = data.update(TABLA_SETTINGS,  values,SETTINGS_ID+"=?",args);
            FileLog.e(Complementos.TAG_SETTINGS, "setting update fecha "+fecha+ " -- "+date.getTime());
            return true;
        }catch (Exception ex){
            Exceptions.exception = ex;
            FileLog.e(Complementos.TAG_SETTINGS, "setting "+ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateJornada(int finJornada) {
        try{
            SQLiteDatabase data = db.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(SETTINGS_JORNADA_FINALIZADA,finJornada);

            String[] args = new String []{ "1"};
            long insert = data.update(TABLA_SETTINGS,  values,SETTINGS_ID+"=?",args);
            FileLog.e(Complementos.TAG_SETTINGS, "setting update JORNADA "+finJornada);
            return true;
        }catch (Exception ex){
            Exceptions.exception = ex;
            FileLog.e(Complementos.TAG_SETTINGS, "setting "+ex.getMessage());
            return false;
        }
    }
}

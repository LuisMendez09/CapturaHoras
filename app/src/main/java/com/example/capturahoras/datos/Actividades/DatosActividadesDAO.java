package com.example.capturahoras.datos.Actividades;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.capturahoras.complemento.Complementos;
import com.example.capturahoras.complemento.FileLog;
import com.example.capturahoras.datos.DBHandler;
import com.example.capturahoras.datos.Trabajadores.ITrabajadoresDAO;
import com.example.capturahoras.modelo.Actividades;

import java.util.ArrayList;
import java.util.List;

public class DatosActividadesDAO implements IActividadesDAO {

    private DBHandler db;

    public  DatosActividadesDAO(Context c) {
        db = DBHandler.getInstancia(c);
    }

    @Override
    public List<Actividades> listarActivos() {
        ArrayList<Actividades> actividades = new ArrayList<Actividades>();
        Actividades actividad = null;
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_ACTIVIDADES + " ORDER BY " + DESCRIPCION ;
        SQLiteDatabase data = db.getWritableDatabase();
        Cursor cursor = data.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                actividad = new Actividades();
                actividad.setClave(cursor.getInt(0));
                actividad.setDescripcion(cursor.getString(1));

                actividades.add(actividad);
            } while (cursor.moveToNext());
        }
        // return contact list

        cursor.close();
        data.close();
        FileLog.v(Complementos.TAG_BDHANDLER,"Total Trabajadores "+actividades.size());
        return actividades;
    }

    @Override
    public Actividades leerPorId(int id) {

        Actividades actividades = null;

        String selectQuery = "SELECT * FROM " + TABLE_ACTIVIDADES +" WHERE "+CLAVE+" = '"+id+"' ";
        SQLiteDatabase data = db.getWritableDatabase();
        Cursor cursor = data.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                actividades = new Actividades();
                actividades.setClave(cursor.getInt(0));
                actividades.setDescripcion(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        // return contact list

        cursor.close();
        data.close();
        if(actividades != null)
            FileLog.v(Complementos.TAG_BDHANDLER,"Trabajador "+actividades.toString());

        return actividades;
    }

    @Override
    public Actividades leerPorDescripcion(String descripcion) {

        Actividades actividades = null;

        String selectQuery = "SELECT * FROM " + TABLE_ACTIVIDADES +" WHERE "+DESCRIPCION+" = '"+descripcion+"' ";
        SQLiteDatabase data = db.getWritableDatabase();
        Cursor cursor = data.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                actividades = new Actividades();
                actividades.setClave(cursor.getInt(0));
                actividades.setDescripcion(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        // return contact list

        cursor.close();
        data.close();
        if(actividades != null)
            FileLog.v(Complementos.TAG_BDHANDLER,"Trabajador "+actividades.toString());

        return actividades;
    }

    @Override
    public void guardar(Actividades o) {
        FileLog.v(Complementos.TAG_BDHANDLER,"AÑADIR ACTIVIDADES "+o.toString());
        SQLiteDatabase data = db.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(CLAVE ,o.getClave());
        values.put(DESCRIPCION ,o.getDescripcion());

        Long insert = data.insert(TABLE_ACTIVIDADES, null, values);

        if(insert ==-1)
            FileLog.v(Complementos.TAG_BDHANDLER,"ERROR DE INSERSION ACTIVIDADES ");
        db.close(); // Closing database connection

        return;
    }

    @Override
    public void actualizar(Actividades o) {
        FileLog.v(Complementos.TAG_BDHANDLER,"ACTUALIZAR ACTIVIDADES "+o.toString());
        int i = -1;
        try{
            SQLiteDatabase data = db.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(DESCRIPCION ,o.getDescripcion());

            i = data.update(TABLE_ACTIVIDADES, values, CLAVE + " = ?",
                    new String[]{String.valueOf(o.getClave())});
            data.close();
        }catch (Exception e){
            FileLog.v(Complementos.TAG_BDHANDLER,"error "+e.getMessage());
        }
        return;
    }

    @Override
    public void eliminar(long id) {

    }

    public void reiniciarTabla(){
        SQLiteDatabase data = db.getWritableDatabase();
        data.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVIDADES);

        String CREATE_TABLE = "CREATE TABLE " + TABLE_ACTIVIDADES + "("
                + CLAVE + " INTEGER PRIMARY KEY,"
                + DESCRIPCION+ " TEXT"+ ")";
        data.execSQL(CREATE_TABLE);
    }
}

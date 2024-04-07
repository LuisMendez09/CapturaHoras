package com.example.capturahoras.datos.Etapas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.capturahoras.complemento.Complementos;
import com.example.capturahoras.complemento.FileLog;
import com.example.capturahoras.datos.DBHandler;
import com.example.capturahoras.modelo.Etapas;

import java.util.ArrayList;
import java.util.List;

public class DatosEtapaDAO implements IEtapasDAO{
    private DBHandler db;

    public DatosEtapaDAO(Context c) {
        db= DBHandler.getInstancia(c);
    }

    @Override
    public List<Etapas> listarActivos() {
        ArrayList<Etapas> etapas = new ArrayList<>();
        Etapas etapa = null;
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_ETAPAS ;
        SQLiteDatabase data = db.getWritableDatabase();
        Cursor cursor = data.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                etapa = new Etapas();
                etapa.setClave(cursor.getInt(0));
                etapa.setDescripcion(cursor.getString(1));

                etapas.add(etapa);
            } while (cursor.moveToNext());
        }
        // return contact list

        cursor.close();
        data.close();
        FileLog.v(Complementos.TAG_BDHANDLER,"Total Campo "+etapas.size());
        return etapas;
    }

    @Override
    public Etapas leerPorId(int id) {
        Etapas etapa = null;

        String selectQuery = "SELECT * FROM " + TABLE_ETAPAS +" WHERE "+CLAVE+" = '"+id+"' ";
        SQLiteDatabase data = db.getWritableDatabase();
        Cursor cursor = data.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                etapa = new Etapas();
                etapa.setClave(cursor.getInt(0));
                etapa.setDescripcion(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        // return contact list

        cursor.close();
        data.close();
        if(etapa != null)
            FileLog.v(Complementos.TAG_BDHANDLER,"Campo "+etapa.toString());

        return etapa;
    }

    @Override
    public void guardar(Etapas o) {
        FileLog.v(Complementos.TAG_BDHANDLER,"AÑADIR Etapa "+o.toString());
        SQLiteDatabase data = db.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(CLAVE ,o.getClave());
        values.put(DESCRIPCION ,o.getDescripcion());

        Long insert = data.insert(TABLE_ETAPAS, null, values);

        if(insert ==-1)
            FileLog.v(Complementos.TAG_BDHANDLER,"ERROR DE INSERSION Etapa ");
        db.close(); // Closing database connection

        return;
    }

    @Override
    public void actualizar(Etapas o) {
        FileLog.v(Complementos.TAG_BDHANDLER,"ACTUALIZAR CAMPO "+o.toString());
        int i = -1;
        try{
            SQLiteDatabase data = db.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(DESCRIPCION ,o.getDescripcion());

            i = data.update(TABLE_ETAPAS, values, CLAVE + " = ?",
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

    @Override
    public void reiniciarTabla() {
        FileLog.v(Complementos.TAG_BDHANDLER,"INICIA CREACION DE LAS TABLAS PRODUCTOS");
        SQLiteDatabase data = db.getWritableDatabase();
        data.execSQL("DROP TABLE IF EXISTS " + TABLE_ETAPAS);


        String CREATE_TABLE = "CREATE TABLE " + TABLE_ETAPAS + "("
                + CLAVE + " INTEGER PRIMARY KEY,"
                + DESCRIPCION+ " TEXT"+ ")";

        data.execSQL(CREATE_TABLE);
    }

    @Override
    public Etapas leerPorDescripcion(String descripcion) {
        Etapas etapa = null;

        String selectQuery = "SELECT * FROM " + TABLE_ETAPAS +" WHERE "+DESCRIPCION+" = '"+descripcion+"' ";
        SQLiteDatabase data = db.getWritableDatabase();
        Cursor cursor = data.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                etapa = new Etapas();
                etapa.setClave(cursor.getInt(0));
                etapa.setDescripcion(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        // return contact list

        cursor.close();
        data.close();
        if(etapa != null)
            FileLog.v(Complementos.TAG_BDHANDLER,"Campo "+etapa.toString());

        return etapa;
    }
}

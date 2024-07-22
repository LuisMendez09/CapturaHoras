package com.example.capturahoras.datos.tablaProrrate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.capturahoras.complemento.Complementos;
import com.example.capturahoras.complemento.FileLog;
import com.example.capturahoras.datos.DBHandler;
import com.example.capturahoras.modelo.TablasProrrateo;

import java.util.ArrayList;
import java.util.List;

public class TablaProrrateoDAO implements ITablaProrrateoDAO{

    private DBHandler db;
    private static ArrayList<TablasProrrateo> tps = new ArrayList<TablasProrrateo>();

    public TablaProrrateoDAO(Context c) {
        db= DBHandler.getInstancia(c);
    }

    @Override
    public List<TablasProrrateo> listarActivos() {
        if(tps.size()>0)
            return tps;

        TablasProrrateo tp = null;
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_TABLAPRORRATEO ;
        SQLiteDatabase data = db.getWritableDatabase();
        Cursor cursor = data.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                tp = new TablasProrrateo();
                tp.setId(cursor.getString(0));
                tp.setDescripcion(cursor.getString(1));

                tps.add(tp);
            } while (cursor.moveToNext());
        }
        // return contact list

        cursor.close();
        data.close();
        FileLog.v(Complementos.TAG_BDHANDLER,"Total Tabla prorrateo "+tps.size());
        return tps;
    }

    @Override
    public int ContarRegistros() {
        if(tps.size()>0)
            return tps.size();

        String selectQuery = "SELECT * FROM " + TABLE_TABLAPRORRATEO ;
        int total=0;
        SQLiteDatabase data = db.getWritableDatabase();
        Cursor cursor = data.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        total = cursor.getCount();

        cursor.close();
        data.close();
        FileLog.v(Complementos.TAG_BDHANDLER,"Total Tabla prorrateo "+total);
        return total;
    }

    @Override
    public TablasProrrateo leerPorId(int id) {
        if(tps.size()>0) {
            for (TablasProrrateo tp :tps) {
                if(tp.getId()==String.valueOf(id))
                    return tp;
            }
        }

        TablasProrrateo tp = null;

        String selectQuery = "SELECT * FROM " + TABLE_TABLAPRORRATEO +" WHERE "+CLAVE+" = '"+id+"' ";
        SQLiteDatabase data = db.getWritableDatabase();
        Cursor cursor = data.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                tp = new TablasProrrateo();
                tp.setId(cursor.getString(0));
                tp.setDescripcion(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        // return contact list

        cursor.close();
        data.close();
        if(tp != null)
            FileLog.v(Complementos.TAG_BDHANDLER,"Tabla prorrateo "+tp.toString());

        return tp;
    }

    @Override
    public TablasProrrateo leerPorDescripcion(String descripcion) {
        if(tps.size()>0) {
            for (TablasProrrateo tp :tps) {
                if(tp.getDescripcion()==descripcion)
                    return tp;
            }
        }

        TablasProrrateo tp = null;

        String selectQuery = "SELECT * FROM " + TABLE_TABLAPRORRATEO +" WHERE "+DESCRIPCION+" = '"+descripcion+"' ";
        SQLiteDatabase data = db.getWritableDatabase();
        Cursor cursor = data.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                tp = new TablasProrrateo();
                tp.setId(cursor.getString(0));
                tp.setDescripcion(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        // return contact list

        cursor.close();
        data.close();
        if(tp != null)
            FileLog.v(Complementos.TAG_BDHANDLER,"Tabla prorrateo "+tp.toString());

        return tp;
    }

    @Override
    public void guardar(TablasProrrateo o) {
        FileLog.v(Complementos.TAG_BDHANDLER,"AÑADIR TABLA "+o.toString());
        SQLiteDatabase data = db.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(CLAVE ,o.getId());
        values.put(DESCRIPCION ,o.getDescripcion());

        Long insert = data.insert(TABLE_TABLAPRORRATEO, null, values);

        if(insert ==-1)
            FileLog.v(Complementos.TAG_BDHANDLER,"ERROR DE INSERSION Tabla prorrateo ");
        else
            tps.add(o);

        db.close(); // Closing database connection

        return;
    }

    @Override
    public void actualizar(TablasProrrateo TablasProrrateo) {

    }

    @Override
    public void eliminar(long id) {

    }

    @Override
    public void reiniciarTabla() {
        FileLog.v(Complementos.TAG_BDHANDLER,"INICIA CREACION DE LAS TABLAS PRORRATEO");
        SQLiteDatabase data = db.getWritableDatabase();
        data.execSQL("DROP TABLE IF EXISTS " + TABLE_TABLAPRORRATEO);

        String CREATE_TABLE = "CREATE TABLE " + TABLE_TABLAPRORRATEO + "("
                + CLAVE + " TEXT PRIMARY KEY,"
                + DESCRIPCION+ " TEXT"+ ")";

        data.execSQL(CREATE_TABLE);
    }


}

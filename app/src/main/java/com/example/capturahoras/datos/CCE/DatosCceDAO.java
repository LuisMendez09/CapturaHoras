package com.example.capturahoras.datos.CCE;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.capturahoras.complemento.Complementos;
import com.example.capturahoras.complemento.FileLog;
import com.example.capturahoras.datos.DBHandler;
import com.example.capturahoras.modelo.CCE;

import java.util.ArrayList;
import java.util.List;

public class DatosCceDAO implements ICceDAO{
    private DBHandler db;

    public DatosCceDAO(Context c) {
        db= DBHandler.getInstancia(c);
    }


    @Override
    public void reiniciarTabla() {
        FileLog.v(Complementos.TAG_BDHANDLER,"INICIA CREACION DE LAS TABLAS CCE");
        SQLiteDatabase data = db.getWritableDatabase();
        data.execSQL("DROP TABLE IF EXISTS " + TABLE_CCE);


        String CREATE_TABLE = "CREATE TABLE " + TABLE_CCE + "("
                + CLAVE + " INTEGER PRIMARY KEY,"
                + DESCRIPCION+ " TEXT"+ ")";

        data.execSQL(CREATE_TABLE);
    }

    @Override
    public CCE leerPorDescripcion(String descripcion) {
        CCE ccee = null;

        String selectQuery = "SELECT * FROM " + TABLE_CCE +" WHERE "+DESCRIPCION+" = '"+descripcion+"' ";
        SQLiteDatabase data = db.getWritableDatabase();
        Cursor cursor = data.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ccee = new CCE();
                ccee.setClave(cursor.getInt(0));
                ccee.setDescripcion(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        // return contact list

        cursor.close();
        data.close();
        if(ccee != null)
            FileLog.v(Complementos.TAG_BDHANDLER,"CCE "+ccee.toString());

        return ccee;
    }

    @Override
    public List<CCE> listarActivos() {
        ArrayList<CCE> cces = new ArrayList<>();
        CCE cce = null;
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_CCE ;
        SQLiteDatabase data = db.getWritableDatabase();
        Cursor cursor = data.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                cce = new CCE();
                cce.setClave(cursor.getInt(0));
                cce.setDescripcion(cursor.getString(1));

                cces.add(cce);
            } while (cursor.moveToNext());
        }
        // return contact list

        cursor.close();
        data.close();
        FileLog.v(Complementos.TAG_BDHANDLER,"Total CCE "+cces.size());
        return cces;
    }

    @Override
    public int ContarRegistros() {

        String selectQuery = "SELECT * FROM " + TABLE_CCE ;
        int total=0;
        SQLiteDatabase data = db.getWritableDatabase();
        Cursor cursor = data.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        total = cursor.getCount();

        cursor.close();
        data.close();
        FileLog.v(Complementos.TAG_BDHANDLER,"Total CCE "+total);
        return total;
    }

    @Override
    public CCE leerPorId(int id) {
        CCE ccee = null;

        String selectQuery = "SELECT * FROM " + TABLE_CCE +" WHERE "+CLAVE+" = '"+id+"' ";
        SQLiteDatabase data = db.getWritableDatabase();
        Cursor cursor = data.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ccee = new CCE();
                ccee.setClave(cursor.getInt(0));
                ccee.setDescripcion(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        // return contact list

        cursor.close();
        data.close();
        if(ccee != null)
            FileLog.v(Complementos.TAG_BDHANDLER,"CCE "+ccee.toString());

        return ccee;
    }

    @Override
    public void guardar(CCE o) {
        FileLog.v(Complementos.TAG_BDHANDLER,"AÑADIR CCE "+o.toString());
        SQLiteDatabase data = db.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(CLAVE ,o.getClave());
        values.put(DESCRIPCION ,o.getDescripcion());

        Long insert = data.insert(TABLE_CCE, null, values);

        if(insert ==-1)
            FileLog.v(Complementos.TAG_BDHANDLER,"ERROR DE INSERSION CCE ");
        db.close(); // Closing database connection

        return;
    }

    @Override
    public void actualizar(CCE o) {
        FileLog.v(Complementos.TAG_BDHANDLER,"ACTUALIZAR CCE "+o.toString());
        int i = -1;
        try{
            SQLiteDatabase data = db.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(DESCRIPCION ,o.getDescripcion());

            i = data.update(TABLE_CCE, values, CLAVE + " = ?",
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
}

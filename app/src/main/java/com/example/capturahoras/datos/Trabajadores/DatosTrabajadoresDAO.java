package com.example.capturahoras.datos.Trabajadores;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.capturahoras.complemento.Complementos;
import com.example.capturahoras.complemento.FileLog;
import com.example.capturahoras.datos.DBHandler;
import com.example.capturahoras.modelo.Trabajadores;

import java.util.ArrayList;
import java.util.List;

public class DatosTrabajadoresDAO implements ITrabajadoresDAO {
    private DBHandler db;

    public DatosTrabajadoresDAO(Context context) {
        db= DBHandler.getInstancia(context);
    }

    @Override
    public List<Trabajadores> listarActivos() {
        ArrayList<Trabajadores> trabajadores = new ArrayList<Trabajadores>();
        Trabajadores trabajador = null;
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_TRABAJADORES + " WHERE "+STATUS+"=1" ;
        SQLiteDatabase data = db.getWritableDatabase();
        Cursor cursor = data.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                trabajador = new Trabajadores();
                trabajador.setNumero(cursor.getInt(0));
                trabajador.setNombre(cursor.getString(1));
                trabajador.setEstatusInt(cursor.getInt(2));

                trabajadores.add(trabajador);
            } while (cursor.moveToNext());
        }
        // return contact list

        cursor.close();
        data.close();
        FileLog.v(Complementos.TAG_BDHANDLER,"Total Trabajadores "+trabajadores.size());
        return trabajadores;
    }

    @Override
    public Trabajadores leerPorId(int id) {
        Trabajadores trabajador = null;

        String selectQuery = "SELECT * FROM " + TABLE_TRABAJADORES +" WHERE "+CLAVE+" = '"+id+"' ";
        SQLiteDatabase data = db.getWritableDatabase();
        Cursor cursor = data.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                trabajador = new Trabajadores();
                trabajador.setNumero(cursor.getInt(0));
                trabajador.setNombre(cursor.getString(1));
                trabajador.setEstatusInt(cursor.getInt(2));
            } while (cursor.moveToNext());
        }
        // return contact list

        cursor.close();
        data.close();
        if(trabajador != null)
            FileLog.v(Complementos.TAG_BDHANDLER,"Trabajador "+trabajador.toString());

        return trabajador;
    }

    @Override
    public void guardar(Trabajadores o) {
        FileLog.v(Complementos.TAG_BDHANDLER,"AÑADIR TRABAJADOR "+o.toString());
        SQLiteDatabase data = db.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(CLAVE ,o.getNumero());
        values.put(NOMBRE ,o.getNombre());
        values.put(STATUS ,o.getEstatusInt());

        Long insert = data.insert(TABLE_TRABAJADORES, null, values);

        if(insert ==-1)
            FileLog.v(Complementos.TAG_BDHANDLER,"ERROR DE INSERSION TRABAJADOR ");
        db.close(); // Closing database connection

        return;
    }

    @Override
    public void actualizar(Trabajadores o) {
        FileLog.v(Complementos.TAG_BDHANDLER,"ACTUALIZAR TRABAJADORES "+o.toString());
        int i = -1;
        try{
            SQLiteDatabase data = db.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(NOMBRE ,o.getNombre());
            values.put(STATUS ,o.getEstatusInt());

            i = data.update(TABLE_TRABAJADORES, values, CLAVE + " = ?",
                    new String[]{String.valueOf(o.getNumero())});
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
        data.execSQL("DROP TABLE IF EXISTS " + TABLE_TRABAJADORES);

        String CREATE_TABLE = "CREATE TABLE " + TABLE_TRABAJADORES + "("
                + CLAVE + " INTEGER PRIMARY KEY,"
                + NOMBRE+ " TEXT,"
                + STATUS+ " INTEGER"+ ")";
        data.execSQL(CREATE_TABLE);
    }


}

package com.example.capturahoras.datos.Productos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.capturahoras.complemento.Complementos;
import com.example.capturahoras.complemento.FileLog;
import com.example.capturahoras.datos.DBHandler;
import com.example.capturahoras.modelo.Campos;
import com.example.capturahoras.modelo.Productos;

import java.util.ArrayList;
import java.util.List;

public class DatosProductoDAO implements IProductosDAO{
    private DBHandler db;

    public DatosProductoDAO(Context c) {
        db= DBHandler.getInstancia(c);
    }

    @Override
    public List<Productos> listarActivos() {
        ArrayList<Productos> productos = new ArrayList<Productos>();
        Productos producto = null;
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_PRODUCTOS ;
        SQLiteDatabase data = db.getWritableDatabase();
        Cursor cursor = data.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                producto = new Productos();
                producto.setClave(cursor.getInt(0));
                producto.setDescripcion(cursor.getString(1));

                productos.add(producto);
            } while (cursor.moveToNext());
        }
        // return contact list

        cursor.close();
        data.close();
        FileLog.v(Complementos.TAG_BDHANDLER,"Total Productos "+productos.size());
        return productos;
    }

    @Override
    public int ContarRegistros() {

        String selectQuery = "SELECT * FROM " + TABLE_PRODUCTOS;
        int total=0;
        SQLiteDatabase data = db.getWritableDatabase();
        Cursor cursor = data.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        total = cursor.getCount();

        cursor.close();
        data.close();
        FileLog.v(Complementos.TAG_BDHANDLER,"Total Productos "+total);
        return total;
    }

    @Override
    public Productos leerPorId(int id) {
        Productos producto = null;

        String selectQuery = "SELECT * FROM " + TABLE_PRODUCTOS +" WHERE "+CLAVE+" = '"+id+"' ";
        SQLiteDatabase data = db.getWritableDatabase();
        Cursor cursor = data.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                producto = new Productos();
                producto.setClave(cursor.getInt(0));
                producto.setDescripcion(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        // return contact list

        cursor.close();
        data.close();
        if(producto != null)
            FileLog.v(Complementos.TAG_BDHANDLER,"Producto "+producto.toString());

        return producto;
    }

    @Override
    public void guardar(Productos o) {
        FileLog.v(Complementos.TAG_BDHANDLER,"AÑADIR Producto "+o.toString());
        SQLiteDatabase data = db.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(CLAVE ,o.getClave());
        values.put(DESCRIPCION ,o.getDescripcion());

        Long insert = data.insert(TABLE_PRODUCTOS, null, values);

        if(insert ==-1)
            FileLog.v(Complementos.TAG_BDHANDLER,"ERROR DE INSERSION Producto ");
        db.close(); // Closing database connection

        return;
    }

    @Override
    public void actualizar(Productos o) {
        FileLog.v(Complementos.TAG_BDHANDLER,"ACTUALIZAR Producto "+o.toString());
        int i = -1;
        try{
            SQLiteDatabase data = db.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(DESCRIPCION ,o.getDescripcion());

            i = data.update(TABLE_PRODUCTOS, values, CLAVE + " = ?",
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
        data.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTOS);


        String CREATE_TABLE = "CREATE TABLE " + TABLE_PRODUCTOS + "("
                + CLAVE + " INTEGER PRIMARY KEY,"
                + DESCRIPCION+ " TEXT"+ ")";

        data.execSQL(CREATE_TABLE);
    }

    @Override
    public Productos leerPorDescripcion(String descripcion) {
        Productos producto = null;

        String selectQuery = "SELECT * FROM " + TABLE_PRODUCTOS +" WHERE "+DESCRIPCION+" = '"+descripcion+"' ";
        SQLiteDatabase data = db.getWritableDatabase();
        Cursor cursor = data.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                producto = new Productos();
                producto.setClave(cursor.getInt(0));
                producto.setDescripcion(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        // return contact list

        cursor.close();
        data.close();
        if(producto != null)
            FileLog.v(Complementos.TAG_BDHANDLER,"Producto "+producto.toString());

        return producto;
    }
}

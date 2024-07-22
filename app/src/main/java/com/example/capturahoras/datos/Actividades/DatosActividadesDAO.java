package com.example.capturahoras.datos.Actividades;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;


import androidx.annotation.RequiresApi;

import com.example.capturahoras.complemento.Complementos;
import com.example.capturahoras.complemento.FileLog;
import com.example.capturahoras.datos.DBHandler;
import com.example.capturahoras.datos.Trabajadores.ITrabajadoresDAO;
import com.example.capturahoras.modelo.Actividades;

import java.util.ArrayList;
import java.util.List;

public class DatosActividadesDAO implements IActividadesDAO {

    private DBHandler db;
    private static ArrayList<Actividades> actividades = new ArrayList<Actividades>();
    public  DatosActividadesDAO(Context c) {
        db = DBHandler.getInstancia(c);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public List<Actividades> listarActivos() {
        //ArrayList<Actividades> actividades = new ArrayList<Actividades>();
        if(actividades.size()>0){
            actividades.forEach(x->x.setSelect(false));
            return actividades;
        }



        Actividades actividad = null;
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_ACTIVIDADES + " ORDER BY " + DESCRIPCION ;
        SQLiteDatabase data = db.getWritableDatabase();
        Cursor cursor = data.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                actividad = new Actividades();
                actividad.setClave(cursor.getLong(0));
                actividad.setDescripcion(cursor.getString(1));
                actividad.setPrecio(cursor.getFloat(2));

                actividades.add(actividad);
            } while (cursor.moveToNext());
        }
        // return contact list

        cursor.close();
        data.close();
        FileLog.v(Complementos.TAG_BDHANDLER,"Total Actividades "+actividades.size());
        return actividades;
    }

    @Override
    public int ContarRegistros() {
        if(actividades.size()>0)
            return actividades.size();

        String selectQuery = "SELECT * FROM " + TABLE_ACTIVIDADES ;
        int total=0;
        SQLiteDatabase data = db.getWritableDatabase();
        Cursor cursor = data.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        total = cursor.getCount();

        cursor.close();
        data.close();
        FileLog.v(Complementos.TAG_BDHANDLER,"Total actividades "+total);
        return total;
    }

    @Override
    public Actividades leerPorId(int id) {
        if(actividades.size()>0){
            for (Actividades a :actividades) {
                if(a.getClave() == id)
                    return  a;
            }
        }

        Actividades actividades = null;

        String selectQuery = "SELECT * FROM " + TABLE_ACTIVIDADES +" WHERE "+CLAVE+" = '"+id+"' ";
        SQLiteDatabase data = db.getWritableDatabase();
        Cursor cursor = data.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                actividades = new Actividades();
                actividades.setClave(cursor.getLong(0));
                actividades.setDescripcion(cursor.getString(1));
                actividades.setPrecio(cursor.getFloat(2));
            } while (cursor.moveToNext());
        }
        // return contact list

        cursor.close();
        data.close();
        if(actividades != null)
            FileLog.v(Complementos.TAG_BDHANDLER,"actividad "+actividades.toString());

        return actividades;
    }

    @Override
    public Actividades leerPorDescripcion(String descripcion) {
        if(actividades.size()>0){
            for (Actividades a :actividades) {
                if(a.getDescripcion() == descripcion)
                    return  a;
            }
        }
        Actividades actividades = null;

        String selectQuery = "SELECT * FROM " + TABLE_ACTIVIDADES +" WHERE "+DESCRIPCION+" = '"+descripcion+"' ";
        SQLiteDatabase data = db.getWritableDatabase();
        Cursor cursor = data.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                actividades = new Actividades();
                actividades.setClave(cursor.getLong(0));
                actividades.setDescripcion(cursor.getString(1));
                actividades.setPrecio(cursor.getFloat(2));
            } while (cursor.moveToNext());
        }
        // return contact list

        cursor.close();
        data.close();
        if(actividades != null)
            FileLog.v(Complementos.TAG_BDHANDLER,"actividad "+actividades.toString());

        return actividades;
    }

    @Override
    public void guardar(Actividades o) {
        FileLog.v(Complementos.TAG_BDHANDLER,"AÑADIR ACTIVIDADES "+o.toString());
        SQLiteDatabase data = db.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(CLAVE ,o.getClave());
        values.put(DESCRIPCION ,o.getDescripcion());
        values.put(PRECIO ,o.getPrecio());

        Long insert = data.insert(TABLE_ACTIVIDADES, null, values);

        if(insert ==-1)
            FileLog.v(Complementos.TAG_BDHANDLER,"ERROR DE INSERSION ACTIVIDADES ");
        else{
            o.setClave(insert);
            actividades.add(o);
        }

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
            values.put(PRECIO ,o.getPrecio());

            i = data.update(TABLE_ACTIVIDADES, values, CLAVE + " = ?",
                    new String[]{String.valueOf(o.getClave())});
            if(i>0){
                for (int j=0;j<actividades.size();j++) {
                    if(actividades.get(j).getClave()==o.getClave()){
                        actividades.set(j,o);
                        break;
                    }
                }
            }

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
                + DESCRIPCION+ " TEXT,"
                + PRECIO + " REAL"+ ")";
        data.execSQL(CREATE_TABLE);
    }
}

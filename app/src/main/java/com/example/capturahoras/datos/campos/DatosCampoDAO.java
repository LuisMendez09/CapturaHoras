package com.example.capturahoras.datos.campos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.capturahoras.complemento.Complementos;
import com.example.capturahoras.complemento.FileLog;
import com.example.capturahoras.datos.CCE.DatosCceDAO;
import com.example.capturahoras.datos.DBHandler;
import com.example.capturahoras.datos.Etapas.DatosEtapaDAO;
import com.example.capturahoras.datos.Productos.DatosProductoDAO;
import com.example.capturahoras.modelo.Campos;

import java.util.ArrayList;
import java.util.List;

public class DatosCampoDAO implements ICamposDAO{
    private DBHandler db;
    private Context context;

    public DatosCampoDAO(Context c) {
        db= DBHandler.getInstancia(c);
        context=c;
    }

    @Override
    public List<Campos> listarActivos() {
        ArrayList<Campos> campos = new ArrayList<Campos>();
        Campos campo = null;
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_CAMPOS ;
        SQLiteDatabase data = db.getWritableDatabase();
        Cursor cursor = data.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                campo = new Campos();
                campo.setClave(cursor.getInt(0));
                campo.setSuperficie(cursor.getFloat(1));
                campo.setDescripcion(cursor.getString(2));
                campo.setProductoSeleccionado(new DatosProductoDAO(context).leerPorId(cursor.getInt(3)));
                campo.setCceSeleccionada(new DatosCceDAO(context).leerPorId(cursor.getInt(4)));
                campo.setEtapaSeleccionada(new DatosEtapaDAO(context).leerPorId(cursor.getInt(5)));

                campos.add(campo);
            } while (cursor.moveToNext());
        }
        // return contact list

        cursor.close();
        data.close();
        FileLog.v(Complementos.TAG_BDHANDLER,"Total Campo "+campos.size());
        return campos;
    }

    @Override
    public Campos leerPorId(int id) {
        Campos campo = null;

        String selectQuery = "SELECT * FROM " + TABLE_CAMPOS +" WHERE "+CLAVE+" = '"+id+"' ";
        SQLiteDatabase data = db.getWritableDatabase();
        Cursor cursor = data.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                campo = new Campos();
                campo.setClave(cursor.getInt(0));
                campo.setSuperficie(cursor.getFloat(1));
                campo.setDescripcion(cursor.getString(2));
                campo.setProductoSeleccionado(new DatosProductoDAO(context).leerPorId(cursor.getInt(3)));
                campo.setCceSeleccionada(new DatosCceDAO(context).leerPorId(cursor.getInt(4)));
                campo.setEtapaSeleccionada(new DatosEtapaDAO(context).leerPorId(cursor.getInt(5)));
            } while (cursor.moveToNext());
        }
        // return contact list

        cursor.close();
        data.close();
        if(campo != null)
            FileLog.v(Complementos.TAG_BDHANDLER,"Campo "+campo.toString());

        return campo;
    }

    @Override
    public Campos leerPorDescripcion(String descripcion) {
        Campos campo = null;

        String selectQuery = "SELECT * FROM " + TABLE_CAMPOS +" WHERE "+DESCRIPCION+" = '"+descripcion+"' ";
        SQLiteDatabase data = db.getWritableDatabase();
        Cursor cursor = data.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                campo = new Campos();
                campo.setClave(cursor.getInt(0));
                campo.setSuperficie(cursor.getFloat(1));
                campo.setDescripcion(cursor.getString(2));
                campo.setProductoSeleccionado(new DatosProductoDAO(context).leerPorId(cursor.getInt(3)));
                campo.setCceSeleccionada(new DatosCceDAO(context).leerPorId(cursor.getInt(4)));
                campo.setEtapaSeleccionada(new DatosEtapaDAO(context).leerPorId(cursor.getInt(5)));
            } while (cursor.moveToNext());
        }
        // return contact list

        cursor.close();
        data.close();
        if(campo != null)
            FileLog.v(Complementos.TAG_BDHANDLER,"Campo "+campo.toString());

        return campo;
    }


    @Override
    public void guardar(Campos o) {
        FileLog.v(Complementos.TAG_BDHANDLER,"AÑADIR Campo "+o.toString());
        SQLiteDatabase data = db.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(CLAVE ,o.getClave());
        values.put(DESCRIPCION ,o.getDescripcion());
        values.put(SUPERFICIE ,o.getSuperficie());
        if(o.getProductoSeleccionado()!=null)
            values.put(PRODUCTO,o.getProductoSeleccionado().getClave());
        if(o.getEtapaSeleccionada()!=null)
            values.put(ETAPA,o.getEtapaSeleccionada().getClave());
        if(o.getCceSeleccionada()!=null)
            values.put(CCE,o.getCceSeleccionada().getClave());

        Long insert = data.insert(TABLE_CAMPOS, null, values);

        if(insert ==-1)
            FileLog.v(Complementos.TAG_BDHANDLER,"ERROR DE INSERSION Campo ");
        db.close(); // Closing database connection

        return;
    }

    @Override
    public void actualizar(Campos o) {
        FileLog.v(Complementos.TAG_BDHANDLER,"ACTUALIZAR CAMPO "+o.toString());
        int i = -1;
        try{
            SQLiteDatabase data = db.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(DESCRIPCION ,o.getDescripcion());
            values.put(SUPERFICIE ,o.getSuperficie());
            values.put(PRODUCTO,o.getProductoSeleccionado().getClave());
            values.put(ETAPA,o.getEtapaSeleccionada().getClave());
            values.put(CCE,o.getCceSeleccionada().getClave());

            i = data.update(TABLE_CAMPOS, values, CLAVE + " = ?",
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
        FileLog.v(Complementos.TAG_BDHANDLER,"INICIA CREACION DE LAS TABLAS CAMPOS");
        SQLiteDatabase data = db.getWritableDatabase();
        data.execSQL("DROP TABLE IF EXISTS " + TABLE_CAMPOS);


        String CREATE_TABLE = "CREATE TABLE " + ICamposDAO.TABLE_CAMPOS + "("
                + ICamposDAO.CLAVE + " INTEGER PRIMARY KEY,"
                + ICamposDAO.SUPERFICIE+ " REAL,"
                + ICamposDAO.DESCRIPCION+ " TEXT,"
                + ICamposDAO.PRODUCTO+ " INTEGER,"
                + ICamposDAO.CCE+ " INTEGER,"
                + ICamposDAO.ETAPA+ " INTEGER"+ ")";

        data.execSQL(CREATE_TABLE);
    }


}

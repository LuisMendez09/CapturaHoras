package com.example.capturahoras.datos.Asistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.capturahoras.complemento.Complementos;
import com.example.capturahoras.complemento.FileLog;
import com.example.capturahoras.datos.Actividades.DatosActividadesDAO;
import com.example.capturahoras.datos.CamposTrabajados.CamposTrabajadosDAO;
import com.example.capturahoras.datos.DBHandler;
import com.example.capturahoras.datos.Trabajadores.DatosTrabajadoresDAO;
import com.example.capturahoras.modelo.Asistencia;
import com.example.capturahoras.modelo.Settings;

import java.util.ArrayList;
import java.util.List;

public class AsistenciaDAO implements IAsistenciaDAO{
    private DBHandler db;
    private Context context;

    public  AsistenciaDAO(Context c) {
        db = DBHandler.getInstancia(c);
        context = c;
    }

    @Override
    public List<Asistencia> listarActivos() {
        ArrayList<Asistencia> asistencias = new ArrayList<Asistencia>();
        Asistencia asistencia = null;
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_ASISTENCIA  ;
        SQLiteDatabase data = db.getWritableDatabase();
        Cursor cursor = data.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                asistencia = new Asistencia();
                asistencia.setFecha(cursor.getLong(0));
                asistencia.setTrabajador(new DatosTrabajadoresDAO(context).leerPorId(cursor.getInt(1)));
                //asistencia.setActividad(new DatosActividadesDAO(context).leerPorId(cursor.getInt(2)));
                asistencia.setTotalHoras(cursor.getFloat(2));
                asistencia.setId(cursor.getInt(3));
                asistencia.setDispositivo(cursor.getString(5));
                asistencia.setHoraInicial(cursor.getLong(6));
                asistencia.setHoraFinal(cursor.getLong(7));
                asistencia.setCamposTrabajados(new CamposTrabajadosDAO(context).listarCamposTrabajados(asistencia.getId()));
                asistencia.setEnviado(cursor.getInt(8));

                asistencias.add(asistencia);
            } while (cursor.moveToNext());
        }
        // return contact list

        cursor.close();
        data.close();
        FileLog.v(Complementos.TAG_BDHANDLER,"Total Asistencia "+asistencias.size());
        return asistencias;
    }

    @Override
    public Asistencia leerPorId(int id) {
        Asistencia asistencia = null;

        String selectQuery = "SELECT * FROM " + TABLE_ASISTENCIA +" WHERE "+ID+" = '"+id+"' ";
        SQLiteDatabase data = db.getWritableDatabase();
        Cursor cursor = data.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                asistencia = new Asistencia();
                asistencia.setFecha(cursor.getLong(0));
                asistencia.setTrabajador(new DatosTrabajadoresDAO(context).leerPorId(cursor.getInt(1)));
                //asistencia.setActividad(new DatosActividadesDAO(context).leerPorId(cursor.getInt(2)));
                asistencia.setTotalHoras(cursor.getFloat(2));
                asistencia.setId(cursor.getInt(3));
                asistencia.setDispositivo(cursor.getString(5));
                asistencia.setHoraInicial(cursor.getLong(6));
                asistencia.setHoraFinal(cursor.getLong(7));
                asistencia.setEnviado(cursor.getInt(8));


            } while (cursor.moveToNext());
        }
        // return contact list

        cursor.close();
        data.close();
        if(asistencia != null)
            FileLog.v(Complementos.TAG_BDHANDLER,"Asistencia "+asistencia.toString());

        return asistencia;
    }

    @Override
    public void guardar(Asistencia o) {
        FileLog.v(Complementos.TAG_BDHANDLER,"AÑADIR ASISTENCIA "+o.toString());
        SQLiteDatabase data = db.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(FECHA ,o.getFecha());
        values.put(ID_TRABAJADOR ,o.getTrabajador().getNumero());
        //values.put(ID_ACTIVIDAD ,o.getActividad().getClave());
        values.put(TOTAL_HORAS ,o.getTotalHoras());
        values.put(FECHA_TEXTO ,o.getFechaString());
        values.put(DISPOSITIVO ,o.getDispositivo());
        values.put(HORA_INICIO ,o.getHoraInicial());
        values.put(ENVIADO,0);
        if(o.getHoraFinal()!=0)
            values.put(HORA_FINAL ,o.getHoraFinal());

        Long insert = data.insert(TABLE_ASISTENCIA, null, values);
        o.setId(insert);
        if(insert ==-1)
            FileLog.v(Complementos.TAG_BDHANDLER,"ERROR DE INSERSION ASISTENCIA ");
        db.close(); // Closing database connection

        return;
    }

    @Override
    public void actualizar(Asistencia o) {
        FileLog.v(Complementos.TAG_BDHANDLER,"ACTUALIZAR ASISTENCIA "+o.toString());
        SQLiteDatabase data = db.getWritableDatabase();
        ContentValues values = new ContentValues();

        //values.put(ID_ACTIVIDAD ,o.getActividad().getClave());
        values.put(TOTAL_HORAS ,o.getTotalHoras());
        values.put(HORA_INICIO ,o.getHoraInicial());
        values.put(ENVIADO,o.getEnviado());
        if(o.getHoraFinal()!=0)
            values.put(HORA_FINAL ,o.getHoraFinal());

        data.update(TABLE_ASISTENCIA, values, ID + "=?",new String[]{String.valueOf(o.getId())});


        db.close(); // Closing database connection

        return;
    }

    @Override
    public void eliminar(long id) {
        FileLog.v(Complementos.TAG_BDHANDLER,"ELIMINAR ASISTENCIA "+id);
        SQLiteDatabase data = db.getWritableDatabase();
        ContentValues values = new ContentValues();


        data.delete(TABLE_ASISTENCIA, ID + "=?",new String[]{String.valueOf(id)});


        db.close(); // Closing database connection

        return;
    }

    @Override
    public ArrayList<Asistencia> getAsistenciaDia() {
        ArrayList<Asistencia> asistencias = new ArrayList<Asistencia>();
        Asistencia asistencia = null;
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_ASISTENCIA  +" where " + FECHA_TEXTO + " = '"+ Complementos.getDateActualToString() +"'"   ;
        SQLiteDatabase data = db.getWritableDatabase();
        Cursor cursor = data.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                asistencia = new Asistencia();
                asistencia.setFecha(cursor.getLong(0));
                asistencia.setTrabajador(new DatosTrabajadoresDAO(context).leerPorId(cursor.getInt(1)));
                //asistencia.setActividad(new DatosActividadesDAO(context).leerPorId(cursor.getInt(2)));
                asistencia.setTotalHoras(cursor.getFloat(2));
                asistencia.setId(cursor.getInt(3));
                asistencia.setDispositivo(cursor.getString(5));
                asistencia.setHoraInicial(cursor.getLong(6));
                asistencia.setHoraFinal(cursor.getLong(7));
                asistencia.setEnviado(cursor.getInt(8));
                asistencia.setCamposTrabajados(new CamposTrabajadosDAO(context).listarCamposTrabajados(asistencia.getId()));

                asistencias.add(asistencia);
            } while (cursor.moveToNext());
        }
        // return contact list

        cursor.close();
        data.close();
        FileLog.v(Complementos.TAG_BDHANDLER,"Total Asistencia "+asistencias.size());
        return asistencias;
    }

    @Override
    public Asistencia getAsistenciaTrabajadorDia(int idTrabajador) {
        Asistencia asistencia = null;
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_ASISTENCIA  +" where " + FECHA_TEXTO + " = '"+ Complementos.getDateActualToString() +"' AND "
                + ID_TRABAJADOR + " = "+ idTrabajador;
        SQLiteDatabase data = db.getWritableDatabase();
        Cursor cursor = data.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                asistencia = new Asistencia();
                asistencia.setFecha(cursor.getLong(0));
                asistencia.setTrabajador(new DatosTrabajadoresDAO(context).leerPorId(cursor.getInt(1)));
                asistencia.setTotalHoras(cursor.getFloat(2));
                asistencia.setId(cursor.getInt(3));
                asistencia.setDispositivo(cursor.getString(5));
                asistencia.setHoraInicial(cursor.getLong(6));
                asistencia.setHoraFinal(cursor.getLong(7));
                asistencia.setEnviado(cursor.getInt(8));
                asistencia.setCamposTrabajados(new CamposTrabajadosDAO(context).listarCamposTrabajados(asistencia.getId()));

            } while (cursor.moveToNext());
        }
        // return contact list

        cursor.close();
        data.close();
        if(asistencia != null)
            FileLog.v(Complementos.TAG_BDHANDLER,"Asistencia "+asistencia.toString());
        else
            FileLog.v(Complementos.TAG_BDHANDLER,"sin asistencia");
        return asistencia;
    }

    @Override
    public ArrayList<Asistencia> getAsistenciaSinEnviar() {
        ArrayList<Asistencia> asistencias = new ArrayList<Asistencia>();
        Asistencia asistencia = null;
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_ASISTENCIA  +" where " + ENVIADO + " = 0";
        SQLiteDatabase data = db.getWritableDatabase();
        Cursor cursor = data.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                asistencia = new Asistencia();
                asistencia.setFecha(cursor.getLong(0));
                asistencia.setTrabajador(new DatosTrabajadoresDAO(context).leerPorId(cursor.getInt(1)));
                //asistencia.setActividad(new DatosActividadesDAO(context).leerPorId(cursor.getInt(2)));
                asistencia.setTotalHoras(cursor.getFloat(2));
                asistencia.setId(cursor.getInt(3));
                asistencia.setDispositivo(cursor.getString(5));
                asistencia.setHoraInicial(cursor.getLong(6));
                asistencia.setHoraFinal(cursor.getLong(7));
                asistencia.setCamposTrabajados(new CamposTrabajadosDAO(context).listarCamposTrabajados(asistencia.getId()));
                asistencia.setEnviado(cursor.getInt(8));

                asistencias.add(asistencia);
            } while (cursor.moveToNext());
        }
        // return contact list

        cursor.close();
        data.close();
        FileLog.v(Complementos.TAG_BDHANDLER,"Total Asistencia "+asistencias.size());
        return asistencias;
    }

    @Override
    public ArrayList<Asistencia> getAsistenciaSinEnviarSesionActiva() {
        ArrayList<Asistencia> asistencias = new ArrayList<Asistencia>();
        Asistencia asistencia = null;
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_ASISTENCIA  +" where " + ENVIADO + " = 0 AND "+FECHA_TEXTO+"<>'"+ Settings.FECHA +"'";
        SQLiteDatabase data = db.getWritableDatabase();
        Cursor cursor = data.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                asistencia = new Asistencia();
                asistencia.setFecha(cursor.getLong(0));
                asistencia.setTrabajador(new DatosTrabajadoresDAO(context).leerPorId(cursor.getInt(1)));
                //asistencia.setActividad(new DatosActividadesDAO(context).leerPorId(cursor.getInt(2)));
                asistencia.setTotalHoras(cursor.getFloat(2));
                asistencia.setId(cursor.getInt(3));
                asistencia.setDispositivo(cursor.getString(5));
                asistencia.setHoraInicial(cursor.getLong(6));
                asistencia.setHoraFinal(cursor.getLong(7));
                asistencia.setCamposTrabajados(new CamposTrabajadosDAO(context).listarCamposTrabajados(asistencia.getId()));
                asistencia.setEnviado(cursor.getInt(8));

                asistencias.add(asistencia);
            } while (cursor.moveToNext());
        }
        // return contact list

        cursor.close();
        data.close();
        FileLog.v(Complementos.TAG_BDHANDLER,"Total Asistencia "+asistencias.size());
        return asistencias;
    }
}

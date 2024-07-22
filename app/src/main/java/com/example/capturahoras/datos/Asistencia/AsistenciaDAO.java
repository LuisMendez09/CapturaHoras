package com.example.capturahoras.datos.Asistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.capturahoras.complemento.Complementos;
import com.example.capturahoras.complemento.FileLog;
import com.example.capturahoras.datos.Actividades.DatosActividadesDAO;
import com.example.capturahoras.datos.Actividades.IActividadesDAO;
import com.example.capturahoras.datos.CCE.ICceDAO;
import com.example.capturahoras.datos.CamposTrabajados.CamposTrabajadosDAO;
import com.example.capturahoras.datos.CamposTrabajados.ICamposTrabajadosDAO;
import com.example.capturahoras.datos.DBHandler;
import com.example.capturahoras.datos.Etapas.IEtapasDAO;
import com.example.capturahoras.datos.Productos.IProductosDAO;
import com.example.capturahoras.datos.Trabajadores.DatosTrabajadoresDAO;
import com.example.capturahoras.datos.Trabajadores.ITrabajadoresDAO;
import com.example.capturahoras.datos.campos.ICamposDAO;
import com.example.capturahoras.datos.tablaProrrate.ITablaProrrateoDAO;
import com.example.capturahoras.modelo.Actividades;
import com.example.capturahoras.modelo.Asistencia;
import com.example.capturahoras.modelo.CCE;
import com.example.capturahoras.modelo.Campos;
import com.example.capturahoras.modelo.CamposTrabajados;
import com.example.capturahoras.modelo.Etapas;
import com.example.capturahoras.modelo.Productos;
import com.example.capturahoras.modelo.Settings;
import com.example.capturahoras.modelo.TablasProrrateo;
import com.example.capturahoras.modelo.Trabajadores;

import java.util.ArrayList;
import java.util.HashMap;
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
        String selectQuery = "SELECT * FROM " + VIEW_ASISTENCIA;//"SELECT * FROM " + TABLE_ASISTENCIA  ;
        SQLiteDatabase data = db.getWritableDatabase();
        Cursor cursor = data.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                if(asistencia==null || asistencia.getId() != cursor.getInt(3)){
                    asistencia = new Asistencia();
                    asistencias.add(asistencia);
                }
                SetAsistencia(cursor,asistencia);

                /*asistencia = new Asistencia();
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

                asistencias.add(asistencia);*/
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

        String selectQuery = "SELECT * FROM " + TABLE_ASISTENCIA + " as a "
                + " inner join "+ ITrabajadoresDAO.TABLE_TRABAJADORES +" as t on t."+ITrabajadoresDAO.CLAVE+"=a."+ID_TRABAJADOR
                +" WHERE "+ID+" = '"+id+"' ";
        SQLiteDatabase data = db.getWritableDatabase();
        Cursor cursor = data.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                asistencia = new Asistencia();
                asistencia.setFecha(cursor.getLong(0));
                asistencia.setTotalHoras(cursor.getFloat(2));
                asistencia.setId(cursor.getInt(3));
                asistencia.setDispositivo(cursor.getString(5));
                asistencia.setHoraInicial(cursor.getLong(6));
                asistencia.setHoraFinal(cursor.getLong(7));
                asistencia.setEnviado(cursor.getInt(8));

                Trabajadores t = new Trabajadores();
                t.setNumero(cursor.getInt(9));
                t.setNombre(cursor.getString(10));
                t.setEstatusInt(cursor.getInt(11));
                asistencia.setTrabajador(t);
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

        String selectQuery = "SELECT * FROM " + VIEW_ASISTENCIA +
                " where " + FECHA_TEXTO + " = '"+ Complementos.getDateActualToString() +"'"   ;
        SQLiteDatabase data = db.getWritableDatabase();
        Cursor cursor = data.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                if(asistencia==null || asistencia.getId() != cursor.getInt(3)){
                    asistencia = new Asistencia();
                    asistencias.add(asistencia);
                }
                SetAsistencia(cursor,asistencia);

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
        String selectQuery ="SELECT * FROM " + VIEW_ASISTENCIA
                //"SELECT * FROM " + TABLE_ASISTENCIA +" as a "
                //+ " inner join "+ ITrabajadoresDAO.TABLE_TRABAJADORES +" as t on t."+ITrabajadoresDAO.CLAVE+"=a."+ID_TRABAJADOR
                +" where " + FECHA_TEXTO + " = '"+ Complementos.getDateActualToString() +"' AND "+ ID_TRABAJADOR + " = "+ idTrabajador;
        SQLiteDatabase data = db.getWritableDatabase();
        Cursor cursor = data.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                if(asistencia==null || asistencia.getId() != cursor.getInt(3)){
                    asistencia = new Asistencia();
                }
                SetAsistencia(cursor,asistencia);

                /*Trabajadores t = new Trabajadores();
                t.setNumero(cursor.getInt(8));
                t.setNombre(cursor.getString(9));
                t.setEstatusInt(cursor.getInt(10));

                asistencia = new Asistencia();
                asistencia.setFecha(cursor.getLong(0));
                asistencia.setTrabajador(t);
                asistencia.setTotalHoras(cursor.getFloat(2));
                asistencia.setId(cursor.getInt(3));
                asistencia.setDispositivo(cursor.getString(5));
                asistencia.setHoraInicial(cursor.getLong(6));
                asistencia.setHoraFinal(cursor.getLong(7));
                asistencia.setEnviado(cursor.getInt(8));
                asistencia.setCamposTrabajados(new CamposTrabajadosDAO(context).listarCamposTrabajados(asistencia.getId()));*/
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
        String selectQuery = "SELECT * FROM " + VIEW_ASISTENCIA //"SELECT * FROM " + TABLE_ASISTENCIA
                            +" where " + ENVIADO + " = 0";
        SQLiteDatabase data = db.getWritableDatabase();
        Cursor cursor = data.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                if(asistencia==null || asistencia.getId() != cursor.getInt(3)){
                    asistencia = new Asistencia();
                    asistencias.add(asistencia);
                }
                SetAsistencia(cursor,asistencia);
                /*asistencia = new Asistencia();
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

                asistencias.add(asistencia);*/
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
        String selectQuery = "SELECT * FROM " + VIEW_ASISTENCIA //"SELECT * FROM " + TABLE_ASISTENCIA
                +" where " + ENVIADO + " = 0 AND "+FECHA_TEXTO+"<>'"+ Settings.getInstanacia().getFECHA() +"'";
        SQLiteDatabase data = db.getWritableDatabase();
        Cursor cursor = data.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                if(asistencia==null || asistencia.getId() != cursor.getInt(3)){
                    asistencia = new Asistencia();
                    asistencias.add(asistencia);
                }
                SetAsistencia(cursor,asistencia);
                /*asistencia = new Asistencia();
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

                asistencias.add(asistencia);*/
            } while (cursor.moveToNext());
        }
        // return contact list

        cursor.close();
        data.close();
        FileLog.v(Complementos.TAG_BDHANDLER,"Total Asistencia "+asistencias.size());
        return asistencias;
    }

    private void SetAsistencia(Cursor cursor, Asistencia asistencia){

        if(asistencia.getId() != cursor.getInt(3)){

            asistencia.setFecha(cursor.getLong(0));
            //asistencia.setActividad(new DatosActividadesDAO(context).leerPorId(cursor.getInt(2)));
            asistencia.setTotalHoras(cursor.getFloat(2));
            asistencia.setId(cursor.getInt(3));
            asistencia.setDispositivo(cursor.getString(5));
            asistencia.setHoraInicial(cursor.getLong(6));
            asistencia.setHoraFinal(cursor.getLong(7));
            asistencia.setEnviado(cursor.getInt(8));

            Trabajadores t = new Trabajadores();
            t.setNumero(cursor.getInt(9));
            t.setNombre(cursor.getString(10));
            t.setEstatusInt(cursor.getInt(11));
            asistencia.setTrabajador(t/*new DatosTrabajadoresDAO(context).leerPorId(cursor.getInt(1))*/);

            asistencia.setCamposTrabajados(new HashMap<>());

        }


        CamposTrabajados ct;
        if(asistencia.getCamposTrabajados().containsKey(cursor.getString(23))){
            ct = asistencia.getCamposTrabajados().get(cursor.getString(23));

        }else{
            ct = new CamposTrabajados();
            ct.setId(cursor.getInt(12));
            ct.setEnviado(cursor.getInt(16));
            ct.setHoras(cursor.getFloat(21));
            ct.setAsistencia(asistencia);

            Actividades ac = new Actividades();
            ac.setClave(cursor.getLong(22));
            ac.setDescripcion(cursor.getString(23));
            ac.setPrecio(cursor.getFloat(24));
            ac.setSelect(true);
            ct.setActividades(ac);
            ct.setCampos(new ArrayList<>());
            asistencia.getCamposTrabajados().put(ac.getDescripcion(),ct);
        }


        if(!cursor.isNull(14)){
            TablasProrrateo tp = new TablasProrrateo();
            tp.setId(cursor.getString(31));
            tp.setDescripcion(cursor.getString(32));
            ct.setTablaProrrateo(tp);
        }

        if(!cursor.isNull(13)){
            Campos c = new Campos();
            c.setSelect(true);
            c.setClave(cursor.getLong(33));
            c.setSuperficie(cursor.getDouble(34));
            c.setDescripcion(cursor.getString(35));

            Productos p = new Productos();
            p.setClave(cursor.getInt(25));
            p.setDescripcion(cursor.getString(26));
            c.setProductoSeleccionado(p);

            CCE cce = new CCE();
            cce.setClave(cursor.getInt(27));
            cce.setDescripcion(cursor.getString(28));
            c.setCceSeleccionada(cce);

            Etapas e = new Etapas();
            e.setClave(cursor.getInt(29));
            e.setDescripcion(cursor.getString(30));
            c.setEtapaSeleccionada(e);

            ct.getCampo().add(c);
        }
    }
}

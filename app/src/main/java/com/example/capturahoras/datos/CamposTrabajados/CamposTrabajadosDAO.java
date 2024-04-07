package com.example.capturahoras.datos.CamposTrabajados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.capturahoras.complemento.Complementos;
import com.example.capturahoras.complemento.FileLog;
import com.example.capturahoras.datos.Actividades.DatosActividadesDAO;
import com.example.capturahoras.datos.Asistencia.AsistenciaDAO;
import com.example.capturahoras.datos.CCE.DatosCceDAO;
import com.example.capturahoras.datos.DBHandler;
import com.example.capturahoras.datos.Etapas.DatosEtapaDAO;
import com.example.capturahoras.datos.Productos.DatosProductoDAO;
import com.example.capturahoras.datos.campos.DatosCampoDAO;
import com.example.capturahoras.datos.tablaProrrate.TablaProrrateoDAO;
import com.example.capturahoras.modelo.Actividades;
import com.example.capturahoras.modelo.Asistencia;
import com.example.capturahoras.modelo.Campos;
import com.example.capturahoras.modelo.CamposTrabajados;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CamposTrabajadosDAO implements ICamposTrabajadosDAO {
    private DBHandler db;
    private Context context;

    public  CamposTrabajadosDAO(Context c) {
        db = DBHandler.getInstancia(c);
        context=c;
    }

    @Override
    public List<CamposTrabajados> listarActivos() {
        return null;
    }

    @Override
    public CamposTrabajados leerPorId(int idAsistencia) {

        return null;
    }

    @Override
    public void guardar(CamposTrabajados o) {
        FileLog.v(Complementos.TAG_BDHANDLER,"AÑADIR CAMPOS TRABAJADOS "+o.toString());
        SQLiteDatabase data = db.getWritableDatabase();

        Long insert=0L;

        if(o.getCampo()!= null){
            for (Campos c :o.getCampo()) {
                ContentValues values = new ContentValues();
                values.put(ID_CAMPO ,c.getClave());
                values.put(ID_ACTIVIDAD,o.getActividades().getClave());
                values.put(ID_ASISTENCIA,o.getAsistencia().getId());
                values.put(PRODUCTO,c.getProductoSeleccionado().getClave());
                values.put(CCE,c.getCceSeleccionada().getClave());
                values.put(ETAPA,c.getEtapaSeleccionada().getClave());
                values.put(ENVIADO,0);

                insert = data.insert(TABLE_CAMPOSTRABAJADOS, null, values);//guardar mallas
                new DatosCampoDAO(context).actualizar(c);
                data = db.getWritableDatabase();
            }
        }

        if(o.getTablaProrrateo() != null){
            ContentValues values = new ContentValues();

            values.put(ID_TABLAPRORRATEO ,o.getTablaProrrateo().getId());
            values.put(ID_ACTIVIDAD,o.getActividades().getClave());
            values.put(ID_ASISTENCIA,o.getAsistencia().getId());
            values.put(ENVIADO,0);

            insert = data.insert(TABLE_CAMPOSTRABAJADOS, null, values);// guardar tabla
        }


        if(insert ==-1)
            FileLog.v(Complementos.TAG_BDHANDLER,"ERROR DE INSERSION CAMPOS TRABAJADOS ");
        db.close(); // Closing database connection

        return;
    }

    @Override
    public void actualizar(CamposTrabajados o) {
        FileLog.v(Complementos.TAG_BDHANDLER,"ACTUALIZAR CAMPOS TRABAJADOS "+o.toString());

        try{
            SQLiteDatabase data = db.getWritableDatabase();
            //ContentValues values = new ContentValues();

            int i=0;

            if(o.getCampo() != null){
                for (Campos c :o.getCampo()) {
                    ContentValues values = new ContentValues();
                    values.put(ID_CAMPO ,c.getClave());
                    values.put(ID_ACTIVIDAD,o.getActividades().getClave());
                    values.put(ID_ASISTENCIA,o.getAsistencia().getId());
                    values.put(PRODUCTO,c.getProductoSeleccionado().getClave());
                    values.put(CCE,c.getCceSeleccionada().getClave());
                    values.put(ETAPA,c.getEtapaSeleccionada().getClave());
                    values.put(ENVIADO,o.getEnviado());

                    i = data.update(TABLE_CAMPOSTRABAJADOS, values, ID + " = ?",new String[]{String.valueOf(c.getId_canposTrabajados())});//guardar mallas
                    new DatosCampoDAO(context).actualizar(c);
                }
            }

            if(o.getTablaProrrateo() != null){
                ContentValues values = new ContentValues();

                values.put(ID_TABLAPRORRATEO ,o.getTablaProrrateo().getId());
                values.put(ID_ACTIVIDAD,o.getActividades().getClave());
                values.put(ID_ASISTENCIA,o.getAsistencia().getId());
                values.put(ENVIADO,o.getEnviado());

                i = data.update(TABLE_CAMPOSTRABAJADOS, values, ID + " = ?",
                        new String[]{String.valueOf(o.getId())});// guardar tabla
            }

            data.close();
        }catch (Exception e){
            FileLog.v(Complementos.TAG_BDHANDLER,"error "+e.getMessage());
        }
        return;
    }

    @Override
    public void eliminar(long idAsistencia) {
        FileLog.v(Complementos.TAG_BDHANDLER,"ELIMINAR CAMPOS TRABAJADOS "+idAsistencia);
        int i = -1;
        try{

            SQLiteDatabase data = db.getWritableDatabase();
            data.delete(TABLE_CAMPOSTRABAJADOS, ID_ASISTENCIA+"=?", new String[]{String.valueOf(idAsistencia)});
            data.close();
        }catch (Exception e){
            FileLog.v(Complementos.TAG_BDHANDLER,"error "+e.getMessage());
        }
        return;
    }

    @Override
    public HashMap<String,CamposTrabajados> listarCamposTrabajados(long idAsistencia) {
        HashMap<String,CamposTrabajados> camposTrabajados = new HashMap<>();

        String selectQuery = "SELECT * FROM " + TABLE_CAMPOSTRABAJADOS +" WHERE "+ ID_ASISTENCIA +" = '"+idAsistencia+"' ORDER BY "+ID_ASISTENCIA+","+ID_ACTIVIDAD;
        SQLiteDatabase data = db.getWritableDatabase();
        Cursor cursor = data.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                Actividades a = new DatosActividadesDAO(context).leerPorId(cursor.getInt(5));
                a.setSelect(true);
                CamposTrabajados ct = camposTrabajados.get(a.getDescripcion());
                if(ct==null)
                    ct = new CamposTrabajados();

                ct.setId(cursor.getInt(0));
                ct.setActividades(a);
                ct.setAsistencia(new AsistenciaDAO(context).leerPorId(cursor.getInt(3)));
                ct.setEnviado(cursor.getInt(4));

                if(!cursor.isNull(2))
                    ct.setTablaProrrateo(new TablaProrrateoDAO(context).leerPorId(cursor.getInt(2)));

                if(!cursor.isNull(1)){
                    if(ct.getCampo() == null)
                        ct.setCampos(new ArrayList<>());

                    List<Campos> cam = ct.getCampo();
                    Campos campos = new DatosCampoDAO(context).leerPorId(cursor.getInt(1));
                    campos.setSelect(true);
                    campos.setId_canposTrabajados(ct.getId());
                    campos.setProductoSeleccionado(new DatosProductoDAO(context).leerPorId(cursor.getInt(6)));
                    campos.setCceSeleccionada(new DatosCceDAO(context).leerPorId(cursor.getInt(7)));
                    campos.setEtapaSeleccionada(new DatosEtapaDAO(context).leerPorId(cursor.getInt(8)));
                    cam.add(campos);
                    ct.setCampos(cam);
                }

                if(!camposTrabajados.containsKey(a.getDescripcion()))
                    camposTrabajados.put(a.getDescripcion(),ct);
            } while (cursor.moveToNext());
        }
        // return contact list

        cursor.close();
        data.close();
        FileLog.v(Complementos.TAG_BDHANDLER,"Total campos trabajadas por asistencia "+camposTrabajados.size());

        return camposTrabajados;
    }
}

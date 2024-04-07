package com.example.capturahoras.datos.GrupoCampos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.capturahoras.complemento.Complementos;
import com.example.capturahoras.complemento.FileLog;
import com.example.capturahoras.datos.DBHandler;
import com.example.capturahoras.datos.campos.DatosCampoDAO;
import com.example.capturahoras.datos.tablaProrrate.TablaProrrateoDAO;
import com.example.capturahoras.modelo.GrupoCampos;
import com.example.capturahoras.modelo.TablasProrrateo;

import java.util.ArrayList;
import java.util.List;

public class GrupoCamposDAO implements IGrupoCamposDAO{
    private DBHandler db;
    private Context context;

    public  GrupoCamposDAO(Context c) {
        context=c;
        db = DBHandler.getInstancia(c);
    }

    @Override
    public List<GrupoCampos> listarActivos() {

        GrupoCampos campos = null;
        TablasProrrateo tabla =null;
        ArrayList<GrupoCampos> grupo = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_GRUPOPRORRATEO;
        SQLiteDatabase data = db.getWritableDatabase();
        Cursor cursor = data.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            tabla = new TablaProrrateoDAO(context).leerPorId(cursor.getInt(1));
            do {
                campos = new GrupoCampos();
                campos.setCampos(new DatosCampoDAO(context).leerPorId(cursor.getInt(0)));
                campos.setTablaProrrateo(tabla);
            } while (cursor.moveToNext());
        }

        cursor.close();
        data.close();
        FileLog.v(Complementos.TAG_BDHANDLER,"Total campos "+grupo.size());

        return grupo;
    }

    @Override
    public GrupoCampos leerPorId(int id) {
        return null;
    }

    @Override
    public void guardar(GrupoCampos o) {
        FileLog.v(Complementos.TAG_BDHANDLER,"AÑADIR GRUPOCAMPOS "+o.toString());
        SQLiteDatabase data = db.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(IDCAMPO ,o.getCampos().getClave());
        values.put(IDTABLAPRORRATEO ,o.getTablaProrrateo().getId());

        Long insert = data.insert(TABLE_GRUPOPRORRATEO, null, values);

        if(insert ==-1)
            FileLog.v(Complementos.TAG_BDHANDLER,"ERROR DE INSERSION GRUPOCAMPOS ");
        db.close(); // Closing database connection

        return;
    }

    @Override
    public void actualizar(GrupoCampos o) {

    }

    @Override
    public void eliminar(long id) {

    }

    @Override
    public ArrayList<GrupoCampos> getCampos(int idTablaProrrateo) {
        GrupoCampos campos = null;
        TablasProrrateo tabla =null;
        ArrayList<GrupoCampos> grupo = new ArrayList<>();


        /*String selectQuery = "SELECT c."+
                +" FROM " + TABLE_GRUPOPRORRATEO +" as gc"
                + " INNER JOIN " + ICamposDAO.TABLE_CAMPOS +" as c ON c." + ICamposDAO.CLAVE + " = gc."+IDCAMPO
                + " INNER JOIN " + ITablaProrrateoDAO.TABLE_TABLAPRORRATEO +" as tp ON tp." + ITablaProrrateoDAO.CLAVE + " = gc."+IDTABLAPRORRATEO
                +" WHERE "+IDTABLAPRORRATEO+" = '"+idTablaProrrateo+"' ";*/

        String selectQuery = "SELECT * FROM " + TABLE_GRUPOPRORRATEO +" WHERE "+IDTABLAPRORRATEO+" = '"+idTablaProrrateo+"' ";
        SQLiteDatabase data = db.getWritableDatabase();
        Cursor cursor = data.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            tabla = new TablaProrrateoDAO(context).leerPorId(idTablaProrrateo);
            do {
                campos = new GrupoCampos();
                campos.setCampos(new DatosCampoDAO(context).leerPorId(cursor.getInt(0)));
                campos.setTablaProrrateo(tabla);
            } while (cursor.moveToNext());
        }

        cursor.close();
        data.close();
        FileLog.v(Complementos.TAG_BDHANDLER,"Total campos "+grupo.size());

        return grupo;
    }
}

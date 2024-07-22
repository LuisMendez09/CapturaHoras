package com.example.capturahoras.datos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.capturahoras.complemento.Complementos;
import com.example.capturahoras.complemento.FileLog;
import com.example.capturahoras.complemento.KeyValues;
import com.example.capturahoras.datos.Actividades.IActividadesDAO;
import com.example.capturahoras.datos.Asistencia.IAsistenciaDAO;
import com.example.capturahoras.datos.CCE.ICceDAO;
import com.example.capturahoras.datos.CamposTrabajados.ICamposTrabajadosDAO;
import com.example.capturahoras.datos.Etapas.IEtapasDAO;
import com.example.capturahoras.datos.GrupoCampos.IGrupoCamposDAO;
import com.example.capturahoras.datos.Productos.IProductosDAO;
import com.example.capturahoras.datos.Settings.ISettingDAO;
import com.example.capturahoras.datos.Trabajadores.ITrabajadoresDAO;
import com.example.capturahoras.datos.campos.ICamposDAO;
import com.example.capturahoras.datos.tablaProrrate.ITablaProrrateoDAO;


public class DBHandler extends SQLiteOpenHelper  {

    private static DBHandler instacia = null;

    private static final String UPDATE_ACTIVIDAD_PRECIO = "ALTER TABLE "+IActividadesDAO.TABLE_ACTIVIDADES
            +" ADD "+ IActividadesDAO.PRECIO + " REAL NOT NULL DEFAULT 0.0;";

    public static DBHandler getInstancia(Context context){
        if(instacia == null){
            instacia = new DBHandler(context);
            instacia.getWritableDatabase();
        }

        return instacia;
    }


    private DBHandler(@Nullable Context context) {
        super(new DataBaseContext(context), KeyValues.MY_DATABASE_NAME+KeyValues.EXTENCIO_DATABASE,
                null, KeyValues.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        FileLog.v(Complementos.TAG_BDHANDLER,"INICIA CREACION DE LAS TABLAS TRABAJADORES");

        String CREATE_SETTINGS_TABLE = "CREATE TABLE " + ISettingDAO.TABLA_SETTINGS + "("
                +ISettingDAO.SETTINGS_ID+ " INTEGER,"
                +ISettingDAO.SETTINGS_FECHA+ " TEXT PRIMARY KEY,"
                +ISettingDAO.SETTINGS_DATE+" INTEGER,"
                +ISettingDAO.SETTINGS_URL+" TEXT,"
                +ISettingDAO.SETTINGS_MAILS+" TEXT,"
                +ISettingDAO.SETTINGS_USUARIO+" TEXT,"
                +ISettingDAO.SETTINGS_JORNADA_FINALIZADA+" INTEGER"
                +")";
        sqLiteDatabase.execSQL(CREATE_SETTINGS_TABLE);

        String CREATE_TABLE = "CREATE TABLE " + ITrabajadoresDAO.TABLE_TRABAJADORES + "("
                + ITrabajadoresDAO.CLAVE + " INTEGER PRIMARY KEY,"
                + ITrabajadoresDAO.NOMBRE+ " TEXT,"
                + ITrabajadoresDAO.STATUS+ " INTEGER"+ ")";
        sqLiteDatabase.execSQL(CREATE_TABLE);


        FileLog.v(Complementos.TAG_BDHANDLER,"TERMINA CREACION DE LAS TABLAS TRABAJADORES");

        FileLog.v(Complementos.TAG_BDHANDLER,"INICIA CREACION DE LAS TABLAS ACTIVIDADES");
        CREATE_TABLE = "CREATE TABLE " + IActividadesDAO.TABLE_ACTIVIDADES + "("
                + IActividadesDAO.CLAVE + " INTEGER PRIMARY KEY,"
                + IActividadesDAO.DESCRIPCION+ " TEXT,"
                + IActividadesDAO.PRECIO + " REAL"+ ")";

        sqLiteDatabase.execSQL(CREATE_TABLE);
        FileLog.v(Complementos.TAG_BDHANDLER,"TERMINA CREACION DE LAS TABLAS ACTIVIDADES");

        FileLog.v(Complementos.TAG_BDHANDLER,"INICIA CREACION DE LAS TABLAS CAMPOS");
        CREATE_TABLE = "CREATE TABLE " + ICamposDAO.TABLE_CAMPOS + "("
                + ICamposDAO.CLAVE + " INTEGER PRIMARY KEY,"
                + ICamposDAO.SUPERFICIE+ " REAL,"
                + ICamposDAO.DESCRIPCION+ " TEXT,"
                + ICamposDAO.PRODUCTO+ " INTEGER,"
                + ICamposDAO.CCE+ " INTEGER,"
                + ICamposDAO.ETAPA+ " INTEGER"+ ")";

        sqLiteDatabase.execSQL(CREATE_TABLE);
        FileLog.v(Complementos.TAG_BDHANDLER,"TERMINA CREACION DE LAS TABLAS CAMPO");

        FileLog.v(Complementos.TAG_BDHANDLER,"INICIA CREACION DE LAS TABLAS PRORRATEO");
        CREATE_TABLE = "CREATE TABLE " + ITablaProrrateoDAO.TABLE_TABLAPRORRATEO + "("
                + ITablaProrrateoDAO.CLAVE + " TEXT PRIMARY KEY,"
                + ITablaProrrateoDAO.DESCRIPCION+ " TEXT"+ ")";

        sqLiteDatabase.execSQL(CREATE_TABLE);
        FileLog.v(Complementos.TAG_BDHANDLER,"TERMINA CREACION DE LAS TABLAS PRORRATEO");

        FileLog.v(Complementos.TAG_BDHANDLER,"INICIA CREACION DE LAS TABLAS GRUPOS CAMPO");
        CREATE_TABLE = "CREATE TABLE " + IGrupoCamposDAO.TABLE_GRUPOPRORRATEO + "("
                + IGrupoCamposDAO.IDCAMPO + " INTEGER,"
                + IGrupoCamposDAO.IDTABLAPRORRATEO+ " TEXT"+ ")";

        sqLiteDatabase.execSQL(CREATE_TABLE);
        FileLog.v(Complementos.TAG_BDHANDLER,"TERMINA CREACION DE LAS TABLAS GRUPO CAMPOS");

        FileLog.v(Complementos.TAG_BDHANDLER,"INICIA CREACION DE LAS TABLAS ASISTENCIA");
        CREATE_TABLE = "CREATE TABLE " + IAsistenciaDAO.TABLE_ASISTENCIA + "("
                + IAsistenciaDAO.FECHA + " INTEGER,"
                + IAsistenciaDAO.ID_TRABAJADOR + " INTEGER,"
                //+ IAsistenciaDAO.ID_ACTIVIDAD + " INTEGER,"
                + IAsistenciaDAO.TOTAL_HORAS + " REAL,"
                + IAsistenciaDAO.ID + " INTEGER PRIMARY KEY,"
                + IAsistenciaDAO.FECHA_TEXTO+ " TEXT,"
                + IAsistenciaDAO.DISPOSITIVO+ " TEXT,"
                + IAsistenciaDAO.HORA_INICIO+ " INTEGER,"
                + IAsistenciaDAO.HORA_FINAL+ " INTEGER,"
                + IAsistenciaDAO.ENVIADO+ " INTEGER"+ ")";

        sqLiteDatabase.execSQL(CREATE_TABLE);
        FileLog.v(Complementos.TAG_BDHANDLER,"TERMINA CREACION DE LAS TABLAS ASISTENCIA");

        FileLog.v(Complementos.TAG_BDHANDLER,"INICIA CREACION DE LAS TABLAS CAMPOS TRABAJADOS");
        CREATE_TABLE = "CREATE TABLE " + ICamposTrabajadosDAO.TABLE_CAMPOSTRABAJADOS + "("
                + ICamposTrabajadosDAO.ID + " INTEGER  PRIMARY KEY,"
                + ICamposTrabajadosDAO.ID_CAMPO + " INTEGER,"
                + ICamposTrabajadosDAO.ID_TABLAPRORRATEO + " INTEGER,"
                + ICamposTrabajadosDAO.ID_ASISTENCIA + " TEXT,"
                + ICamposTrabajadosDAO.ENVIADO + " INTEGER,"
                + ICamposTrabajadosDAO.ID_ACTIVIDAD + " INTEGER,"
                + ICamposDAO.PRODUCTO+ " INTEGER,"
                + ICamposDAO.CCE+ " INTEGER,"
                + ICamposDAO.ETAPA+ " INTEGER,"
                + ICamposTrabajadosDAO.HORAS+ " REAL"+ ")";

        sqLiteDatabase.execSQL(CREATE_TABLE);
        FileLog.v(Complementos.TAG_BDHANDLER,"TERMINA CREACION DE LAS TABLAS CAMPOS TRABAJADOS");

        FileLog.v(Complementos.TAG_BDHANDLER,"INICIA CREACION DE LAS TABLAS PRODUCTOS");
        CREATE_TABLE = "CREATE TABLE " + IProductosDAO.TABLE_PRODUCTOS + "("
                + IProductosDAO.CLAVE + " INTEGER  PRIMARY KEY,"
                + IProductosDAO.DESCRIPCION + " TEXT"+ ")";

        sqLiteDatabase.execSQL(CREATE_TABLE);
        FileLog.v(Complementos.TAG_BDHANDLER,"TERMINA CREACION DE LAS TABLAS PRODUCTOS");

        FileLog.v(Complementos.TAG_BDHANDLER,"INICIA CREACION DE LAS TABLAS ETAPAS");
        CREATE_TABLE = "CREATE TABLE " + IEtapasDAO.TABLE_ETAPAS + "("
                + IEtapasDAO.CLAVE + " INTEGER  PRIMARY KEY,"
                + IEtapasDAO.DESCRIPCION + " TEXT"+ ")";

        sqLiteDatabase.execSQL(CREATE_TABLE);
        FileLog.v(Complementos.TAG_BDHANDLER,"TERMINA CREACION DE LAS TABLAS ETAPAS");

        FileLog.v(Complementos.TAG_BDHANDLER,"INICIA CREACION DE LAS TABLAS CCE");
        CREATE_TABLE = "CREATE TABLE " + ICceDAO.TABLE_CCE + "("
                + ICceDAO.CLAVE + " INTEGER  PRIMARY KEY,"
                + ICceDAO.DESCRIPCION + " TEXT"+ ")";

        sqLiteDatabase.execSQL(CREATE_TABLE);
        FileLog.v(Complementos.TAG_BDHANDLER,"TERMINA CREACION DE LAS TABLAS CCE");

        FileLog.v(Complementos.TAG_BDHANDLER,"creacion de vista asistencia");
        CREATE_TABLE = "CREATE VIEW "+IAsistenciaDAO.VIEW_ASISTENCIA+" AS "+
                "SELECT * FROM " + IAsistenciaDAO.TABLE_ASISTENCIA  +" as a " +
                " inner join "+ITrabajadoresDAO.TABLE_TRABAJADORES+" as t on t."+ITrabajadoresDAO.CLAVE+"=a."+IAsistenciaDAO.ID_TRABAJADOR+
                " inner join "+ ICamposTrabajadosDAO.TABLE_CAMPOSTRABAJADOS +" as ct on ct."+ICamposTrabajadosDAO.ID_ASISTENCIA+"=a."+IAsistenciaDAO.ID+
                " inner join "+ IActividadesDAO.TABLE_ACTIVIDADES +" as ac on ac."+IActividadesDAO.CLAVE+"=ct."+IAsistenciaDAO.ID_ACTIVIDAD+
                " left join "+ IProductosDAO.TABLE_PRODUCTOS +" as p on p."+IProductosDAO.CLAVE+"=ct."+ICamposTrabajadosDAO.PRODUCTO+
                " left join "+ ICceDAO.TABLE_CCE +" as cc on cc."+ICceDAO.CLAVE+"=ct."+ICamposTrabajadosDAO.CCE+
                " left join "+ IEtapasDAO.TABLE_ETAPAS +" as e on e."+IEtapasDAO.CLAVE+"=ct."+ICamposTrabajadosDAO.ETAPA+
                " left join "+ ITablaProrrateoDAO.TABLE_TABLAPRORRATEO +" as tp on tp."+ITablaProrrateoDAO.CLAVE+"=ct."+ICamposTrabajadosDAO.ID_TABLAPRORRATEO+
                " left join "+ICamposDAO.TABLE_CAMPOS+" as c on c."+ICamposDAO.CLAVE+"=ct."+ICamposTrabajadosDAO.ID_CAMPO;

        sqLiteDatabase.execSQL(CREATE_TABLE);
        FileLog.v(Complementos.TAG_BDHANDLER,"TERMINA CREACION DE vista asistencia");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if(oldVersion == newVersion){
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ITrabajadoresDAO.TABLE_TRABAJADORES);
            //sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + IActividadesDAO.TABLE_ACTIVIDADES);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ICamposDAO.TABLE_CAMPOS);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ITablaProrrateoDAO.TABLE_TABLAPRORRATEO);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + IGrupoCamposDAO.TABLE_GRUPOPRORRATEO);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + IAsistenciaDAO.TABLE_ASISTENCIA);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ICamposTrabajadosDAO.TABLE_CAMPOSTRABAJADOS);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + IProductosDAO.TABLE_PRODUCTOS);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + IEtapasDAO.TABLE_ETAPAS);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ICceDAO.TABLE_CCE);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ISettingDAO.TABLA_SETTINGS);

            onCreate(sqLiteDatabase);
        }else{
            if(oldVersion == 1 && newVersion>=2){
                Log.e("Log android","actualiuzacion tablas en metodo onUpgrade()");
                sqLiteDatabase.execSQL(UPDATE_ACTIVIDAD_PRECIO);
            }

            if(oldVersion == 2 && newVersion>=3){
                FileLog.v(Complementos.TAG_BDHANDLER,"creacion de vista asistencia");
                String CREATE_TABLE = "CREATE VIEW "+IAsistenciaDAO.VIEW_ASISTENCIA+" AS "+
                        "SELECT * FROM " + IAsistenciaDAO.TABLE_ASISTENCIA  +" as a " +
                        " inner join "+ITrabajadoresDAO.TABLE_TRABAJADORES+" as t on t."+ITrabajadoresDAO.CLAVE+"=a."+IAsistenciaDAO.ID_TRABAJADOR+
                        " inner join "+ ICamposTrabajadosDAO.TABLE_CAMPOSTRABAJADOS +" as ct on ct."+ICamposTrabajadosDAO.ID_ASISTENCIA+"=a."+IAsistenciaDAO.ID+
                        " inner join "+ IActividadesDAO.TABLE_ACTIVIDADES +" as ac on ac."+IActividadesDAO.CLAVE+"=ct."+IAsistenciaDAO.ID_ACTIVIDAD+
                        " left join "+ IProductosDAO.TABLE_PRODUCTOS +" as p on p."+IProductosDAO.CLAVE+"=ct."+ICamposTrabajadosDAO.PRODUCTO+
                        " left join "+ ICceDAO.TABLE_CCE +" as cc on cc."+ICceDAO.CLAVE+"=ct."+ICamposTrabajadosDAO.CCE+
                        " left join "+ IEtapasDAO.TABLE_ETAPAS +" as e on e."+IEtapasDAO.CLAVE+"=ct."+ICamposTrabajadosDAO.ETAPA+
                        " left join "+ ITablaProrrateoDAO.TABLE_TABLAPRORRATEO +" as tp on tp."+ITablaProrrateoDAO.CLAVE+"=ct."+ICamposTrabajadosDAO.ID_TABLAPRORRATEO+
                        " left join "+ICamposDAO.TABLE_CAMPOS+" as c on c."+ICamposDAO.CLAVE+"=ct."+ICamposTrabajadosDAO.ID_CAMPO;

                sqLiteDatabase.execSQL(CREATE_TABLE);
                FileLog.v(Complementos.TAG_BDHANDLER,"TERMINA CREACION DE vista asistencia");
            }
        }



    }


}

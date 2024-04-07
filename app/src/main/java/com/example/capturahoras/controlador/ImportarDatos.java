package com.example.capturahoras.controlador;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.capturahoras.complemento.Complementos;
import com.example.capturahoras.complemento.FileLog;
import com.example.capturahoras.datos.Actividades.DatosActividadesDAO;
import com.example.capturahoras.datos.CCE.DatosCceDAO;
import com.example.capturahoras.datos.Etapas.DatosEtapaDAO;
import com.example.capturahoras.datos.GrupoCampos.GrupoCamposDAO;
import com.example.capturahoras.datos.Productos.DatosProductoDAO;
import com.example.capturahoras.datos.Trabajadores.DatosTrabajadoresDAO;
import com.example.capturahoras.datos.API.Catalogos;
import com.example.capturahoras.datos.campos.DatosCampoDAO;
import com.example.capturahoras.datos.tablaProrrate.TablaProrrateoDAO;
import com.example.capturahoras.modelo.Actividades;
import com.example.capturahoras.modelo.Asistencia;
import com.example.capturahoras.modelo.CCE;
import com.example.capturahoras.modelo.Campos;
import com.example.capturahoras.modelo.CamposTrabajados;
import com.example.capturahoras.modelo.Etapas;
import com.example.capturahoras.modelo.GrupoCampos;
import com.example.capturahoras.modelo.Productos;
import com.example.capturahoras.modelo.Settings;
import com.example.capturahoras.modelo.TablasProrrateo;
import com.example.capturahoras.modelo.Trabajadores;

import java.util.HashMap;
import java.util.List;
import java.util.Set;


public class ImportarDatos extends Worker {
    public static final String TRABAJADORES="trabajadores";
    public static final String CATALOGOS="catalogos";
    public static final String TODOS="todosCatalogos";
    private static final String ACTIVIDADES="actividades";
    private static final String CAMPOS="campos";
    private static final String TABLA_PRORRATEO="tabla";
    private static final String TABLA_PRODUCTOS="productos";
    private static final String TABLA_ETAPAS="etapas";
    private static final String TABLA_CCE="cce";

    public static final String KEY_CATALOGOS = "catalogos";
    public static final String PROGRESS = "PROGRESS";
    public static final String FINISH = "finalizar";
    public static final String TOTAL = "total";
    public static final String MENSAjE = "mensaje";
    public static final String ERROR = "error";

    public static final String KEY_ENVIAR = "enviar";
    public static final String REGISTROS_SIN_ENVIAR="sin enviar";
    public static final String CAMPOS_UPDATE="camposUpdate";


    public ImportarDatos(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    private boolean getCatalogo(String catalogo){
        boolean resultado=true;
        Catalogos c = new Catalogos();
        FileLog.i(Complementos.TAG_CATALOGOS,"actualziar catalogo "+catalogo);
        try{
            switch (catalogo){
                case TRABAJADORES:
                    resultado = descargarCatalogoTrabajadores(c);
                    break;
                case CATALOGOS:
                    //resultado = descargarRestosCatalogos(c);
                    break;
                case TODOS:
                    resultado = descargarTodosCatalogos(c);
                    break;
                case REGISTROS_SIN_ENVIAR:
                    resultado = subirRegistros(c);
                    break;
                case CAMPOS_UPDATE:
                    resultado = actualizarCampos(c);
                    break;

            }

            if(resultado){
                setProgressAsync(new Data.Builder().putBoolean(FINISH,true).build());
                Thread.sleep(100L);
            }else{
                setProgressAsync(new Data.Builder().putString(ERROR,"Error al conectar con el servidor").build());
                Thread.sleep(100L);
            }
        }catch (InterruptedException e){
            FileLog.i(Complementos.TAG_CATALOGOS,e.getMessage());
        }

        return resultado;
    }

    private boolean subirRegistros(Catalogos API){
        try {
            setProgressAsync(new Data.Builder()
                    .putString(MENSAjE,"Enviando informacion")
                    .build());

            List<Asistencia> ase = AsistenciaControl.getAsistenciaSinEnviar(Settings.CONTEXT);

            Boolean b = API.enviarAsistencias(ase);
            if(!b){
                API.getException().printStackTrace();
                FileLog.i(Complementos.TAG_CATALOGOS,API.getException().getMessage());
            }else{
                for (Asistencia a :ase) {
                    a.setEnviado(1);
                    HashMap<String, CamposTrabajados> hct = a.getCamposTrabajados();
                    Set<String> actividad = hct.keySet();
                    for (String key: actividad) {
                        hct.get(key).setEnviado(1);
                    }

                    AsistenciaControl.actualizarEnvio(Settings.CONTEXT,a);
                }
            }


            //Configuracion.inicializarCatalogosVehiculos();
        } catch (Exception e) {
            e.printStackTrace();
            FileLog.i(Complementos.TAG_CATALOGOS,e.getMessage());
        }


        return true;
    }

    private boolean actualizarCampos(Catalogos API){
        try {
            setProgressAsync(new Data.Builder()
                    .putString(MENSAjE,"Enviando campos")
                    .build());

            List<Campos> ase = CamposControl.getCampos(Settings.CONTEXT);

            Boolean b = API.actualziarCampos(ase);
            if(!b){
                API.getException().printStackTrace();
                FileLog.i(Complementos.TAG_CATALOGOS,API.getException().getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            FileLog.i(Complementos.TAG_CATALOGOS,e.getMessage());
        }


        return true;
    }

    private boolean descargarCatalogoTrabajadores(Catalogos API){
        try {
            setProgressAsync(new Data.Builder()
                    .putString(MENSAjE,"Buscado catalogos de Trabajadores")
                    .build());

            //catalogo de vehiculos
            Boolean b = API.buscarTrabajadores();//buscarCatalogoTrabajadores();
            if(!b){
                API.getException().printStackTrace();
                FileLog.i(Complementos.TAG_CATALOGOS,API.getException().getMessage());
                return  false;
            }

            DatosTrabajadoresDAO trabajadores = new DatosTrabajadoresDAO(this.getApplicationContext());
            trabajadores.reiniciarTabla();
            guardarDatos(API,TRABAJADORES,API.getResult().size(),trabajadores);

            //Configuracion.inicializarCatalogosVehiculos();
        } catch (InterruptedException e) {
            e.printStackTrace();
            FileLog.i(Complementos.TAG_CATALOGOS,e.getMessage());
        }


        return true;
    }

    private boolean descargarCatalogoActividades(Catalogos API){
        try {
            setProgressAsync(new Data.Builder()
                    .putString(MENSAjE,"Buscado catalogos de Actividades")
                    .build());

            //catalogo de vehiculos
            Boolean b = API.buscarActividades();
            if(!b){
                API.getException().printStackTrace();
                FileLog.i(Complementos.TAG_CATALOGOS,API.getException().getMessage());
                return  false;
            }


            DatosActividadesDAO actividadesDAO = new DatosActividadesDAO(this.getApplicationContext());
            actividadesDAO.reiniciarTabla();
            guardarDatos(API,ACTIVIDADES,API.getResult().size(),actividadesDAO);

            //Configuracion.inicializarCatalogosVehiculos();
        } catch (InterruptedException e) {
            e.printStackTrace();
            FileLog.i(Complementos.TAG_CATALOGOS,e.getMessage());
        }


        return true;
    }

    private boolean descargarCatalogoCampos(Catalogos API){
        try {
            setProgressAsync(new Data.Builder()
                    .putString(MENSAjE,"Buscado catalogos de campos")
                    .build());

            //catalogo de vehiculos
            Boolean b = API.buscarCampos();
            if(!b){
                API.getException().printStackTrace();
                FileLog.i(Complementos.TAG_CATALOGOS,API.getException().getMessage());
                return  false;
            }

            DatosCampoDAO camposDAO = new DatosCampoDAO(this.getApplicationContext());
            camposDAO.reiniciarTabla();
            guardarDatos(API,CAMPOS,API.getResult().size(),camposDAO);

            //Configuracion.inicializarCatalogosVehiculos();
        } catch (InterruptedException e) {
            e.printStackTrace();
            FileLog.i(Complementos.TAG_CATALOGOS,e.getMessage());
        }


        return true;
    }

    private boolean descargarCatalogoTablaProrrateo(Catalogos API){
        try {
            setProgressAsync(new Data.Builder()
                    .putString(MENSAjE,"Buscado catalogos de prorrateo")
                    .build());

            //catalogo de vehiculos
            Boolean b = API.buscarTablaProrrateo();
            if(!b){
                API.getException().printStackTrace();
                FileLog.i(Complementos.TAG_CATALOGOS,API.getException().getMessage());
                return  false;
            }

            TablaProrrateoDAO tablas = new TablaProrrateoDAO(this.getApplicationContext());
            tablas.reiniciarTabla();
            guardarDatos(API,TABLA_PRORRATEO,API.getResult().size(),tablas);

            //Configuracion.inicializarCatalogosVehiculos();
        } catch (InterruptedException e) {
            e.printStackTrace();
            FileLog.i(Complementos.TAG_CATALOGOS,e.getMessage());
        }


        return true;
    }

    private boolean descargarCatalogoProductos(Catalogos API){
        try {
            setProgressAsync(new Data.Builder()
                    .putString(MENSAjE,"Buscado catalogos de Productos")
                    .build());

            //catalogo de vehiculos
            Boolean b = API.buscarProductos();
            if(!b){
                API.getException().printStackTrace();
                FileLog.i(Complementos.TAG_CATALOGOS,API.getException().getMessage());
                return  false;
            }

            DatosProductoDAO productos = new DatosProductoDAO(this.getApplicationContext());
            productos.reiniciarTabla();
            guardarDatos(API,TABLA_PRODUCTOS,API.getResult().size(),productos);

            //Configuracion.inicializarCatalogosVehiculos();
        } catch (InterruptedException e) {
            e.printStackTrace();
            FileLog.i(Complementos.TAG_CATALOGOS,e.getMessage());
        }


        return true;
    }

    private boolean descargarCatalogoEtapas(Catalogos API){
        try {
            setProgressAsync(new Data.Builder()
                    .putString(MENSAjE,"Buscado catalogos de Etapas")
                    .build());

            //catalogo de vehiculos
            Boolean b = API.buscarEtapas();
            if(!b){
                API.getException().printStackTrace();
                FileLog.i(Complementos.TAG_CATALOGOS,API.getException().getMessage());
                return  false;
            }

            DatosEtapaDAO etapas = new DatosEtapaDAO(this.getApplicationContext());
            etapas.reiniciarTabla();
            guardarDatos(API,TABLA_ETAPAS,API.getResult().size(),etapas);

            //Configuracion.inicializarCatalogosVehiculos();
        } catch (InterruptedException e) {
            e.printStackTrace();
            FileLog.i(Complementos.TAG_CATALOGOS,e.getMessage());
        }

        return true;
    }

    private boolean descargarCatalogoCCE(Catalogos API){
        try {
            setProgressAsync(new Data.Builder()
                    .putString(MENSAjE,"Buscado catalogos de CCE")
                    .build());

            //catalogo de vehiculos
            Boolean b = API.buscarCCE();
            if(!b){
                API.getException().printStackTrace();
                FileLog.i(Complementos.TAG_CATALOGOS,API.getException().getMessage());
                return  false;
            }

            DatosCceDAO cce = new DatosCceDAO(this.getApplicationContext());
            cce.reiniciarTabla();
            guardarDatos(API,TABLA_CCE,API.getResult().size(),cce);

            //Configuracion.inicializarCatalogosVehiculos();
        } catch (InterruptedException e) {
            e.printStackTrace();
            FileLog.i(Complementos.TAG_CATALOGOS,e.getMessage());
        }

        return true;
    }

    private boolean descargarRestosCatalogos(Catalogos conexion) {

        setProgressAsync(new Data.Builder().putString(MENSAjE,"Buscado catalogos").build());

        //catalogo de combustibles
        boolean resultado =descargarCatalogoActividades(conexion);

        if(!resultado){
            setProgressAsync(new Data.Builder().putString(ERROR,conexion.getException().getMessage()).build());
            return  false;
        }

        resultado =descargarCatalogoCampos(conexion);

        if(!resultado){
            setProgressAsync(new Data.Builder().putString(ERROR,conexion.getException().getMessage()).build());
            return  false;
        }

        resultado =descargarCatalogoTablaProrrateo(conexion);

        if(!resultado){
            setProgressAsync(new Data.Builder().putString(ERROR,conexion.getException().getMessage()).build());
            return  false;
        }

        resultado =descargarCatalogoProductos(conexion);
        if(!resultado){
            setProgressAsync(new Data.Builder().putString(ERROR,conexion.getException().getMessage()).build());
            return  false;
        }

        resultado =descargarCatalogoEtapas(conexion);
        if(!resultado){
            setProgressAsync(new Data.Builder().putString(ERROR,conexion.getException().getMessage()).build());
            return  false;
        }

        resultado =descargarCatalogoCCE(conexion);
        if(!resultado){
            setProgressAsync(new Data.Builder().putString(ERROR,conexion.getException().getMessage()).build());
            return  false;
        }
///agregar el resto de catalogos

        return true;
    }

    private boolean descargarTodosCatalogos(Catalogos conexion) {

        boolean b = descargarRestosCatalogos(conexion);
        if(!b){
            setProgressAsync(new Data.Builder().putString(ERROR,conexion.getException().getMessage()).build());
            return  false;
        }

        b = descargarCatalogoTrabajadores(conexion);
        if(!b){
            setProgressAsync(new Data.Builder().putString(ERROR,conexion.getException().getMessage()).build());
            return  false;
        }

        return true;
    }

    private void guardarDatos(Catalogos conexiones, String catalogo, int totalRegistros, Object db) throws InterruptedException {
        setProgressAsync(new Data.Builder()
                .putInt(TOTAL,totalRegistros)
                .putString(MENSAjE,"Guardando catalogo de "+catalogo)

                .build());
        Thread.sleep(100L);

        FileLog.i(Complementos.TAG_CATALOGOS,"inicia el guardado de datos");

        for (int i =0;i<totalRegistros;i++) {
            switch (catalogo){
                case TRABAJADORES:
                    ((DatosTrabajadoresDAO) db).guardar((Trabajadores) conexiones.getResult().get(i));
                    break;
                case ACTIVIDADES:
                    ((DatosActividadesDAO) db).guardar((Actividades) conexiones.getResult().get(i));
                    break;
                case CAMPOS:
                    Campos c = (Campos) conexiones.getResult().get(i);
                    ((DatosCampoDAO) db).guardar(c);
                    for(int j=0; j<c.getTablas_Prorateo().size();j++){
                        GrupoCampos gc = new GrupoCampos();
                        gc.setCampos(c);
                        gc.setTablaProrrateo(c.getTablas_Prorateo().get(j));
                        new GrupoCamposDAO(Settings.CONTEXT).guardar(gc);
                        gc=null;
                    }
                    c=null;
                    break;
                case TABLA_PRORRATEO:
                    ((TablaProrrateoDAO) db).guardar((TablasProrrateo) conexiones.getResult().get(i));
                    break;
                case TABLA_PRODUCTOS:
                    ((DatosProductoDAO) db).guardar((Productos) conexiones.getResult().get(i));
                    break;
                case TABLA_ETAPAS:
                    ((DatosEtapaDAO) db).guardar((Etapas) conexiones.getResult().get(i));
                    break;
                case TABLA_CCE:
                    ((DatosCceDAO) db).guardar((CCE) conexiones.getResult().get(i));
                    break;
            }

            setProgressAsync(new Data.Builder().putInt(PROGRESS,i+1).build());
            Thread.sleep(10L);
        }
    }

    @NonNull
    @Override
    public Result doWork() {
        String data = getInputData().getString(KEY_CATALOGOS);
        boolean catalogo = getCatalogo(data);

        if(catalogo)
            return Result.success();
        else
            return Result.failure();
    }
}

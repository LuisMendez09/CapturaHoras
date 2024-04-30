package com.example.capturahoras.controlador;

import android.content.Context;


import com.example.capturahoras.R;
import com.example.capturahoras.complemento.Complementos;
import com.example.capturahoras.complemento.Exceptions;
import com.example.capturahoras.complemento.FileLog;
import com.example.capturahoras.datos.Settings.ISettingDAO;
import com.example.capturahoras.datos.Settings.SettingDAO;
import com.example.capturahoras.modelo.Settings;

import java.util.Date;
import java.util.Set;

public class SesionControl {

    public static void InicializarContext(Context context){
        Settings.CONTEXT = context;
        FileLog.i(Complementos.TAG_COFIG,"Contexto iniciado");
    }

    public static boolean inicializarSesion(){
        FileLog.i(Complementos.TAG_COFIG,"inicializar sesion");
        ISettingDAO settings = new SettingDAO(Settings.CONTEXT);
        boolean res = true;
        settings.leerPorId(1);

        if(Settings.DATE == null){
            //inicializar settings
            Settings.DATE = new Date();
            Settings.FECHA = Complementos.convertirDateAstring(Settings.DATE);
            Settings.URL = "";
            Settings.USUARIO = "";
            Settings.FIN_JORNADA=0;

            settings.guardar(null);

        }

        return res;
    }

    public boolean actualizarInicioSesion(){
        FileLog.i(Complementos.TAG_COFIG,"actualizar sesion");
        String usuarioActual = Settings.USUARIO;

        Date dateInicio = Settings.DATE;
        String fechaInicio =Settings.FECHA;
        int jornada = Settings.FIN_JORNADA;

        Date actual = new Date();
        String fechaActual = Complementos.convertirDateAstring(actual,"dd/MM/yyyy");

        if(fechaInicio.equals(fechaActual))
            return true;

        Settings.DATE = actual;
        Settings.FECHA = fechaActual;
        Settings.USUARIO = "";
        Settings.FIN_JORNADA = 0;

        boolean b = new SettingDAO(Settings.CONTEXT).updateFecha();
        if(!b){
            Settings.DATE = dateInicio;
            Settings.FECHA = fechaInicio;
            Settings.USUARIO = usuarioActual;
            Settings.FIN_JORNADA = jornada;
            return false;
        }

        return true;
    }



    public static boolean inicializarCatalogos(){

        TrabajadoresControl.getTrabajadores(Settings.CONTEXT);

        return true;
    }

    public static boolean validarCatalogos(){
        FileLog.e(Complementos.TAG_COFIG, "validar catalogos cargados");
        int trabajadores = TrabajadoresControl.getTotalTrabajadores(Settings.CONTEXT);
        int actividades = ActividadesControl.getTotalActividades(Settings.CONTEXT);
        int campos = CamposControl.getTotalCampos(Settings.CONTEXT);
        int tablas = TablasControl.getTotalTablas(Settings.CONTEXT);
        int productos = ProductosControl.getTotalProductos(Settings.CONTEXT);
        int etapas = EtapasControl.getTotalEtapas(Settings.CONTEXT);
        int cce = CceControl.getTotalCce(Settings.CONTEXT);


        if(trabajadores>0 && actividades >0 && campos>0 && tablas >0&& productos >0&& etapas >0&& cce >0)
            return true;

        return false;
    }

    public static boolean validarConfiguraciones(){

        if(Settings.MAILS.equals("") || Settings.URL.equals("")){
            FileLog.e(Complementos.TAG_COFIG, "falta agregar configuraciones");
            return false;
        }


        return true;
    }

    public static boolean getUsuario(){
        boolean res = true;

        if(Settings.USUARIO == null || Settings.USUARIO.equals(""))
            return false;

        return res;
    }

    public static boolean actualizarUsuario(String usuario){
        usuario = usuario.toUpperCase();
        String usuarioAnterior =Settings.USUARIO;
        if(usuario.equals("")){
            Exceptions.exception = new Exception(Settings.CONTEXT.getString(R.string.msn_Error_usuario));
            return false;
        }

        if(usuario.equals(usuarioAnterior))
            return true;

        Settings.USUARIO = usuario;
        ISettingDAO settings = new SettingDAO(Settings.CONTEXT);
        boolean b = settings.updateUsuario();
        if(!b){
            Settings.USUARIO = usuarioAnterior;
            return false;
        }

        return true;
    }

    public static boolean actualizarUrlServidor(String url){

        if(url.equals("")){
            Exceptions.exception = new Exception(Settings.CONTEXT.getString(R.string.msn_Error_url));
            return false;
        }

        String ant =Settings.URL;
        Settings.URL = url;
        boolean b = new SettingDAO(Settings.CONTEXT).updateUrl();
        if(!b){
            Settings.URL = ant;
            return false;
        }


        return true;
    }

    public static boolean actualizarMails(String mails){

        if(mails.equals("")){
            Exceptions.exception = new Exception(Settings.CONTEXT.getString(R.string.msn_Error_mails));
            return false;
        }

        Settings.MAILS = mails;
        boolean b = new SettingDAO(Settings.CONTEXT).updateMail();
        if(!b)
            return false;

        return true;
    }


    public static boolean validarSesion(){
        //0=jornada Iniciada, 1=Jornada finalizada
        if(Settings.FIN_JORNADA == 1)
            return false;

        return true;
    }

    public static boolean finalizarSesion(){
        FileLog.e(Complementos.TAG_COFIG, "finalizar setting "+Settings.valor());
        Settings.FIN_JORNADA = 1;
        boolean b = new SettingDAO(Settings.CONTEXT).updateJornada();
        if(!b)
            return false;

        return true;
    }
}

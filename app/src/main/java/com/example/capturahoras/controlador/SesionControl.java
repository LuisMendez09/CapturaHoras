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
        ISettingDAO settingsDAO = new SettingDAO(Settings.CONTEXT);
        boolean res = true;
        Settings settings = settingsDAO.leerPorId(1);

        if(settings.getDATE() == null){
            //inicializar settings
            FileLog.i(Complementos.TAG_COFIG,"crear registro en la base de datos SETTING");
            settings.setDATE(new Date());
            settings.setFECHA(Complementos.convertirDateAstring2(settings.getDATE()));
            settings.setUSUARIO("");
            settings.setUSUARIO("");
            settings.setFIN_JORNADA(0);

            settingsDAO.guardar(settings);
        }

        return res;
    }

    public boolean actualizarInicioSesion(){
        FileLog.i(Complementos.TAG_COFIG,"actualizar actualizarInicioSesion");
        Settings settings = Settings.getInstanacia();
        String usuarioActual = settings.getUSUARIO();

        Date dateInicio = settings.getDATE();
        String fechaInicio =settings.getFECHA();
        int jornada = settings.getFIN_JORNADA();

        Date actual = new Date();
        String fechaActual = Complementos.convertirDateAstring(actual,"dd/MM/yyyy");

        FileLog.i(Complementos.TAG_COFIG,"Fecha setting "+fechaInicio+"--actual "+fechaActual);
        if(fechaInicio.equals(fechaActual))
            return true;


        settings.setDATE(actual);
        settings.setFECHA(fechaActual);
        settings.setUSUARIO("");
        settings.setFIN_JORNADA(0);

        boolean b = new SettingDAO(Settings.CONTEXT).updateFecha(settings.getDATE(),settings.getFECHA());
        FileLog.i(Complementos.TAG_COFIG,"respuesta "+b);
        if(b)
            b = new SettingDAO(Settings.CONTEXT).updateJornada(settings.getFIN_JORNADA());

        if(!b){
            FileLog.i(Complementos.TAG_COFIG,"error actualizar sesion");
            settings.setDATE(dateInicio);
            settings.setFECHA(fechaInicio);
            settings.setUSUARIO(usuarioActual);
            settings.setFIN_JORNADA(jornada);
            return false;
        }

        return true;
    }

    public static boolean inicializarCatalogos(){
        FileLog.i(Complementos.TAG_COFIG,"inicializarCatalogos");
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

        FileLog.e(Complementos.TAG_COFIG, "validarConfiguraciones");
        Settings settings = Settings.getInstanacia();
        if(settings.getMAILS().equals("") || settings.getURL().equals("")){
            FileLog.e(Complementos.TAG_COFIG, "falta agregar configuraciones");
            return false;
        }


        return true;
    }

    public static boolean getUsuario(){
        FileLog.e(Complementos.TAG_COFIG, "validarUsuario");
        boolean res = true;
        Settings settings = Settings.getInstanacia();
        if(settings.getUSUARIO() == null || settings.getUSUARIO().equals(""))
            return false;

        return res;
    }

    public static boolean actualizarUsuario(String usuario){
        FileLog.e(Complementos.TAG_COFIG, "actualizarUsuario "+usuario);
        usuario = usuario.toUpperCase();
        Settings settings = Settings.getInstanacia();
        String usuarioAnterior =settings.getUSUARIO();
        if(usuario.equals("")){
            Exceptions.exception = new Exception(Settings.CONTEXT.getString(R.string.msn_Error_usuario));
            FileLog.e(Complementos.TAG_COFIG, "actualizarUsuario "+Settings.CONTEXT.getString(R.string.msn_Error_usuario));
            return false;
        }

        if(usuario.equals(usuarioAnterior))
            return true;

        settings.setUSUARIO(usuario);
        ISettingDAO settingsDAO = new SettingDAO(Settings.CONTEXT);
        boolean b = settingsDAO.updateUsuario(usuario);
        if(!b){
            settings.setUSUARIO(usuarioAnterior);
            return false;
        }

        return true;
    }

    public static boolean actualizarUrlServidor(String url){
        FileLog.e(Complementos.TAG_COFIG, "actualizarUrlServidor "+url);
        Settings settings = Settings.getInstanacia();
        if(url.equals("")){
            Exceptions.exception = new Exception(Settings.CONTEXT.getString(R.string.msn_Error_url));
            return false;
        }

        String ant =settings.getURL();
        settings.setURL(url);
        boolean b = new SettingDAO(Settings.CONTEXT).updateUrl(url);
        if(!b){
            settings.setURL(ant);
            return false;
        }


        return true;
    }

    public static boolean actualizarMails(String mails){
        FileLog.e(Complementos.TAG_COFIG, "actualizarMails "+mails);
        Settings settings = Settings.getInstanacia();
        if(mails.equals("")){
            Exceptions.exception = new Exception(Settings.CONTEXT.getString(R.string.msn_Error_mails));
            return false;
        }

        String ant = settings.getMAILS();

        settings.setMAILS(mails);
        boolean b = new SettingDAO(Settings.CONTEXT).updateMail(mails);
        if(!b){
            settings.setMAILS(ant);
            return false;
        }

        return true;
    }


    public static boolean validarSesion(){
        FileLog.e(Complementos.TAG_COFIG, "validarSesion ");
        Settings settings = Settings.getInstanacia();
        //0=jornada Iniciada=true, 1=Jornada finalizada=false
        if(settings.getFIN_JORNADA() == 1)
            return false;

        return true;
    }

    public static boolean finalizarSesion(){
        Settings settings = Settings.getInstanacia();

        FileLog.e(Complementos.TAG_COFIG, "finalizar setting "+settings.valor());
        int ant = settings.getFIN_JORNADA();

        settings.setFIN_JORNADA(1);
        boolean b = new SettingDAO(Settings.CONTEXT).updateJornada(1);
        if(!b){
            FileLog.e(Complementos.TAG_COFIG, "Jornada no finalizada");
            settings.setFIN_JORNADA(ant);
            return false;
        }


        return true;
    }
}

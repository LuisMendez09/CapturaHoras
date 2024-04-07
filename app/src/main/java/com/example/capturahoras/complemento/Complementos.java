package com.example.capturahoras.complemento;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Spinner;

import java.io.File;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Complementos {
    public static final String TAG_MAIN="MainActivity";
    public static final String TAG_INICIO="Cortina";
    public static final String TAG_BDHANDLER = "DBHandler";
    public static final String DEBUG_CONTEXT = "DatabaseContext";
    public static final String TAG_CONTROLADOR = "Controlador";
    public static final String TAG_PRODUCCION = "Asistencia";
    public static final String TAG_CATALOGOS="DescargaCatalogos";
    public static final String TAG_LOGIN ="Login";
    public static final String TAG_COFIG = "Configuracion";
    public static final String TAG_SETTINGS = "setting";
    public static final String TAG_HOME = "Home";
    public static final String TAG_CAPTURA = "captura";

    public  static File rutaAlmacenamiento(Context context){
        File storageDir = null;
        File[] temp2 = null;
        File temp = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            temp2 = context.getExternalMediaDirs();
        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                temp2 = context.getExternalFilesDirs("");
            } else{
                temp = context.getExternalFilesDir("");
                if(temp == null)
                    temp = context.getDatabasePath("");
            }
        }

        if(temp != null){
            storageDir = temp;
        }

        if(temp2 != null){
            if(temp2[temp2.length-1]==null){
                storageDir = temp2[0];
            }else{
                storageDir = temp2[temp2.length - 1];
            }
        }

        return storageDir;
    }

    public static int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }

    public static Long getDateTimeActual(){
        return new Date().getTime();
    }

public static String getTotalHoras(Date timeI, Date timeF){
Log.i("nuevo",timeI+" ---- "+timeF);
        if(timeF.getTime()!= Long.valueOf(0)){

            Long dif = timeF.getTime() - timeI.getTime();
            Long horas,minutos,segundos;

            segundos = dif/1000;
            minutos = segundos / 60;
            segundos = segundos % 60;
            horas = minutos / 60;
            minutos = minutos % 60;

            return String.format("%02d", horas)+":"+String.format("%02d", minutos);
        }
        return "00:00";
}

    public static Date getDateActual(){
        return new Date();
    }

    public static String getDateActualToString(){
        Format formatter = new SimpleDateFormat("dd/MM/yyyy");

        return formatter.format(new Date());
    }

    public static String getDateActualToStringServidor(){
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");

        return formatter.format(new Date());
    }

    public static Long convertirStringAlong(String fecha,String hora) throws ParseException {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return (sdf.parse(fecha+" "+hora)).getTime();
    }



/*
    public static Long convertirStringSegundosAlong(String fecha,String hora) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Long time = (sdf.parse(fecha+" "+hora)).getTime();
        return time;
    }*/

    public static final String convertirDateAstring(Date fecha){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String fechaComoCadena = sdf.format(fecha);
        return fechaComoCadena;
    }

    public static final String convertirDateAstring2(Date fecha){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String fechaComoCadena = sdf.format(fecha);
        return fechaComoCadena;
    }

    public static String fechaInicioSemana() {
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);


        while (c.get(Calendar.DAY_OF_WEEK) != Calendar.THURSDAY) {
            Log.i("enviar", c.get(Calendar.DAY_OF_WEEK) + "----");
            c.add(Calendar.DAY_OF_YEAR, -1);
        }
        return obtenerFechaServidor(c.getTime());
    }

    public static String fechaFinSemana() {
        Date d = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(d);

        while (c.get(Calendar.DAY_OF_WEEK) != Calendar.WEDNESDAY) {
            c.add(Calendar.DAY_OF_YEAR, 1);
        }

        return obtenerFechaServidor(c.getTime());
    }

    public static String obtenerFechaServidor(Date fecha) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return sdf.format(fecha);
    }

    public static String obtenerHoraString(Date hora){

        if(hora.getTime()!=Long.valueOf(0)){
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

            return sdf.format(hora);
        }else{
            return "";
        }

    }

    public static final String convertirDateAstring(Date fecha,String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String fechaComoCadena = sdf.format(fecha);
        return fechaComoCadena;
    }
}

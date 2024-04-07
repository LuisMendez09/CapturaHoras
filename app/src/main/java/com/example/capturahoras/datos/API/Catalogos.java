package com.example.capturahoras.datos.API;

import com.example.capturahoras.complemento.Complementos;
import com.example.capturahoras.controlador.CamposControl;
import com.example.capturahoras.datos.API.ActividadesAPI.IActividadesAPIService;
import com.example.capturahoras.datos.API.AsistenciaAPI.EndpointAsistencia;
import com.example.capturahoras.datos.API.AsistenciaAPI.EnpointCamposTrabajados;
import com.example.capturahoras.datos.API.CCE_API.ICceAPIService;
import com.example.capturahoras.datos.API.CamposAPI.ICamposAPIService;
import com.example.capturahoras.datos.API.EtapasAPI.IEtapasAPIService;
import com.example.capturahoras.datos.API.ProductosAPI.IProductosAPIService;
import com.example.capturahoras.datos.API.TablaProrrateo.ITablaProrrateoAPIService;
import com.example.capturahoras.datos.API.TrabajadoresAPI.ITrabajadoresAPIService;
import com.example.capturahoras.modelo.Actividades;
import com.example.capturahoras.modelo.Asistencia;
import com.example.capturahoras.modelo.CCE;
import com.example.capturahoras.modelo.Campos;
import com.example.capturahoras.modelo.CamposTrabajados;
import com.example.capturahoras.modelo.Etapas;
import com.example.capturahoras.modelo.ModelAPI.AsistrnciaPOST;
import com.example.capturahoras.modelo.Productos;
import com.example.capturahoras.modelo.Settings;
import com.example.capturahoras.modelo.TablasProrrateo;
import com.example.capturahoras.modelo.Trabajadores;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Catalogos {
    private List resultado = null;
    private Exception exception;


    public List<Object> getResult(){return resultado;}

    public Exception getException() {
        return exception;
    }

    public boolean buscarTrabajadores() {
        exception = null;
        resultado = new ArrayList<>();

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Settings.URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        try {
            ITrabajadoresAPIService trabajadoresAPIService = retrofit.create(ITrabajadoresAPIService.class);
            Call<List<Trabajadores>> call = trabajadoresAPIService.getTrabajadores();

            Response<List<Trabajadores>> execute = call.execute();
            resultado = execute.body();
        } catch (Exception e) {
            e.printStackTrace();
            exception = e;
            return false;
        }

        return  true;
    }

    public boolean buscarActividades() {
        exception = null;
        resultado = new ArrayList<>();

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Settings.URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())

                .build();

        try {
            IActividadesAPIService actividadesAPIService = retrofit.create(IActividadesAPIService.class);
            Call<List<Actividades>> call = actividadesAPIService.getActividades();

            Response<List<Actividades>> execute = call.execute();
            resultado= execute.body();
        } catch (Exception e) {
            e.printStackTrace();
            exception = e;
            return false;
        }

        return  true;
    }

    public boolean buscarCampos() {
        exception = null;
        resultado = new ArrayList<>();

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Settings.URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        try {
            ICamposAPIService camposAPIService = retrofit.create(ICamposAPIService.class);
            Call<List<Campos>> call = camposAPIService.getCampos();

            Response<List<Campos>> execute = call.execute();
            resultado= execute.body();
        } catch (Exception e) {
            e.printStackTrace();
            exception = e;
            return false;
        }

        return  true;
    }

    public boolean buscarTablaProrrateo() {
        exception = null;
        resultado = new ArrayList<>();

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Settings.URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        try{
            ITablaProrrateoAPIService API = retrofit.create(ITablaProrrateoAPIService.class);
            Call<List<TablasProrrateo>> call = API.getTablasProrateo();

            try {
                Response<List<TablasProrrateo>> execute = call.execute();
                resultado= execute.body();
            } catch (IOException e) {
                e.printStackTrace();
                exception = e;
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            exception = e;
            return false;
        }
        return  true;
    }

    public boolean buscarProductos() {
        exception = null;
        resultado = new ArrayList<>();

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Settings.URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        try{
            IProductosAPIService API = retrofit.create(IProductosAPIService.class);
            Call<List<Productos>> call = API.geProductos();

            try {
                Response<List<Productos>> execute = call.execute();
                resultado= execute.body();
            } catch (IOException e) {
                e.printStackTrace();
                exception = e;
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            exception = e;
            return false;
        }
        return  true;
    }

    public boolean buscarEtapas() {
        exception = null;
        resultado = new ArrayList<>();

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Settings.URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        try{
            IEtapasAPIService API = retrofit.create(IEtapasAPIService.class);
            Call<List<Etapas>> call = API.geEtapas();

            try {
                Response<List<Etapas>> execute = call.execute();
                resultado= execute.body();
            } catch (IOException e) {
                e.printStackTrace();
                exception = e;
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            exception = e;
            return false;
        }
        return  true;
    }

    public boolean buscarCCE() {
        exception = null;
        resultado = new ArrayList<>();

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Settings.URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        try{
            ICceAPIService API = retrofit.create(ICceAPIService.class);
            Call<List<CCE>> call = API.getCce();

            try {
                Response<List<CCE>> execute = call.execute();
                resultado= execute.body();
            } catch (IOException e) {
                e.printStackTrace();
                exception = e;
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            exception = e;
            return false;
        }
        return  true;
    }

    public boolean enviarAsistencias(List<Asistencia> asistencias) {
        exception = null;
        resultado = new ArrayList<>();

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Settings.URL)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        for (Asistencia a : asistencias) {
            AsistrnciaPOST asistrnciaPOST = new AsistrnciaPOST();
            asistrnciaPOST.setDispositivo(a.getDispositivo());
            asistrnciaPOST.setId_trabajador(a.getTrabajador().getNumero());
            asistrnciaPOST.setTotal_horas(a.getTotalHoras());
            asistrnciaPOST.setFecha(Complementos.obtenerFechaServidor(new Date(a.getFecha())));
            asistrnciaPOST.setHoraInicio(Complementos.obtenerHoraString(new Date(a.getHoraInicial())));

            String s = Complementos.obtenerHoraString(new Date(a.getHoraFinal()));
            if(s.equals(""))
                s = null;
            asistrnciaPOST.setHoraFinal(s);
            try{
                EndpointAsistencia API = retrofit.create(EndpointAsistencia.class);
                Call<AsistrnciaPOST> call = API.Postasistencia(asistrnciaPOST);
                try {
                    Response<AsistrnciaPOST> execute = call.execute();
                    asistrnciaPOST = execute.body();
                    if(asistrnciaPOST != null){
                        //a.setId(asistrnciaPOST.getId());
                        Collection<CamposTrabajados> values = a.getCamposTrabajados().values();
                        enviarCamposTrabajados(values,asistrnciaPOST.getId());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    exception = e;
                    return false;
                }
            }catch (Exception e){
                e.printStackTrace();
                exception = e;
                return false;
            }
        }
        return  true;
    }

    public boolean enviarCamposTrabajados(Collection<CamposTrabajados> ct, int idAsistencia) {
        exception = null;
        resultado = new ArrayList<>();

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Settings.URL)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        for (CamposTrabajados a : ct) {

            try{
                EnpointCamposTrabajados API = retrofit.create(EnpointCamposTrabajados.class);
                List<JsonObject> json = a.getJson(idAsistencia);

                for (JsonObject j :json) {
                    Call<Void> call = API.PostCamposTrabajados(j.toString());
                    try {
                        Response<Void> execute = call.execute();
                        execute.body();
                    } catch (IOException e) {
                        e.printStackTrace();
                        exception = e;
                        return false;
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                exception = e;
                return false;
            }
        }
        return  true;
    }

    public boolean actualziarCampos(List<Campos> campos) {
        exception = null;
        resultado = new ArrayList<>();

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Settings.URL)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        for (Campos c : campos) {
            try{
                if(c.getEtapaSeleccionada()== null && c.getProductoSeleccionado()== null && c.getCceSeleccionada()== null)
                    continue;

                ICamposAPIService API = retrofit.create(ICamposAPIService.class);
                String s = c.getJson().toString();
                Call<Void> call = API.PutCampos(c.getClave(),s);
                try {
                    Response<Void> execute = call.execute();
                    execute.body();
                } catch (IOException e) {
                    e.printStackTrace();
                    exception = e;
                    return false;
                }
            }catch (Exception e){
                e.printStackTrace();
                exception = e;
                return false;
            }
        }

        return  true;
    }
}

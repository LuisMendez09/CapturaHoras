package com.example.capturahoras.datos.API.ActividadesAPI;

import com.example.capturahoras.modelo.Actividades;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IActividadesAPIService {
    @GET("actividades")
    Call<List<Actividades>> getActividades();
}

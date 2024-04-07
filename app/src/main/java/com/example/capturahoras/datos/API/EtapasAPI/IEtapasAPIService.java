package com.example.capturahoras.datos.API.EtapasAPI;

import com.example.capturahoras.modelo.Etapas;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IEtapasAPIService {
    @GET("Etapas")
    Call<List<Etapas>> geEtapas();
}

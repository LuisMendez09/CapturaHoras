package com.example.capturahoras.datos.API.CCE_API;

import com.example.capturahoras.modelo.CCE;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ICceAPIService {
    @GET("CCEs")
    Call<List<CCE>> getCce();
}

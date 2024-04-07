package com.example.capturahoras.datos.API.TablaProrrateo;

import com.example.capturahoras.modelo.TablasProrrateo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ITablaProrrateoAPIService {
    @GET("tablasProrateo")
    Call<List<TablasProrrateo>> getTablasProrateo();
}

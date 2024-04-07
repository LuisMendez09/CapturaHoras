package com.example.capturahoras.datos.API.AsistenciaAPI;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface EnpointCamposTrabajados {
    @Headers("Content-Type: application/json")
    @POST("camposTrabajados")
    Call<Void> PostCamposTrabajados(@Body String camposTrabajados);
}

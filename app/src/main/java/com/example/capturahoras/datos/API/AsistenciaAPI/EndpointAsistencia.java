package com.example.capturahoras.datos.API.AsistenciaAPI;

import com.example.capturahoras.modelo.Asistencia;
import com.example.capturahoras.modelo.ModelAPI.AsistrnciaPOST;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface EndpointAsistencia {
    @Headers("Content-Type: application/json")
    @POST("asistencias")
    Call<AsistrnciaPOST> Postasistencia(@Body AsistrnciaPOST asistencia);


}


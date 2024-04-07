package com.example.capturahoras.datos.API.CamposAPI;

import com.example.capturahoras.modelo.Campos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ICamposAPIService {
    @GET("campos")
    Call<List<Campos>> getCampos();
    @Headers("Content-Type: application/json")
    @PUT("campos/{id}/")
    Call<Void> PutCampos(@Path("id") int clave, @Body String camposTrabajados);
}

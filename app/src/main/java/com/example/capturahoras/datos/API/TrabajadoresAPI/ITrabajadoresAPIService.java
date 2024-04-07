package com.example.capturahoras.datos.API.TrabajadoresAPI;

import com.example.capturahoras.modelo.Trabajadores;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ITrabajadoresAPIService {
    @GET("trabajadores")
    Call<List<Trabajadores>> getTrabajadores();

}

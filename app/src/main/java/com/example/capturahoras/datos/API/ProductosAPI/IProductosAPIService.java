package com.example.capturahoras.datos.API.ProductosAPI;

import com.example.capturahoras.modelo.Productos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IProductosAPIService {
    @GET("Productos")
    Call<List<Productos>> geProductos();
}

package com.example.capturahoras.datos.API.ActividadesAPI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ActividadesResult {
    @SerializedName("results")
    @Expose
    private ArrayList results;

    public ArrayList getResults(){
        return results;
    }
}

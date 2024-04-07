package com.example.capturahoras.controlador;

import android.content.Context;

import com.example.capturahoras.datos.Productos.DatosProductoDAO;
import com.example.capturahoras.modelo.Productos;

import java.util.List;

public class ProductosControl {
    public static List<Productos> getProductos(Context c){

        return  new DatosProductoDAO(c).listarActivos();
    }

    public static CharSequence[] getProductosArray(Context c){
        List<Productos> productos = new DatosProductoDAO(c).listarActivos();
        CharSequence[] prod = new CharSequence[productos.size()];
        for (int i = 0; i < productos.size(); i++) {
            prod[i] = productos.get(i).getDescripcion();        }
        return  prod;
    }

    public static Productos getProductos(Context c,String nombreProducto){
        return new DatosProductoDAO(c).leerPorDescripcion(nombreProducto);
    }

    public static int getTotalProductos(Context c){
        return new DatosProductoDAO(c).listarActivos().size();
    }
}

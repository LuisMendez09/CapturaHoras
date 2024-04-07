package com.example.capturahoras.ui.detalle;



import com.example.capturahoras.modelo.CCE;
import com.example.capturahoras.modelo.Etapas;
import com.example.capturahoras.modelo.Productos;

public interface OnItemSelecctionListener {
    void onItemsSelectProducto(Productos productos);
    void onItemsSelectEtapa(Etapas etapas);
    void onItemsSelectCce(CCE cce);
}

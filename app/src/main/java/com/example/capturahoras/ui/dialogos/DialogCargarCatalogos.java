package com.example.capturahoras.ui.dialogos;

import com.example.capturahoras.controlador.SesionControl;

public class DialogCargarCatalogos {
    public interface IDialogo{
        void finalizarDialog(boolean resultado);
    }

    private IDialogo dialogo;

    public DialogCargarCatalogos(IDialogo dialogo){
        this.dialogo = dialogo;

        new Thread(()->{
            //codigo a ejecutar
            boolean b = SesionControl.inicializarCatalogos();
            this.dialogo.finalizarDialog(b);
        }).start();
    }

}

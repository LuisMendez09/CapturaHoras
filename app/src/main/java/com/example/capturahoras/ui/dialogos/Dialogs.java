package com.example.capturahoras.ui.dialogos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.TextView;

import com.example.capturahoras.R;
import com.example.capturahoras.modelo.Settings;
import com.example.capturahoras.ui.SeleccionItem;


public class Dialogs {
    private static String valor;

    public static final void DialogError(String msn) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Settings.CONTEXT);
        CharSequence cs = msn;
        builder.setMessage(cs)
               .setTitle(R.string.msn_titulo_error)
        .setNegativeButton(R.string.btn_cerrar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static final void DialogSeleccion(TextView textView, SeleccionItem Iposicion, CharSequence[] datos) {

        AlertDialog.Builder builder = new AlertDialog.Builder(Settings.CONTEXT);
        final int[] pisicion = {0};

        builder.setTitle(R.string.msn_titulo_CapturaTiposVehiculos);
        builder.setSingleChoiceItems(datos, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                valor = datos[i].toString();
                pisicion[0] =i;
            }
        });

        builder.setPositiveButton(R.string.btn_seleccionar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                textView.setText(valor);
                Iposicion.itemSeleccionados(pisicion[0],textView.getId());
                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }



}

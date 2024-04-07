package com.example.capturahoras.ui.dialogos;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;

import com.example.capturahoras.complemento.FileLog;
import com.example.capturahoras.R;
import com.example.capturahoras.complemento.Complementos;
import com.example.capturahoras.complemento.FileLog;
import com.example.capturahoras.controlador.ImportarDatos;

import org.jetbrains.annotations.NotNull;

public class DialogsActualizarCatalogo extends DialogFragment implements DialogoProgreso.actualizacionCatalogos{

    Button btn_actualizarVehiculos;
    Button btn_actualizarCombustibles;
    View view;

    @SuppressLint("InflateParams")
    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)  {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        FileLog.i(Complementos.TAG_CATALOGOS,"inicia el dialog de actualziacion de catalogos");
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        view = inflater.inflate(com.example.capturahoras.R.layout.dialog_actualizar_catalogos, null);
        btn_actualizarVehiculos = (Button) view.findViewById(R.id.btn_cat_vehiculos);
        btn_actualizarCombustibles = (Button) view.findViewById(R.id.btn_cat_tiposCombustibles);

        builder.setTitle(R.string.msn_titulo_actualziarCatalogos)
                .setView(view)
                .setPositiveButton(R.string.btn_cerrar, null);

        btn_actualizarVehiculos.setOnClickListener(view -> {
            new DialogoProgreso(getContext(),this, ImportarDatos.KEY_CATALOGOS,ImportarDatos.TRABAJADORES,this);
        });

        btn_actualizarCombustibles.setOnClickListener(view->{
            new DialogoProgreso(getContext(),this,ImportarDatos.KEY_CATALOGOS,ImportarDatos.CATALOGOS,this);
        });

        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();

        AlertDialog dialog = (AlertDialog) getDialog();

        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);//Al presionar atras no desaparece
    }

    @Override
    public void finilazacion(String error) {
        if(error.length()!=0){
            Dialogs.DialogError(error);
        }

    }
}

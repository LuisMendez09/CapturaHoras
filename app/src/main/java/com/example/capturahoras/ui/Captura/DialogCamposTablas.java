package com.example.capturahoras.ui.Captura;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.capturahoras.R;
import com.example.capturahoras.modelo.Actividades;
import com.example.capturahoras.modelo.CamposTrabajados;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;


public class DialogCamposTablas extends DialogFragment {
    ListView lv_campos;
    HashMap<String, CamposTrabajados> hct;
    CamposTablasAdaptar cta;

    private OnItemSelectListener listener;

    public void setOnItemSelectListener(OnItemSelectListener listener){
        this.listener = listener;
    }

    public void setCamposSeleccionados(HashMap<String, CamposTrabajados> hct){
        this.hct = hct;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View root = inflater.inflate(R.layout.dialog_select_campos, null);

        lv_campos = root.findViewById(R.id.lv_campos);
        List<CamposTrabajados> values = new ArrayList<>();
        for (CamposTrabajados trabajados : hct.values()) {
            values.add(trabajados);
        }

        cta = new CamposTablasAdaptar(getContext(), values);
        cta.setSupportFragmentManager(getActivity().getSupportFragmentManager());
        lv_campos.setAdapter(cta);

        builder.setTitle(R.string.msn_titulo_Selec_campos)
                .setView(root)
                .setPositiveButton(R.string.btn_acepatar, (dialogInterface, i) -> {
                    listener.onItemsSelectCamposTrabajados(hct);
                })
                .setNegativeButton(R.string.btn_cancelar, (dialogInterface, i) -> dismiss());


        AlertDialog alertDialog = builder.create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.show();

        return alertDialog;
    }
}

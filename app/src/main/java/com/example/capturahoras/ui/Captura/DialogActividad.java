package com.example.capturahoras.ui.Captura;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;

import com.example.capturahoras.R;
import com.example.capturahoras.controlador.ActividadesControl;

public class DialogActividad extends DialogFragment {
    TextView textView;
    public AlertDialog dialog;
    CharSequence[] actividades;
    AlertDialog.Builder builder;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View root = inflater.inflate(R.layout.dialog_actividades, null);
        textView = root.findViewById(R.id.et_filtroA);
        actividades = ActividadesControl.getActividadesArray(this.getContext());

        builder.setTitle(R.string.msn_titulo_Selec_trabajador)
                .setCustomTitle(root)
                .setMultiChoiceItems(actividades,null,(dialogInterface, i, b) -> {})
                .setNegativeButton(R.string.btn_cancelar, (dialogInterface, i) -> dismiss());

        textView.requestFocus(); //Asegurar que editText tiene focus

        dialog = builder.create();
        dialog.show();
        dialog.getWindow().clearFlags( WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                actividades = new CharSequence[]{actividades[0],actividades[1]};
                builder.setMultiChoiceItems(actividades,null,(dialogInterface, i3, b) -> {});
                builder.notifyAll();
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });

        return dialog;
    }
}

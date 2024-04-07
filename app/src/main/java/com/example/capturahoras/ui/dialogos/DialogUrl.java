package com.example.capturahoras.ui.dialogos;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.capturahoras.R;
import com.example.capturahoras.controlador.SesionControl;
import com.example.capturahoras.modelo.Settings;


public class DialogUrl extends DialogFragment {

    private EditText url;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_url,container,false);
        ((ImageButton) view.findViewById(R.id.btn_cerrar)).setOnClickListener(view1 -> dismissAllowingStateLoss());

        ((Button)view.findViewById(R.id.btn_guardar_settings)).setOnClickListener(view1 -> guardar());

        url = view.findViewById(R.id. et_url);
        url.setText(Settings.URL);
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog =super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        return dialog;
    }

    private void guardar(){
        String  servidor = url.getText().toString();

        //guardar la url
        Boolean r = SesionControl.actualizarUrlServidor(servidor);

        dismiss();
    }
}

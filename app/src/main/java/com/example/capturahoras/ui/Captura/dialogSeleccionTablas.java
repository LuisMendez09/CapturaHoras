package com.example.capturahoras.ui.Captura;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.capturahoras.R;
import com.example.capturahoras.controlador.TablasControl;
import com.example.capturahoras.modelo.TablasProrrateo;

import java.util.ArrayList;
import java.util.List;

public class dialogSeleccionTablas extends DialogFragment {
    private RadioGroup grupoRadios;
    private TextView tv_filtroA;
    private List<TablasProrrateo> tablas =new ArrayList<>();//todas las opciones}
    private TablasProrrateo tablaSeleccionada;

    private OnItemSelectListener listener;

    public void setOnItemSelectListener(OnItemSelectListener listener){
        this.listener = listener;
    }

    public void setTablaSeleccionadas(TablasProrrateo tablaSeleccionadas){
        List<TablasProrrateo> c = TablasControl.getTablas(this.getContext());
        this.tablas = c;

        if(tablaSeleccionadas == null){
            return;
        }

        this.tablaSeleccionada = tablaSeleccionadas;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View root = inflater.inflate(R.layout.dialog_seleccion_radiobutton, null);

        grupoRadios = root.findViewById(R.id.groupRadio);
        tv_filtroA = root.findViewById(R.id.et_filtro);

        agregarRadioButtom("");

        builder.setTitle(R.string.msn_titulo_Selec_campos)
                .setView(root)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(listener!=null){
                            listener.onItemsSelectTabla(tablaSeleccionada);
                            dismiss();
                        }
                        dismiss();
                    }
                })
                .setNegativeButton(R.string.btn_cancelar, (dialogInterface, i) -> dismiss());

        tv_filtroA.setEnabled(true);
        tv_filtroA.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                agregarRadioButtom(tv_filtroA.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.show();
        return alertDialog;
    }

    private void agregarRadioButtom(String filtro){

        grupoRadios.clearCheck();
        grupoRadios.removeAllViews();

        for (TablasProrrateo tp : tablas) {
            if(filtro.equals("") || tp.getDescripcion().toLowerCase().contains(filtro.toLowerCase())){
                RadioButton button = new RadioButton(this.getContext());
                button.setText(tp.getDescripcion());

                button.setChecked(false);

                button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                        if(!b)
                            return;

                        for (TablasProrrateo tp :tablas) {
                            if (tp.getDescripcion().equals(compoundButton.getText().toString())){
                                tablaSeleccionada = tp;
                            }
                        }
                    }
                });
                grupoRadios.addView(button);

            }
        }

        for (int i = 0; i < grupoRadios.getChildCount(); i++) {
            String title = ((RadioButton)grupoRadios.getChildAt(i)).getText().toString();
            if(tablaSeleccionada != null && title.equals(tablaSeleccionada.getDescripcion()))
                ((RadioButton) grupoRadios.getChildAt(i)).setChecked(true);
        }
    }
}

package com.example.capturahoras.ui.Captura;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capturahoras.R;
import com.example.capturahoras.controlador.ActividadesControl;
import com.example.capturahoras.controlador.CamposControl;
import com.example.capturahoras.modelo.Actividades;
import com.example.capturahoras.modelo.Campos;

import java.util.List;

public class DialogSeleccionActividad extends DialogFragment {
    private RecyclerView lv_Actividades;
    private TextView tv_filtroA;
    private List<Actividades> actividades;

    private ActividadesAdapter actividadesAdapter;
    private OnItemSelectListener listener;

    public void setOnItemSelectListener(OnItemSelectListener listener){
        this.listener = listener;
    }

    public void setActividadesSeleccionadas(List<Actividades> actividadesSeleccionadas){
        List<Actividades> a = ActividadesControl.getActividades(this.getContext());
        this.actividades = a;

        if(actividadesSeleccionadas == null){
            return;
        }

        for (Actividades act : this.actividades) {
            for (Actividades select : actividadesSeleccionadas) {
                if(act.getClave()== select.getClave())
                    act.setSelect(select.isSelect());
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View root = inflater.inflate(R.layout.dialog_seleccion_actividad, null);

        lv_Actividades=root.findViewById(R.id.lv_activdades);
        tv_filtroA = root.findViewById(R.id.et_filtroA);

        builder.setTitle(R.string.msn_titulo_Selec_actividad)
                .setView(root)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        actividades = actividadesAdapter.getActividadesSelecc();
                        if(listener!=null){
                            dismiss();
                                listener.onItemsSelectActividad(actividades);
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
                if(actividadesAdapter!=null)
                    actividadesAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        InicializarLista();

        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(R.color.segundo);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                Button negativeButton = ((AlertDialog)dialog).getButton(DialogInterface.BUTTON_NEGATIVE);
                Button positiveButton = ((AlertDialog)dialog).getButton(DialogInterface.BUTTON_POSITIVE);

                // this not working because multiplying white background (e.g. Holo Light) has no effect
                //negativeButton.getBackground().setColorFilter(0xFFFF0000, PorterDuff.Mode.MULTIPLY);

               /* final Drawable negativeButtonDrawable = getResources().getDrawable(R.drawable.boton_negativo);
                final Drawable positiveButtonDrawable = getResources().getDrawable(R.drawable.boton_negativo);
                if (Build.VERSION.SDK_INT >= 16) {
                    negativeButton.setBackground(negativeButtonDrawable);
                    positiveButton.setBackground(positiveButtonDrawable);
                } else {
                    negativeButton.setBackgroundDrawable(negativeButtonDrawable);
                    positiveButton.setBackgroundDrawable(positiveButtonDrawable);
                }*/
                negativeButton.setTextColor(Color.rgb(235,50,55));
                positiveButton.setTextColor(Color.rgb(255,255,255));

                negativeButton.invalidate();
                positiveButton.invalidate();
            }
        });

        alertDialog.show();
        return alertDialog;
    }

    private void InicializarLista(){
        if(actividades.size()==0)
            actividades = ActividadesControl.getActividades(this.getContext());

        actividadesAdapter = new ActividadesAdapter(actividades);
        lv_Actividades.setAdapter(actividadesAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        lv_Actividades.setLayoutManager(layoutManager);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        lv_Actividades.addItemDecoration(itemDecoration);

    }
}

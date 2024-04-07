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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capturahoras.R;
import com.example.capturahoras.controlador.CamposControl;
import com.example.capturahoras.modelo.Campos;

import java.util.ArrayList;
import java.util.List;

public class DialogSeleccionCampos extends DialogFragment {
    private RecyclerView lv_Actividades;
    private TextView tv_filtroA;
    private List<Campos> campos = new ArrayList<>();

    private CamposAdapter camposAdapter;
    private OnItemSelectListener listener;

    public void setOnItemSelectListener(OnItemSelectListener listener){
        this.listener = listener;
    }

    public void setCamposSeleccionadas(List<Campos> camposSeleccionadas){
        List<Campos> c = CamposControl.getCampos(this.getContext());
        this.campos = c;

        if(camposSeleccionadas == null){
            return;
        }

        for (Campos cam : this.campos) {
            for (Campos select : camposSeleccionadas) {
                if(cam.getClave()== select.getClave())
                    cam.setSelect(select.isSelect());
            }
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View root = inflater.inflate(R.layout.dialog_seleccion_campos, null);

        lv_Actividades=root.findViewById(R.id.lv_campos);
        tv_filtroA = root.findViewById(R.id.et_filtro);

        builder.setTitle(R.string.msn_titulo_Selec_campos)
                .setView(root)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        campos = camposAdapter.getCamposSelecc();
                        if(listener!=null){
                            dismiss();
                            listener.onItemsSelectCampo(campos);
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
                if(camposAdapter !=null)
                    camposAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        InicializarLista();

        AlertDialog alertDialog = builder.create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.show();
        return alertDialog;
    }

    private void InicializarLista(){
        if(campos.size()==0)
            campos = CamposControl.getCampos(this.getContext());

        camposAdapter = new CamposAdapter(campos);
        lv_Actividades.setAdapter(camposAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        lv_Actividades.setLayoutManager(layoutManager);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        lv_Actividades.addItemDecoration(itemDecoration);

    }

    public List<Campos> getCampos(){
        return campos;
    }
}

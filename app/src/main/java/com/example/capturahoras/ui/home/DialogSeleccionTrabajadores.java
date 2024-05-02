package com.example.capturahoras.ui.home;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capturahoras.R;
import com.example.capturahoras.complemento.Complementos;
import com.example.capturahoras.complemento.FileLog;
import com.example.capturahoras.controlador.AsistenciaControl;
import com.example.capturahoras.datos.Trabajadores.DatosTrabajadoresDAO;
import com.example.capturahoras.modelo.Asistencia;
import com.example.capturahoras.modelo.Trabajadores;

import java.util.List;

public class DialogSeleccionTrabajadores extends DialogFragment {
    private TrabajadoresAdaptar trabajadoresAdapter;

    private EditText et_trabajador;
    private RecyclerView lv_trabajadores;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View root = inflater.inflate(R.layout.dialog_trabajadores, null);

        lv_trabajadores = root.findViewById(R.id.lt_restultadosTrabajadores);
        et_trabajador = root.findViewById(R.id.et_filtroT);

        builder.setTitle(R.string.msn_titulo_Selec_trabajador)
                .setView(root)
                .setNegativeButton(R.string.btn_cancelar, (dialogInterface, i) -> dismiss());

        et_trabajador.setEnabled(true);
        et_trabajador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(trabajadoresAdapter!=null)
                    trabajadoresAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_trabajador.setOnKeyListener((view, keyCode, event) -> {
            // If the event is a key-down event on the "enter" button
            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                seleccionarTrabajadors(0);
                return true;
            }
            return false;
        });

        inicializarRecyclerView();
        AlertDialog alertDialog = builder.create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                Button negativeButton = ((AlertDialog)dialog).getButton(DialogInterface.BUTTON_NEGATIVE);

                negativeButton.setTextColor(Color.rgb(235,50,55));

                negativeButton.invalidate();
            }
        });
        alertDialog.show();
        return alertDialog;
    }

    @Override
    public void onStart() {
        super.onStart();

        //Obtenemos el AlertDialog
        AlertDialog dialog = (AlertDialog) getDialog();

        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);//Al presionar atras no desaparece

        //Implementamos el listener del boton OK para mostrar el toast
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                dismiss();
        });
    }

    //@RequiresApi(api = Build.VERSION_CODES.N)
    private void inicializarRecyclerView(){
        List<Trabajadores> trabajadores = new DatosTrabajadoresDAO(this.getContext()).listarActivos();

        trabajadoresAdapter = new TrabajadoresAdaptar(trabajadores);
        lv_trabajadores.setAdapter(trabajadoresAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        lv_trabajadores.setLayoutManager(layoutManager);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        lv_trabajadores.addItemDecoration(itemDecoration);

        trabajadoresAdapter.setOnItemClickListener((itemView, position) -> {
            seleccionarTrabajadors(position);
            /*Trabajadores itemPosition = trabajadoresAdapter.getItemPosition(position);

            HomeFragmentDirections.ActionNavHomeToCapturaFragment a = HomeFragmentDirections.actionNavHomeToCapturaFragment();
            a.setTrabajador(itemPosition);
            a.setAsistenacia(AsistenciaControl.getAsistenciaTrabajador(this.getContext(),itemPosition.getNumero()));
            Navigation.findNavController(getActivity(),R.id.fabManual).navigate(a);

            this.dismiss();*/
        });
    }

    private void seleccionarTrabajadors(int position){
        FileLog.i(Complementos.TAG_HOME,"captura manual");
        Trabajadores itemPosition = trabajadoresAdapter.getItemPosition(position);

        if(itemPosition == null)
            return;

        HomeFragmentDirections.ActionNavHomeToCapturaFragment action = HomeFragmentDirections.actionNavHomeToCapturaFragment(itemPosition.getNumero());
        Asistencia asistencia = AsistenciaControl.getAsistenciaTrabajadorDia(this.getContext(),itemPosition.getNumero());
        if(asistencia!=null)
            action.setIdAsistencia(asistencia.getId());
        //a.setTrabajador(itemPosition);
        //a.setAsistenacia(AsistenciaControl.getAsistenciaTrabajadorDia(this.getContext(),itemPosition.getNumero()));
        Navigation.findNavController(getActivity(),R.id.fabManual).navigate(action);

        this.dismiss();
    }
}

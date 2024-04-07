package com.example.capturahoras.ui.detalle;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.capturahoras.R;
import com.example.capturahoras.controlador.AsistenciaControl;
import com.example.capturahoras.controlador.SesionControl;
import com.example.capturahoras.modelo.Asistencia;
import com.example.capturahoras.modelo.Campos;
import com.example.capturahoras.modelo.CamposTrabajados;
import com.example.capturahoras.ui.Captura.CapturaFragment;
import com.example.capturahoras.ui.Captura.CapturaFragmentArgs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class DetalleFragment extends Fragment {
    private Asistencia asistencia;
    private boolean edicion;
    private HashMap<Integer,Campos> camposSelccionados;

    private ListView listaCamposSeleccionados;
    private Button btn_guardar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalle, container, false);

        DetalleFragmentArgs capturaArgs = DetalleFragmentArgs.fromBundle(getArguments());
        edicion = capturaArgs.getEdicion();
        asistencia = capturaArgs.getAsistenciaArg();

        this.camposSelccionados = new HashMap<>();
        getListaCampos();

        listaCamposSeleccionados= view.findViewById(R.id.lv_campos_seleccionados);
        btn_guardar = view.findViewById(R.id.btn_guardar);

        DetalleAdapter detalleAdapter = new DetalleAdapter(getContext(), new ArrayList<>(camposSelccionados.values()));
        detalleAdapter.setSupportFragmentManager(getActivity().getSupportFragmentManager());
        listaCamposSeleccionados.setAdapter(detalleAdapter);

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogoGuardarCambio();
            }
        });
        return view;
    }

    private void guardar(){
        if(asistencia == null)
            return;

        if(!validarCampos()){
            DialogoInf("campos no validos\n favor de revisar los campos");
            return;
        }

        Collection<CamposTrabajados> values = asistencia.getCamposTrabajados().values();
        for (CamposTrabajados ct :values) {
            if(ct.getCampo() == null)
                continue;
            if(ct.getCampo().size() == 0)
                continue;

            for (Campos c : ct.getCampo()) {
                if(camposSelccionados.containsKey(c.getClave())){
                    Campos campos = camposSelccionados.get(c.getClave());
                    campos.setProductoSeleccionado(camposSelccionados.get(c.getClave()).getProductoSeleccionado());
                    campos.setCceSeleccionada(camposSelccionados.get(c.getClave()).getCceSeleccionada());
                    campos.setEtapaSeleccionada(camposSelccionados.get(c.getClave()).getEtapaSeleccionada());
                }
            }
        }

        if(edicion)
            AsistenciaControl.actualizar(this.getContext(),asistencia);
        else
            AsistenciaControl.guardar(this.getContext(),asistencia);

        NavHostFragment.findNavController(DetalleFragment.this).popBackStack(R.id.nav_home, false);
        return;
    }

    private boolean validarCampos(){
        Collection<Campos> values = camposSelccionados.values();
        for (Campos v :values) {
            if(v.getProductoSeleccionado() == null)
                return  false;
            if(v.getEtapaSeleccionada() == null)
                return  false;
            if(v.getDescripcion() == null)
                return  false;
        }

        return true;
    }

    private void getListaCampos(){
        if(asistencia == null)
            return;

        Collection<CamposTrabajados> values = asistencia.getCamposTrabajados().values();
        for (CamposTrabajados ct :values) {
            if(ct.getCampo() == null)
                continue;
            if(ct.getCampo().size() == 0)
                continue;

            for (Campos c : ct.getCampo()) {
                if(!camposSelccionados.containsKey(c.getClave()))
                    camposSelccionados.put(c.getClave(),c);
            }
        }

        return;
    }

    private void DialogoGuardarCambio(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Esta seguro de guardar el registro")
                .setTitle("Captura de asistencia")
                .setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(!SesionControl.validarSesion()){
                            DialogoInf("Session finalizada");
                            return;
                        }
                        guardar();
                    }
                })
                .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void DialogoInf(String mensaje){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
// 2. Chain together various setter methods to set the dialog characteristics.
        builder.setMessage(mensaje)
                .setTitle("Captura de asistencia")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
// 3. Get the AlertDialog.
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
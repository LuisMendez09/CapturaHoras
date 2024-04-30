package com.example.capturahoras.ui.home;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.capturahoras.R;
import com.example.capturahoras.complemento.Complementos;
import com.example.capturahoras.complemento.FileLog;
import com.example.capturahoras.controlador.AsistenciaControl;
import com.example.capturahoras.controlador.SesionControl;
import com.example.capturahoras.controlador.TrabajadoresControl;
import com.example.capturahoras.modelo.Asistencia;
import com.example.capturahoras.modelo.Trabajadores;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.List;

public class HomeFragment extends Fragment {

    private AsistenciaAdaptar asistenciaAdaptar;

    private ListView lv_asistenacia;
    private FloatingActionButton fabBarcode;
    private FloatingActionButton fabTrabajador;

    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() == null) {
            Toast.makeText(getContext(), "CANCELADO", Toast.LENGTH_SHORT).show();
        } else {
            try{
                int i = Integer.parseInt(result.getContents());
                Trabajadores t = TrabajadoresControl.getTrabajadoresPorId(this.getContext(), i);

                HomeFragmentDirections.ActionNavHomeToCapturaFragment action = HomeFragmentDirections.actionNavHomeToCapturaFragment(t.getNumero());
                Asistencia at = AsistenciaControl.getAsistenciaTrabajadorDia(this.getContext(), t.getNumero());
                if(at!=null)
                    action.setIdAsistencia(at.getId());
                //action.setTrabajador(t);
                //action.setAsistenacia(AsistenciaControl.getAsistenciaTrabajador(this.getContext(),t.getNumero()));
                Navigation.findNavController(getActivity(),fabBarcode.getId()).navigate(action);
            }catch (Exception e){
                DialogoInf("Error al obtener el numero de trabajador");
            }
        }
    });


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);


        lv_asistenacia = root.findViewById(R.id.lt_asistencia);
        fabBarcode = root.findViewById(R.id.fab);
        fabTrabajador = root.findViewById(R.id.fabManual);

        fabBarcode.setOnClickListener(view -> {
            FileLog.i(Complementos.TAG_HOME,"Captura por codigo");
            if(SesionControl.validarSesion())
                escanear();
            else
                DialogoInf("SESION FINALIZADA");
        });

        fabTrabajador.setOnClickListener(view -> {
            FileLog.i(Complementos.TAG_HOME,"Captura por codigo");
            if(SesionControl.validarSesion())
                new DialogSeleccionTrabajadores().show(getActivity().getSupportFragmentManager(), "trabajdores");
            else
                DialogoInf("SESION FINALIZADA");

        });



        inicializarRecyclerView();

        FileLog.i(Complementos.TAG_HOME,"Inicia Home");
        return root;
    }

    public void escanear() {
        ScanOptions options = new ScanOptions();
        options.setDesiredBarcodeFormats(ScanOptions.ALL_CODE_TYPES);
        options.setPrompt("ESCANEAR CODIGO");
        options.setCameraId(0);
        options.setOrientationLocked(false);
        options.setBeepEnabled(false);
        options.setCaptureActivity(CaptureActivityPortrait.class);
        options.setBarcodeImageEnabled(false);

        barcodeLauncher.launch(options);
    }

    //@RequiresApi(api = Build.VERSION_CODES.N)
    private void inicializarRecyclerView(){

        List<Asistencia> asistencia = AsistenciaControl.getAsistenciaDia(getContext());
        asistenciaAdaptar = new AsistenciaAdaptar(getContext(),asistencia);
        lv_asistenacia.setAdapter(asistenciaAdaptar);
        lv_asistenacia.setOnItemLongClickListener((adapterView, view, i, l) -> {

            if(SesionControl.validarSesion()){
                Asistencia item = asistenciaAdaptar.getItem(i);
                DialogoEliminar(item);
            }else{
                DialogoInf("SESION FINALIZADA");
            }
            return false;
        });

        lv_asistenacia.setOnItemClickListener((adapterView, view, i, l) -> {
            if(SesionControl.validarSesion()){
                Asistencia item = asistenciaAdaptar.getItem(i);
                HomeFragmentDirections.ActionNavHomeToCapturaFragment action
                        = HomeFragmentDirections.actionNavHomeToCapturaFragment(item.getTrabajador().getNumero());
                action.setIdAsistencia(item.getId());
                //action.setTrabajador(item.getTrabajador());
                //action.setAsistenacia(item);
                Navigation.findNavController(getActivity(),fabBarcode.getId()).navigate(action);
            }else{
                DialogoInf("SESION FINALIZADA");
            }
        });
    }

    private void DialogoEliminar(Asistencia asistencia){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Esta seguro de elimniar este registro")
                .setTitle("Captura de asistencia")
                .setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       AsistenciaControl.eliminar(getContext(),asistencia.getId());
                       asistenciaAdaptar.remove(asistencia);
                       asistenciaAdaptar.notifyDataSetChanged();
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

}//43628
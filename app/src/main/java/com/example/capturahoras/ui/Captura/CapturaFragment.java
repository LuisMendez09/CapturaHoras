package com.example.capturahoras.ui.Captura;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.capturahoras.R;
import com.example.capturahoras.complemento.Complementos;
import com.example.capturahoras.complemento.FileLog;
import com.example.capturahoras.controlador.AsistenciaControl;
import com.example.capturahoras.controlador.CamposControl;
import com.example.capturahoras.controlador.SesionControl;
import com.example.capturahoras.controlador.TablasControl;
import com.example.capturahoras.modelo.Actividades;
import com.example.capturahoras.modelo.Asistencia;
import com.example.capturahoras.modelo.Campos;
import com.example.capturahoras.modelo.CamposTrabajados;
import com.example.capturahoras.modelo.Trabajadores;
import com.example.capturahoras.modelo.TablasProrrateo;
import com.example.capturahoras.ui.home.HomeFragmentDirections;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class CapturaFragment extends Fragment implements OnItemSelectListener{
    private Button cancelar;
    private Button guardar;
    private TextView tv_numero;
    private TextView tv_nombre;
    private TextView tv_horaInicio;
    private TextView tv_horaFinal;
    private Button btn_actividad;
    private EditText et_horas;
    private ListView lv_camposTrabajados;

    private Asistencia asistencias;
    private Trabajadores trabajador;
    private List<Actividades> actividadesS = new ArrayList<>();
    private HashMap<String,List<Campos>>  campos = new HashMap<>();
    private HashMap<String,TablasProrrateo> tabla= new HashMap<>();

    private HashMap<String,CamposTrabajados> hct = new HashMap<>();

    CharSequence[] arrayCampos;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_captura, container, false);

        CapturaFragmentArgs capturaArgs = CapturaFragmentArgs.fromBundle(getArguments());
        trabajador = capturaArgs.getTrabajador();
        asistencias = capturaArgs.getAsistenacia();

        tv_nombre = view.findViewById(R.id.tv_nombre);
        tv_numero = view.findViewById(R.id.tv_numero);
        tv_horaInicio = view.findViewById(R.id.et_inicio);
        tv_horaFinal = view.findViewById(R.id.et_final);
        cancelar = view.findViewById(R.id.bt_cancelar);
        guardar = view.findViewById(R.id.bt_guardar);
        btn_actividad = view.findViewById(R.id.bt_actividad);
        et_horas = view.findViewById(R.id.tv_horas);
        lv_camposTrabajados = view.findViewById(R.id.lv_campos_trabajados);

        tv_horaInicio.setOnClickListener(v1 -> getTime(tv_horaInicio));
        tv_horaFinal.setOnClickListener(v1 -> getTime(tv_horaFinal));

        guardar.setOnClickListener(view12 -> {

            DialogoGuardarCambio();
        });
        cancelar.setOnClickListener(view1 -> NavHostFragment.findNavController(CapturaFragment.this).popBackStack(R.id.nav_home, false));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        tv_numero.setText(String.valueOf(trabajador.getNumero()));
        tv_nombre.setText(trabajador.getNombre());

        arrayCampos = CamposControl.getCamposArray(this.getContext());

        iniciarEdicion();

        btn_actividad.setOnClickListener(v1-> {
            DialogSeleccionActividad dialogSeleccionActividad = new DialogSeleccionActividad();
            dialogSeleccionActividad.setActividadesSeleccionadas(actividadesS);
            dialogSeleccionActividad.setOnItemSelectListener(this);
            dialogSeleccionActividad.show(getActivity().getSupportFragmentManager(),"actividad");
        });


    }

    public void iniciarEdicion(){
        if(asistencias == null)
            return;

        et_horas.setText(String.valueOf(asistencias.getTotalHoras()));
        tv_horaInicio.setText(Complementos.obtenerHoraString(new Date(asistencias.getHoraInicial())));
        tv_horaFinal.setText(Complementos.obtenerHoraString(new Date(asistencias.getHoraFinal())));

        actividadesS.clear();

        Set<String> act = asistencias.getCamposTrabajados().keySet();
        hct = asistencias.getCamposTrabajados();

        Set<String> strings = hct.keySet();
        for (String key :strings) {
            actividadesS.add(hct.get(key).getActividades());
            if(!campos.containsKey(key))
                campos.put(key,hct.get(key).getCampo());
            if(!tabla.containsKey(key))
                tabla.put(key,hct.get(key).getTablaProrrateo());
        }

        List<CamposTrabajados> values = new ArrayList<>();

        for (String a : act) {
            CamposTrabajados ct = hct.get(a);
            values.add(ct);
        }

        CamposTrabajadosAdapter cta = new CamposTrabajadosAdapter(getContext(),values);
        cta.setHatMap(hct);
        lv_camposTrabajados.setAdapter(cta);
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

                        if(asistencias==null)
                            guardar();
                        else
                            update();
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

    private void guardar(){
        if(!validar())
            return;

        Asistencia asistencia = new Asistencia();
        //asistencia.setActividad(actividad);
        asistencia.setFecha(Complementos.getDateActual().getTime());
        asistencia.setTotalHoras(Float.parseFloat(et_horas.getText().toString()));
        asistencia.setTrabajador(trabajador);
        try {
            asistencia.setHoraInicial(Complementos.convertirStringAlong(asistencia.getFechaString(),tv_horaInicio.getText().toString()));

            asistencia.setHoraFinal(0);
            if(!tv_horaFinal.getText().toString().equals(""))
                asistencia.setHoraFinal(Complementos.convertirStringAlong(asistencia.getFechaString(),tv_horaFinal.getText().toString()));
        } catch (ParseException e) {
            DialogoInf("ERROR!!, favor de revisar la hora de inicio");
            FileLog.e("CapturaFragmen",e.getMessage());
            return;
        }

        asistencia.setCamposTrabajados(hct);

        boolean mallas = false;
        Collection<CamposTrabajados> values = hct.values();
        for (CamposTrabajados ct :hct.values()) {
            if(ct.getCampo().size() >0){
                mallas = true;
                break;
            }
        }

        if(mallas){
            CapturaFragmentDirections.ActionCapturaFragmentToDetalleFragment2 action = CapturaFragmentDirections.actionCapturaFragmentToDetalleFragment2(asistencia);
            action.setEdicion(false);
            Navigation.findNavController(getActivity(),getId()).navigate(action);
            return;
        }

        AsistenciaControl.guardar(this.getContext(),asistencia);
        NavHostFragment.findNavController(CapturaFragment.this).popBackStack(R.id.nav_home, false);
    }

    private void update(){
        if(!validar())
            return;

        //asistencias.setActividad(actividadesS);
        asistencias.setTotalHoras(Float.parseFloat(et_horas.getText().toString()));
        try {
            asistencias.setHoraInicial(Complementos.convertirStringAlong(asistencias.getFechaString(),tv_horaInicio.getText().toString()));

            asistencias.setHoraFinal(0);
            if(!tv_horaFinal.getText().toString().equals(""))
                asistencias.setHoraFinal(Complementos.convertirStringAlong(asistencias.getFechaString(),tv_horaFinal.getText().toString()));
        } catch (ParseException e) {
            DialogoInf("ERROR!!, favor de revisar la hora de inicio");
            FileLog.e("CapturaFragmen",e.getMessage());
            return;
        }

        asistencias.setCamposTrabajados(hct);
        if(campos.size()==0){
            AsistenciaControl.actualizar(this.getContext(),asistencias);
            NavHostFragment.findNavController(CapturaFragment.this).popBackStack(R.id.nav_home, false);
        }

        CapturaFragmentDirections.ActionCapturaFragmentToDetalleFragment2 action = CapturaFragmentDirections.actionCapturaFragmentToDetalleFragment2(asistencias);
        action.setEdicion(true);
        Navigation.findNavController(getActivity(),getId()).navigate(action);
    }

    public boolean validar(){
        if(actividadesS.size() == 0){
            DialogoInf("ERROR!!, Favor de seleccionar una actividad");
            return false;
        }
        if(et_horas.getText().toString().equals("")){
            DialogoInf("ERROR!!, Favor de ingresar las horas trabajadas");
            return false;
        }
        if(tv_horaInicio.getText().toString().equals("")){
            DialogoInf("ERROR!!, Favor de ingresar la hora inicial");
            return false;
        }
        if(campos.size()==0 && tabla == null){
            DialogoInf("ERROR!!, Favor de seleccionar campos o una tabla de prorrateo");
            return false;
        }

        return true;
    }

    private void getTime(final TextView textView) {
        // Get Current Date
        int mHour, mMinute;
        final Calendar c = Calendar.getInstance();

        // Get Current Time
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this.getContext(),
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        textView.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

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

    @Override
    public void onItemsSelectActividad(List<Actividades> actividades) {
        actividadesS = actividades;
        String act="";

        for (Actividades a : actividadesS) {
            if(!hct.containsKey(a.getDescripcion())){
                CamposTrabajados ct = new CamposTrabajados();
                ct.setActividades(a);
                hct.put(a.getDescripcion(),ct);
            }
            act = act+"," + a.getDescripcion();
        }
        //tv_actividad.setText(act);


        Object[] objects = hct.keySet().toArray();
        for (int i=0;i<objects.length;i++) {
            String s = objects[i].toString();
            if(!act.contains(s))
                hct.remove(s);
        }

        Set<String> key = hct.keySet();
        List<CamposTrabajados> values = new ArrayList<>();

        for (String a : key) {
            CamposTrabajados ct = hct.get(a);
            values.add(ct);
        }

        CamposTrabajadosAdapter cta = new CamposTrabajadosAdapter(getContext(),values);
        cta.setHatMap(hct);
        lv_camposTrabajados.setAdapter(cta);

        DialogCamposTablas dialogSeleccionCampos = new DialogCamposTablas();
        dialogSeleccionCampos.setOnItemSelectListener(this);
        dialogSeleccionCampos.setCamposSeleccionados(hct);
        dialogSeleccionCampos.show(getActivity().getSupportFragmentManager(),"campos");
    }

    @Override
    public void onItemsSelectCampo(List<Campos> actividades) {

    }

    @Override
    public void onItemsSelectTabla(TablasProrrateo tablasProrrateo) {

    }

    @Override
    public void onItemsSelectCamposTrabajados(HashMap<String, CamposTrabajados> hct) {
        Set<String> key = hct.keySet();
        List<CamposTrabajados> values = new ArrayList<>();

        for (String a : key) {
            CamposTrabajados ct = hct.get(a);
            values.add(ct);
        }

        CamposTrabajadosAdapter cta = new CamposTrabajadosAdapter(getContext(),values);
        cta.setHatMap(hct);
        lv_camposTrabajados.setAdapter(cta);

    }
}
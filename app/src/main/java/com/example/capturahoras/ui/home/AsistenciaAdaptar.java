package com.example.capturahoras.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.capturahoras.R;
import com.example.capturahoras.modelo.Asistencia;
import com.example.capturahoras.modelo.Campos;
import com.example.capturahoras.modelo.CamposTrabajados;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class AsistenciaAdaptar extends ArrayAdapter<Asistencia> {
    private List<Asistencia> asistencias;

    public AsistenciaAdaptar(@NonNull Context context, @NonNull List<Asistencia> asistencias) {
        super(context, R.layout.item_asistencia, asistencias);
        this.asistencias = asistencias;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_asistencia,parent,false);

        TextView numeroT = convertView.findViewById(R.id.tv_numeroL);
        TextView nombreTrabajdor = convertView.findViewById(R.id.tv_trabajador);
        TextView horas = convertView.findViewById(R.id.tv_horas);
        TextView campos = convertView.findViewById(R.id.tv_campos);
        TextView tablas = convertView.findViewById(R.id.tv_tablas);
        TextView actividad = convertView.findViewById(R.id.tv_actividad);


        Asistencia a = asistencias.get(position);
        HashMap<String,CamposTrabajados> hct=a.getCamposTrabajados();
        Set<String> keys =hct.keySet();
        nombreTrabajdor.setText(a.getTrabajador().getNombre());
        numeroT.setText(a.getTrabajador().getNumero()+"");
        horas.setText("Horas: " + String.valueOf( a.getTotalHoras()));

        String act = "";
        String c = "";
        String t = "";

        act = keys.toString();
        actividad.setText(act);

        for(String key : keys){
            CamposTrabajados ct = hct.get(key);
            if(ct.getCampo() != null){
                for (Campos cam : ct.getCampo()) {
                    c = c +"," + cam.getDescripcion();
                }
            }

            if(ct.getTablaProrrateo() != null)
                t = t +"," + ct.getTablaProrrateo().getDescripcion();
        }

        campos.setText(c);
        tablas.setText(t);

        return convertView;
    }

    @Nullable
    @Override
    public Asistencia getItem(int position) {
        return asistencias.get(position);
    }


}

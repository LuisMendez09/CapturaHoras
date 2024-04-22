package com.example.capturahoras.ui.Captura;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.capturahoras.R;
import com.example.capturahoras.modelo.Campos;
import com.example.capturahoras.modelo.CamposTrabajados;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class CamposTrabajadosAdapter extends ArrayAdapter<CamposTrabajados> {
    HashMap<String,CamposTrabajados> htc;
    List<CamposTrabajados> camposTrabajados;

    public CamposTrabajadosAdapter(@NonNull Context context, @NonNull List<CamposTrabajados> camposTrabajados) {
        super(context, R.layout.item_campos_trabajados, camposTrabajados);
        this.camposTrabajados = camposTrabajados;
    }

    public void setHatMap(HashMap<String,CamposTrabajados> htc){
        this.htc = htc;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_campos_trabajados,parent,false);

        TextView actividad = convertView.findViewById(R.id.tv_lvactividad);
        TextView campos = convertView.findViewById(R.id.tv_lvcampos);
        TextView tabla = convertView.findViewById(R.id.tv_lvtabla);
        TextView horas = convertView.findViewById(R.id.tv_horasActividad);

        CamposTrabajados ct = this.camposTrabajados.get(position);
        actividad.setText(ct.getActividades().getDescripcion());
        horas.setText(ct.getHoras()+"");

        if(ct.getTablaProrrateo() != null)
            tabla.setText(ct.getTablaProrrateo().getDescripcion());

        if( ct.getCampo()!= null){
            String c = "";
            for (Campos cam : ct.getCampo()) {
                c=c+","+cam.getDescripcion();
            }
            campos.setText(c);
        }

        return convertView;
    }

    @Nullable
    @Override
    public CamposTrabajados getItem(int position) {
        return camposTrabajados.get(position);
    }
}

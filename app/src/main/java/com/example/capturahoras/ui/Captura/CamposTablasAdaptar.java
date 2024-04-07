package com.example.capturahoras.ui.Captura;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.example.capturahoras.R;
import com.example.capturahoras.modelo.Actividades;
import com.example.capturahoras.modelo.Campos;
import com.example.capturahoras.modelo.CamposTrabajados;
import com.example.capturahoras.modelo.TablasProrrateo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CamposTablasAdaptar extends ArrayAdapter<CamposTrabajados> implements OnItemSelectListener {
    private TextView camposS;
    private TextView tablasS;

    List<CamposTrabajados> camposTrabajados;
    FragmentManager fragmentManager;
    int index;

    public CamposTablasAdaptar(@NonNull Context context, @NonNull List<CamposTrabajados> camposTrabajados) {
        super(context, R.layout.item_select_campos, camposTrabajados);
        this.camposTrabajados = camposTrabajados;
    }

    public void setSupportFragmentManager(FragmentManager fragmentManager){
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_select_campos,parent,false);

        TextView actividad = convertView.findViewById(R.id.tv_actividad);
        TextView campos = convertView.findViewById(R.id.tv_camposTabla);
        Button btn_campos =convertView.findViewById(R.id.btn_campos);
        TextView tablas = convertView.findViewById(R.id.tv_tablas);
        Button btn_tablas =convertView.findViewById(R.id.btn_tablas);
        camposS = campos;
        tablasS = tablas;
        String act = "";
        String t = "";

        CamposTrabajados ct = this.camposTrabajados.get(position);
        actividad.setText(ct.getActividades().getDescripcion());

        iniciarCampos(ct.getCampo(),ct.getTablaProrrateo());

        btn_campos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index = position;

                DialogSeleccionCampos dialogSeleccionCampos = new DialogSeleccionCampos();
                dialogSeleccionCampos.setOnItemSelectListener(CamposTablasAdaptar.this);
                dialogSeleccionCampos.setCamposSeleccionadas(ct.getCampo());

                dialogSeleccionCampos.show(CamposTablasAdaptar.this.fragmentManager,"campo");
            }
        });

        btn_tablas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index = position;
                dialogSeleccionTablas dialogSeleccionTablas = new dialogSeleccionTablas();
                dialogSeleccionTablas.setOnItemSelectListener(CamposTablasAdaptar.this);
                dialogSeleccionTablas.setTablaSeleccionadas(ct.getTablaProrrateo());

                dialogSeleccionTablas.show(CamposTablasAdaptar.this.fragmentManager,"Tablas Prorrateo");
            }
        });

        return convertView;
    }

    private void iniciarCampos(List<Campos>cp,TablasProrrateo tp){
        if(cp == null && tp == null)
            return;

        String c = "";
        String t = "";
        if(cp!= null && cp.size()>0){

            for (Campos cam : cp) {
                c = c +"," + cam.getDescripcion();
            }
        }

        if(tp!= null)
            t = tp.getDescripcion();

        camposS.setText(c);
        tablasS.setText(t);

        notifyDataSetChanged();
    }


    @Nullable
    @Override
    public CamposTrabajados getItem(int position) {
        return camposTrabajados.get(position);
    }

    @Override
    public void onItemsSelectActividad(List<Actividades> actividades) {

    }

    @Override
    public void onItemsSelectCampo(List<Campos> cp) {
        camposTrabajados.get(index).setCampos(cp);
        camposTrabajados.get(index).setTablaProrrateo(null);
        iniciarCampos(cp,null);
        //notifyAll();


    }

    @Override
    public void onItemsSelectTabla(TablasProrrateo tablasProrrateo) {
        camposTrabajados.get(index).setCampos(new ArrayList<>());
        camposTrabajados.get(index).setTablaProrrateo(tablasProrrateo);
        iniciarCampos(null,tablasProrrateo);
    }

    @Override
    public void onItemsSelectCamposTrabajados(HashMap<String, CamposTrabajados> hct) {

    }
}

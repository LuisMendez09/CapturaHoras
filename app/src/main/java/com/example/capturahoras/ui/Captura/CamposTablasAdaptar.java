package com.example.capturahoras.ui.Captura;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
        EditText horas = convertView.findViewById(R.id.tv_horasActividad);
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
        if(ct.getHoras() != 0)
            horas.setText(ct.getHoras()+"");

        iniciarCampos(ct.getCampo(),ct.getTablaProrrateo());

        horas.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().equals(""))
                    return;

                camposTrabajados.get(position).setHoras(Float.parseFloat(charSequence.toString()));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btn_campos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index = position;

                //DialogSeleccionMultiplesCampos dialogSeleccionMultiplesCampos = new DialogSeleccionMultiplesCampos();
                //dialogSeleccionMultiplesCampos.setOnItemSelectListener(CamposTablasAdaptar.this);
                //dialogSeleccionMultiplesCampos.setCamposSeleccionadas(ct.getCampo());
                //dialogSeleccionMultiplesCampos.show(CamposTablasAdaptar.this.fragmentManager,"campo");

                DialogSeleccionCampo dsc = new DialogSeleccionCampo();
                dsc.setOnItemSelectListener(CamposTablasAdaptar.this);
                if(ct.getCampo() == null)
                    dsc.setTablaSeleccionadas(null);
                else if(ct.getCampo().size() == 0)
                    dsc.setTablaSeleccionadas(null);
                else
                    dsc.setTablaSeleccionadas(ct.getCampo().get(0));

                dsc.show(CamposTablasAdaptar.this.fragmentManager,"Campos seleccion");
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
    public void onItemsSelectCampo(Campos campo) {
        List<Campos> cam = new ArrayList<>();
        cam.add(campo);
        camposTrabajados.get(index).setCampos(cam);
        camposTrabajados.get(index).setTablaProrrateo(null);
        iniciarCampos(cam,null);
    }

    @Override
    public void onItemsSelectCamposTrabajados(HashMap<String, CamposTrabajados> hct) {

    }
}

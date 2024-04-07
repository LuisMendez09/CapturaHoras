package com.example.capturahoras.ui.detalle;

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
import com.example.capturahoras.modelo.CCE;
import com.example.capturahoras.modelo.Campos;
import com.example.capturahoras.modelo.Etapas;
import com.example.capturahoras.modelo.Productos;

import java.util.List;


public class DetalleAdapter extends ArrayAdapter<Campos> implements OnItemSelecctionListener{
    private FragmentManager fragmentManager;

    List<Campos> camposSeleccionados;
    int index;

    public DetalleAdapter(@NonNull Context context, @NonNull List<Campos> campos) {
        super(context, R.layout.item_detalle_malla, campos);
        this.camposSeleccionados = campos;
    }

    public void setSupportFragmentManager(FragmentManager fragmentManager){
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_detalle_malla,parent,false);

        TextView campos = convertView.findViewById(R.id.tv_campo);
        TextView tv_producto = convertView.findViewById(R.id.tv_producto);
        TextView tv_etapa = convertView.findViewById(R.id.tv_etapa);
        TextView tv_cce = convertView.findViewById(R.id.tv_cce);

        Button btn_producto = convertView.findViewById(R.id.btn_producto);
        Button btn_etapa = convertView.findViewById(R.id.btn_etapa);
        Button btn_cce = convertView.findViewById(R.id.btn_cce);

        btn_etapa.setOnClickListener(view -> {
            index = position;
            DialogSeleccionEtapa dse = new DialogSeleccionEtapa();
            dse.setOnItemSelectListener(DetalleAdapter.this);
            dse.setEtapaSeleccionada(camposSeleccionados.get(position).getEtapaSeleccionada(),tv_etapa);
            dse.show(DetalleAdapter.this.fragmentManager,"etapa");
        });

        btn_cce.setOnClickListener(view -> {
            index = position;
            DialogSeleccionCce dse = new DialogSeleccionCce();
            dse.setOnItemSelectListener(DetalleAdapter.this);
            dse.setCceSeleccionada(camposSeleccionados.get(position).getCceSeleccionada(),tv_cce);
            dse.show(DetalleAdapter.this.fragmentManager,"etapa");
        });

        btn_producto.setOnClickListener(view -> {
            index = position;
            DialogSeleccionProdiucto dse = new DialogSeleccionProdiucto();
            dse.setOnItemSelectListener(DetalleAdapter.this);
            dse.setProductosSeleccionada(camposSeleccionados.get(position).getProductoSeleccionado(),tv_producto);
            dse.show(DetalleAdapter.this.fragmentManager,"etapa");
        });

        Campos c = this.camposSeleccionados.get(position);
        campos.setText(c.getDescripcion());

        if(c.getProductoSeleccionado() != null)
            tv_producto.setText(c.getProductoSeleccionado().getDescripcion());
        if(c.getEtapaSeleccionada() != null)
            tv_etapa.setText(c.getEtapaSeleccionada().getDescripcion());
        if(c.getCceSeleccionada() != null)
            tv_cce.setText(c.getCceSeleccionada().getDescripcion());

        return convertView;
    }

    @Nullable
    @Override
    public Campos getItem(int position) {
        return camposSeleccionados.get(position);
    }

    @Override
    public void onItemsSelectProducto(Productos productos) {
        camposSeleccionados.get(index).setProductoSeleccionado(productos);
    }

    @Override
    public void onItemsSelectEtapa(Etapas etapas) {
        camposSeleccionados.get(index).setEtapaSeleccionada(etapas);

    }

    @Override
    public void onItemsSelectCce(CCE cce) {
        camposSeleccionados.get(index).setCceSeleccionada(cce);
    }
}

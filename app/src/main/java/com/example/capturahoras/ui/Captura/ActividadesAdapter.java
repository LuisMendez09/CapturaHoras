package com.example.capturahoras.ui.Captura;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capturahoras.R;
import com.example.capturahoras.modelo.Actividades;
import com.example.capturahoras.modelo.Campos;

import java.util.ArrayList;
import java.util.List;

public class ActividadesAdapter extends RecyclerView.Adapter<ActividadesAdapter.ViewHolder> implements Filterable {
    public List<Actividades> actividades;
    public List<Actividades> listaFiltrada;
    //public List<Actividades> actividadesSelecc;

    private ActividadesAdapter.CustomFilter mFilter;
    private ActividadesAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }


    public void setOnItemClickListener(ActividadesAdapter.OnItemClickListener listener){
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CheckBox actividad;

        public ViewHolder(@NonNull View viewItem) {
            super(viewItem);

            actividad = viewItem.findViewById(R.id.cb_checkbox);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if(position!= RecyclerView.NO_POSITION){
                            listener.onItemClick(view,position);
                        }
                    }
                }
            });

            actividad.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    Actividades itemPosition = getItemPosition(getAdapterPosition());
                    itemPosition.setSelect(b);
                }
            });

        }

        public CheckBox getActividadView(){return actividad;}
    }

    public List<Actividades> getActividadesSelecc(){
        List<Actividades> cs = new ArrayList<>();

        for (Actividades c:actividades) {
            if(c.isSelect())
                cs.add(c);
        }

        return cs;
    }

    public ActividadesAdapter(List<Actividades> dataSet) {
        this.actividades = dataSet;
        this.listaFiltrada = new ArrayList<>();
        this.listaFiltrada.addAll(dataSet);
        this.mFilter = new ActividadesAdapter.CustomFilter(ActividadesAdapter.this);
    }

    public void update(List<Actividades> dataSet){
        this.actividades = dataSet;
        this.listaFiltrada = new ArrayList<>();
        this.listaFiltrada.addAll(dataSet);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //crea una nueva vista por cada uno de los elementros del dataset
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_seleccion_checkbox,parent,false);

        return new ActividadesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //obtine el elemento del dataset en esta posicion y remplaza el contenido de la vista por cada elemento
        CheckBox actividadView = holder.getActividadView();
        Actividades actividades = listaFiltrada.get(position);
        actividadView.setText(actividades.getDescripcion());
        actividadView.setChecked(actividades.isSelect());

    }

    public Actividades getItemPosition(int position){
        return listaFiltrada.get(position);
    }

    @Override
    public int getItemCount() {
        return this.listaFiltrada.size();
    }

    /**filtro***************************************************************************/
    public class CustomFilter extends Filter{
        private ActividadesAdapter adapter;

        public CustomFilter(ActividadesAdapter adapter) {
            super();
            this.adapter = adapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            listaFiltrada.clear();
            final FilterResults results = new FilterResults();
            if(charSequence.length()==0){
                listaFiltrada.addAll(actividades);
            }
            else{
                final String filtrar = charSequence.toString().toLowerCase().trim();
                for(final Actividades v : actividades){
                    if(v.getDescripcion().toLowerCase().contains(filtrar)){
                        listaFiltrada.add(v);

                    }
                }
            }
            results.values = listaFiltrada;
            results.count = listaFiltrada.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            this.adapter.notifyDataSetChanged();
        }
    }
}

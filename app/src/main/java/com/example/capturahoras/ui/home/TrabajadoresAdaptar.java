package com.example.capturahoras.ui.home;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capturahoras.R;
import com.example.capturahoras.modelo.Trabajadores;

import java.util.ArrayList;
import java.util.List;

public class TrabajadoresAdaptar extends RecyclerView.Adapter<TrabajadoresAdaptar.ViewHolder> implements Filterable {
    private List<Trabajadores> localDataSet;
    private List<Trabajadores> listaFiltrada;

    private TrabajadoresAdaptar.CustomFilter mFilter;
    private TrabajadoresAdaptar.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }


    public void setOnItemClickListener(TrabajadoresAdaptar.OnItemClickListener listener){
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tb_tranajadores;
        private TextView tb_tranajadornumero;

        public ViewHolder(@NonNull View viewItem) {
            super(viewItem);

            tb_tranajadores = viewItem.findViewById(R.id.tv_trabajadorF);
            tb_tranajadornumero = viewItem.findViewById(R.id.tv_numeroF);

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
        }

        public TextView getTrabajadorView(){return tb_tranajadores;}
        public TextView getTrabajadorNumeroView(){return tb_tranajadornumero;}
    }



    public TrabajadoresAdaptar(List<Trabajadores> dataSet) {
        this.localDataSet = dataSet;
        this.listaFiltrada = new ArrayList<>();
        this.listaFiltrada.addAll(dataSet);
        this.mFilter = new TrabajadoresAdaptar.CustomFilter(TrabajadoresAdaptar.this);
    }

    public void update(List<Trabajadores> dataSet){
        this.localDataSet = dataSet;
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
                .inflate(R.layout.item_trabajadores,parent,false);

        return new TrabajadoresAdaptar.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrabajadoresAdaptar.ViewHolder holder, int position) {
        //obtine el elemento del dataset en esta posicion y remplaza el contenido de la vista por cada elemento

        holder.getTrabajadorView().setText(listaFiltrada.get(position).getNombre());
        holder.getTrabajadorNumeroView().setText(listaFiltrada.get(position).getNumero()+"");
    }

    public Trabajadores getItemPosition(int position){
        if(listaFiltrada.size()>0)
            return listaFiltrada.get(position);
        return null;
    }

    @Override
    public int getItemCount() {
        return this.listaFiltrada.size();
    }

    /**filtro***************************************************************************/
    public class CustomFilter extends Filter{
        private TrabajadoresAdaptar adapter;

        public CustomFilter(TrabajadoresAdaptar adapter) {
            super();
            this.adapter = adapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            listaFiltrada.clear();
            final FilterResults results = new FilterResults();
            if(charSequence.length()==0){
                listaFiltrada.addAll(localDataSet);
            }
            else{
                final String filtrar = charSequence.toString().toLowerCase().trim();
                for(final Trabajadores v : localDataSet){
                    if(v.getNombre().toLowerCase().contains(filtrar) || String.valueOf(v.getNumero()).contains(filtrar)){
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

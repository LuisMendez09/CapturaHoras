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
import com.example.capturahoras.complemento.FileLog;
import com.example.capturahoras.modelo.Actividades;
import com.example.capturahoras.modelo.Campos;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class CamposAdapter extends RecyclerView.Adapter<CamposAdapter.ViewHolder> implements Filterable {
    private List<Campos> campos;
    private List<Campos> listaFiltrada;

    private CamposAdapter.CustomFilter mFilter;
    //private CamposAdapter.OnItemClickListener listener;

    //public interface OnItemClickListener {
    //    void onItemClick(View itemView, int position);
    //}

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CheckBox campo;

        public ViewHolder(@NonNull View viewItem) {
            super(viewItem);

            campo = viewItem.findViewById(R.id.cb_campos_checkbox);

            /*itemView.setOnClickListener(view -> {
                if(listener!=null){
                    int position = getAdapterPosition();
                    if(position!= RecyclerView.NO_POSITION){
                        listener.onItemClick(view,position);
                    }
                }
            });*/

            campo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    Campos itemPosition = getItemPosition(getAdapterPosition());
                    itemPosition.setSelect(b);
                }
            });

        }

        public CheckBox getCampoView(){return campo;}
    }

    public List<Campos> getCamposSelecc(){
        List<Campos> cs = new ArrayList<>();

        for (Campos c:campos) {
            if(c.isSelect())
                cs.add(c);
        }

        return cs;
    }

    public CamposAdapter(List<Campos> dataSet) {
        this.campos = dataSet;
        this.listaFiltrada = new ArrayList<>();
        this.listaFiltrada.addAll(dataSet);
        this.mFilter = new CamposAdapter.CustomFilter(CamposAdapter.this);
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    @NonNull
    @Override
    public CamposAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //crea una nueva vista por cada uno de los elementros del dataset
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_campos_checbox,parent,false);

        return new CamposAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CamposAdapter.ViewHolder holder, int position) {
        //obtine el elemento del dataset en esta posicion y remplaza el contenido de la vista por cada elemento
        CheckBox campoView = holder.getCampoView();

        Campos campos = listaFiltrada.get(position);
        campoView.setText(campos.getDescripcion());
        campoView.setChecked(campos.isSelect());

    }

    public Campos getItemPosition(int position){
        return listaFiltrada.get(position);
    }

    @Override
    public int getItemCount() {
        return this.listaFiltrada.size();
    }

    /**filtro***************************************************************************/
    public class CustomFilter extends Filter{
        private CamposAdapter adapter;

        public CustomFilter(CamposAdapter adapter) {
            super();
            this.adapter = adapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            listaFiltrada.clear();
            final FilterResults results = new FilterResults();
            if(charSequence.length()==0){
                listaFiltrada.addAll(campos);
            }
            else{
                final String filtrar = charSequence.toString().toLowerCase().trim();

                for(int i = 0; i < campos.size(); i++){
                    if(campos.get(i).getDescripcion().toLowerCase().contains(filtrar)){
                        listaFiltrada.add(campos.get(i));
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

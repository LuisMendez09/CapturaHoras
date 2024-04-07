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
import com.example.capturahoras.modelo.TablasProrrateo;

import java.util.ArrayList;
import java.util.List;

public class TablasAdapter extends RecyclerView.Adapter<TablasAdapter.ViewHolder> implements Filterable {
private List<TablasProrrateo> tablaProrrateo;
private List<TablasProrrateo> listaFiltrada;

private TablasAdapter.CustomFilter mFilter;
//private TablasAdapter.OnItemClickListener listener;

public interface OnItemClickListener {
    void onItemClick(View itemView, int position);
}

public class ViewHolder extends RecyclerView.ViewHolder{
    private CheckBox tabla;

    public ViewHolder(@NonNull View viewItem) {
        super(viewItem);

        tabla = viewItem.findViewById(R.id.cb_tablas);

        /*itemView.setOnClickListener(view -> {
            if(listener!=null){
                int position = getAdapterPosition();
                if(position!= RecyclerView.NO_POSITION){
                    listener.onItemClick(view,position);
                }
            }
        });*/

        tabla.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                TablasProrrateo itemPosition = getItemPosition(getAdapterPosition());
                for (int i = 0; i < listaFiltrada.size(); i++) {
                    //tablaProrrateo.get(i).setSelect(false);
                }

                //itemPosition.setSelect(b);

            }
        });

    }

    public CheckBox getCampoView(){return tabla;}
}

    public List<TablasProrrateo> getTablasSelecc(){
        List<TablasProrrateo> cs = new ArrayList<>();

        for (TablasProrrateo c: tablaProrrateo) {
            //if(c.isSelect())
                cs.add(c);
        }

        return cs;
    }

    public TablasAdapter(List<TablasProrrateo> dataSet) {
        this.tablaProrrateo = dataSet;
        this.listaFiltrada = new ArrayList<>();
        this.listaFiltrada.addAll(dataSet);
        this.mFilter = new TablasAdapter.CustomFilter(TablasAdapter.this);
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    @NonNull
    @Override
    public TablasAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //crea una nueva vista por cada uno de los elementros del dataset
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_seleccion_tablas,parent,false);

        return new TablasAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TablasAdapter.ViewHolder holder, int position) {
        //obtine el elemento del dataset en esta posicion y remplaza el contenido de la vista por cada elemento
        CheckBox campoView = holder.getCampoView();

        TablasProrrateo campos = listaFiltrada.get(position);
        campoView.setText(campos.getDescripcion());
        //campoView.setChecked(campos.isSelect());

    }

    public TablasProrrateo getItemPosition(int position){
        return listaFiltrada.get(position);
    }

    @Override
    public int getItemCount() {
        return this.listaFiltrada.size();
    }

/**filtro***************************************************************************/
public class CustomFilter extends Filter{
    private TablasAdapter adapter;

    public CustomFilter(TablasAdapter adapter) {
        super();
        this.adapter = adapter;
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        listaFiltrada.clear();
        final FilterResults results = new FilterResults();
        if(charSequence.length()==0){
            listaFiltrada.addAll(tablaProrrateo);
        }
        else{
            final String filtrar = charSequence.toString().toLowerCase().trim();

            for(int i = 0; i < tablaProrrateo.size(); i++){
                if(tablaProrrateo.get(i).getDescripcion().toLowerCase().contains(filtrar)){
                    listaFiltrada.add(tablaProrrateo.get(i));
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

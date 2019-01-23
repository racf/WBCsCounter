package mx.com.sousystems.hemacount.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import mx.com.sousystems.hemacount.R;
import mx.com.sousystems.hemacount.dto.MuestraDTO;

public class TablaAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<MuestraDTO> listaMuestraDTO;

    public TablaAdapter (Context context, int layout, List<MuestraDTO> listaMuestraDTO){
        this.context = context;
        this.layout = layout;
        this.listaMuestraDTO = listaMuestraDTO;
    }

    @Override
    public int getCount() {
        return this.listaMuestraDTO.size();
    }

    @Override
    public Object getItem(int position) {
        return this.listaMuestraDTO.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        //Inflamos la vista que nos ha llegado con nuestro layout personalizado
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        view = layoutInflater.inflate(R.layout.lista_tabla, null);
        String idioma = listaMuestraDTO.get(position).getIdioma();
        Integer cantidad = listaMuestraDTO.get(position).getCantidad();
        double porcentaje = listaMuestraDTO.get(position).getPorcentaje();
        double unidadMedida = listaMuestraDTO.get(position).getUnidadMedida();

        TextView tvDatoTipoWbc = (TextView)view.findViewById(R.id.tvDatoTipoWbc);
        tvDatoTipoWbc.setText(idioma);
        TextView tvDatoPorCantidad = (TextView)view.findViewById(R.id.tvDatoPorCantidad);
        tvDatoPorCantidad.setText(String.valueOf(cantidad));
        TextView tvDatoPorcentaje = (TextView)view.findViewById(R.id.tvDatoPorcentaje);
        tvDatoPorcentaje.setText(String.valueOf(porcentaje));
        TextView tvDatoMedida = (TextView)view.findViewById(R.id.tvDatoMedida);
        tvDatoMedida.setText(String.valueOf(unidadMedida));

        return view;
    }
}

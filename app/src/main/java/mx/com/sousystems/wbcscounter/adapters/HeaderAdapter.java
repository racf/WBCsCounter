package mx.com.sousystems.wbcscounter.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import mx.com.sousystems.wbcscounter.R;
import mx.com.sousystems.wbcscounter.dto.HeaderTablaDTO;

public class HeaderAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<HeaderTablaDTO> listaHeaderDTO;
    public HeaderAdapter(Context context, int layout, List<HeaderTablaDTO> listaHeaderDTO){
        this.context = context;
        this.layout = layout;
        this.listaHeaderDTO = listaHeaderDTO;
    }
    @Override
    public int getCount() {
        return this.listaHeaderDTO.size();
    }

    @Override
    public Object getItem(int position) {
        return this.listaHeaderDTO.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        //Inflamos la vista que nos ha llegado con nuestro layout personalizado
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        view = layoutInflater.inflate(R.layout.header_tabla, null);
        String tipo = listaHeaderDTO.get(position).getTipo();
        String cantidad = listaHeaderDTO.get(position).getCantidad();
        String porcentaje = listaHeaderDTO.get(position).getPorcentaje();
        String unidadMedida = listaHeaderDTO.get(position).getUnidadMedida();

        TextView tvHeaderTipoWbc = (TextView)view.findViewById(R.id.tvHeaderTipoWbc);
        tvHeaderTipoWbc.setText(tipo);
        TextView tvHeaderPorCantidad = (TextView)view.findViewById(R.id.tvHeaderPorCantidad);
        tvHeaderPorCantidad.setText(cantidad);
        TextView tvHeaderPorcentaje = (TextView)view.findViewById(R.id.tvHeaderPorcentaje);
        tvHeaderPorcentaje.setText(porcentaje);
        TextView tvHeaderMedida = (TextView)view.findViewById(R.id.tvHeaderMedida);
        tvHeaderMedida.setText(unidadMedida);

        return view;
    }
}

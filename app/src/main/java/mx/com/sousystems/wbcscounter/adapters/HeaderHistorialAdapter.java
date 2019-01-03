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

public class HeaderHistorialAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<HeaderTablaDTO> listaHeaderDTO;
    public HeaderHistorialAdapter(Context context, int layout, List<HeaderTablaDTO> listaHeaderDTO){
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

        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        view = layoutInflater.inflate(R.layout.header_historial_tabla, null);
        String fecha = listaHeaderDTO.get(position).getFecha();
        String nombre = listaHeaderDTO.get(position).getNombre();

        TextView tvHeaderFecha = (TextView)view.findViewById(R.id.tvHeaderFecha);
        tvHeaderFecha.setText(fecha);
        TextView tvHeaderNombre = (TextView)view.findViewById(R.id.tvHeaderNombre);
        tvHeaderNombre.setText(nombre);
        return view;
    }
}

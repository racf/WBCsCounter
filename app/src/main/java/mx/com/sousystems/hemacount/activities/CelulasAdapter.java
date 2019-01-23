package mx.com.sousystems.hemacount.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mx.com.sousystems.hemacount.R;
import mx.com.sousystems.hemacount.domain.Celula;
import mx.com.sousystems.hemacount.domain.MuestraDetalle;

public class CelulasAdapter extends BaseAdapter {
    private List<Celula> celulas;
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<MuestraDetalle> muestraDetalleArrayList;
    public CelulasAdapter(List<Celula> celulas, ArrayList<MuestraDetalle> muestraDetalleArrayList, Context context){
        this.celulas = celulas;
        this.context = context;
        this.muestraDetalleArrayList = muestraDetalleArrayList;
    }
    @Override
    public int getCount() {
        return celulas.size();
    }

    @Override
    public Object getItem(int position) {
        return celulas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        layoutInflater = LayoutInflater.from(this.context);
        convertView = layoutInflater.inflate(R.layout.item_celula, parent, false);
        ImageView ivIconoCelula = (ImageView) convertView.findViewById(R.id.ivIconoCelula);
        TextView tvNombreCelula = (TextView) convertView.findViewById(R.id.tvNombreCelula);
        TextView tvCantidadPorCelula = (TextView) convertView.findViewById(R.id.tvCantidadPorCelula);

        Celula celula = (Celula) getItem(position);
        tvNombreCelula.setText(celula.getNombre());
        tvCantidadPorCelula.setText("0");
        for (MuestraDetalle muestraDetalle:muestraDetalleArrayList) {
            if (muestraDetalle.getCelulaId().equalsIgnoreCase(celula.getId())){
                tvCantidadPorCelula.setText(String.valueOf(muestraDetalle.getCantidad()));
            }
        }
        return convertView;
    }
}

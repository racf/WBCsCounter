package mx.com.sousystems.wbcscounter.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import mx.com.sousystems.wbcscounter.R;

public class CelulasAdapter extends BaseAdapter {
    private String celulas[];
    private Context context;
    private LayoutInflater layoutInflater;
    public CelulasAdapter(String[] celulas, Context context){
        this.celulas = celulas;
        this.context = context;
    }
    @Override
    public int getCount() {
        return celulas.length;
    }

    @Override
    public Object getItem(int position) {
        return celulas[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        layoutInflater = LayoutInflater.from(this.context);
        convertView = layoutInflater.inflate(R.layout.item_celula, parent, false);
        Button btnNombreCelula = (Button) convertView.findViewById(R.id.btnNombreCelula);
        TextView tvCantidadPorCelula = (TextView) convertView.findViewById(R.id.tvCantidadPorCelula);

        String celula = (String) getItem(position);

        btnNombreCelula.setText(celula);
        return convertView;
    }
}

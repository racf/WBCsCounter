package mx.com.sousystems.wbcscounter.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import mx.com.sousystems.wbcscounter.R;
import mx.com.sousystems.wbcscounter.domain.Muestra;

public class TablaHistorialReciclerView extends RecyclerView.Adapter<TablaHistorialReciclerView.ViewHolder> {
    private int layout;
    private List<Muestra> listaMuestra;
    private ViewHolder.OnItemClickListener itemClickListener;

    public TablaHistorialReciclerView(List<Muestra> listaMuestra, int layout, ViewHolder.OnItemClickListener listener) {
        this.layout = layout;
        this.listaMuestra = listaMuestra;
        this.itemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(listaMuestra.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return listaMuestra.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvFecha;
        TextView tvNombre;

        public ViewHolder(View view) {
            super(view);
            this.tvFecha = (TextView) view.findViewById(R.id.tvFecha);
            this.tvNombre = (TextView) view.findViewById(R.id.tvNombre);
        }

        public void bind(final Muestra muestra, final ViewHolder.OnItemClickListener listener){
            this.tvFecha.setText(muestra.getFecha());
            this.tvNombre.setText(muestra.getPaciente().getNombre()+" "+muestra.getPaciente().getPrimerApellido());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(muestra, getAdapterPosition());
                }
            });
        }

        public interface OnItemClickListener{
            void onItemClick(Muestra muestra, int position);
        }
    }
}

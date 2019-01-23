package mx.com.sousystems.hemacount.repository;

import java.util.ArrayList;
import java.util.List;

import mx.com.sousystems.hemacount.domain.Muestra;
import mx.com.sousystems.hemacount.domain.MuestraDetalle;

public interface MuestraDetalleRepository {
    public long crearMuestraDetalle(MuestraDetalle muestraDetalle);
    public long crearMuestraDetalleTransaccion(Muestra muestra, ArrayList<MuestraDetalle> listaMuestraDetalle);
    public List<MuestraDetalle> obtenerTodasMuestraDetalle();
    public List<MuestraDetalle> obtenerMuestraDetallePorMuestraId(Integer muestraId);
}

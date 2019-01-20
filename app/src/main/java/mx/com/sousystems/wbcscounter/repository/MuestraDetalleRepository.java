package mx.com.sousystems.wbcscounter.repository;

import java.util.ArrayList;
import java.util.List;

import mx.com.sousystems.wbcscounter.domain.Muestra;
import mx.com.sousystems.wbcscounter.domain.MuestraDetalle;

public interface MuestraDetalleRepository {
    public long crearMuestraDetalle(MuestraDetalle muestraDetalle);
    public long crearMuestraDetalleTransaccion(Muestra muestra, ArrayList<MuestraDetalle> listaMuestraDetalle);
    public List<MuestraDetalle> obtenerTodasMuestraDetalle();
    public List<MuestraDetalle> obtenerMuestraDetallePorMuestraId(Integer muestraId);
}

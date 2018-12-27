package mx.com.sousystems.wbcscounter.repository;

import java.util.List;
import mx.com.sousystems.wbcscounter.domain.Muestra;

public interface MuestraRepository {

    public long crearMuestra(Muestra muestra);
    public boolean actualizarMuestra(Muestra muestra);
    public boolean eliminarMuestra(Muestra muestra);
    public List<Muestra> obtenerMuestras(Integer pacienteId, String fechaIni, String fechaFin);
    public List<Muestra> obtenerTodasMuestras();
    public Muestra obtenerMuestraPorId(String id);
    public Muestra obtenerUltimaMuestra();
}

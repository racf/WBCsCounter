package mx.com.sousystems.wbcscounter.repository;

import java.util.List;

import mx.com.sousystems.wbcscounter.domain.Celula;

public interface CelulaRepository {
    public boolean crearCelula(Celula paciente);
    public boolean actualizarCelula(Celula paciente);
    public boolean eliminarCelula(Celula paciente);
    public List<Celula> obtenerTodosCelulas();
    public Celula obtenerCelulaPorId(String id);
}

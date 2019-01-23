package mx.com.sousystems.hemacount.repository;

import java.util.List;

import mx.com.sousystems.hemacount.domain.Celula;

public interface CelulaRepository {

    public boolean crearCelula(Celula celula);
    public boolean actualizarCelula(Celula celula);
    public boolean eliminarCelula(Celula celula);
    public List<Celula> obtenerTodasCelulas();
    public Celula obtenerCelulaPorId(String id);
}

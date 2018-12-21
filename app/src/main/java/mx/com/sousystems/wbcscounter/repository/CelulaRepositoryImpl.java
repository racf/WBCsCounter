package mx.com.sousystems.wbcscounter.repository;

import java.util.List;

import mx.com.sousystems.wbcscounter.domain.Celula;

public class CelulaRepositoryImpl implements CelulaRepository {
    @Override
    public boolean crearCelula(Celula paciente) {
        return false;
    }

    @Override
    public boolean actualizarCelula(Celula paciente) {
        return false;
    }

    @Override
    public boolean eliminarCelula(Celula paciente) {
        return false;
    }

    @Override
    public List<Celula> obtenerTodosCelulas() {
        return null;
    }

    @Override
    public Celula obtenerCelulaPorId(String id) {
        return null;
    }
}

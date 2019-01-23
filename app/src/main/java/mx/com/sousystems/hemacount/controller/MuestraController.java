package mx.com.sousystems.hemacount.controller;

import android.content.Context;

import java.util.List;

import mx.com.sousystems.hemacount.domain.Muestra;
import mx.com.sousystems.hemacount.repository.MuestraRepository;
import mx.com.sousystems.hemacount.repository.MuestraRepositoryImpl;

public class MuestraController {
    MuestraRepository muestraRepository;

    public MuestraController(Context context){
        muestraRepository = new MuestraRepositoryImpl(context);
    }

    public long crearMuestra(Muestra muestra) {
        return  muestraRepository.crearMuestra(muestra);
    }

    public List<Muestra> obtenerMuestras(Integer pacienteId, String fechaIni, String fechaFin) {
        return muestraRepository.obtenerMuestras(pacienteId, fechaIni, fechaFin);
    }

    public List<Muestra> obtenerTodasMuestras() {
        return muestraRepository.obtenerTodasMuestras();
    }

    public boolean eliminarMuestraTransaccion(Muestra muestra) {
        return muestraRepository.eliminarMuestraTransaccion(muestra);
    }
}

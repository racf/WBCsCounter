package mx.com.sousystems.hemacount.controller;

import android.content.Context;

import java.util.List;

import mx.com.sousystems.hemacount.domain.Celula;
import mx.com.sousystems.hemacount.repository.CelulaRepository;
import mx.com.sousystems.hemacount.repository.CelulaRepositoryImpl;

public class CelulaController {
    CelulaRepository celulaRepository;

    public CelulaController(Context context){
         celulaRepository = new CelulaRepositoryImpl(context);
    }

    public List<Celula> obtenerTodasCelulas() {
        return celulaRepository.obtenerTodasCelulas();
    }
}

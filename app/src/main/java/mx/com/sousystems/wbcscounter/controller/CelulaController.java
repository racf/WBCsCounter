package mx.com.sousystems.wbcscounter.controller;

import android.content.Context;

import java.util.List;

import mx.com.sousystems.wbcscounter.domain.Celula;
import mx.com.sousystems.wbcscounter.repository.CelulaRepository;
import mx.com.sousystems.wbcscounter.repository.CelulaRepositoryImpl;

public class CelulaController {
    CelulaRepository celulaRepository;

    public CelulaController(Context context){
         celulaRepository = new CelulaRepositoryImpl(context);
    }

    public List<Celula> obtenerTodasCelulas() {
        return celulaRepository.obtenerTodasCelulas();
    }
}

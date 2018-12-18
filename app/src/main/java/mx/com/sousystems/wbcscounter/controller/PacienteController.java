package mx.com.sousystems.wbcscounter.controller;

import android.content.Context;

import mx.com.sousystems.wbcscounter.repository.PacienteRepository;
import mx.com.sousystems.wbcscounter.repository.PacienteRepositoryImpl;

public class PacienteController {
    PacienteRepository pacienteRepository;

    public PacienteController(Context context){
        pacienteRepository = new PacienteRepositoryImpl(context);
    }


}

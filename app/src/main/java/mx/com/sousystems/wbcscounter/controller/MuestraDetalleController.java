package mx.com.sousystems.wbcscounter.controller;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import mx.com.sousystems.wbcscounter.domain.Muestra;
import mx.com.sousystems.wbcscounter.domain.MuestraDetalle;
import mx.com.sousystems.wbcscounter.repository.MuestraDetalleRepository;
import mx.com.sousystems.wbcscounter.repository.MuestraDetalleRepositoryImpl;

public class MuestraDetalleController {
    MuestraDetalleRepository muestrarDetalleRepository;

    public MuestraDetalleController(Context context){
        muestrarDetalleRepository = new MuestraDetalleRepositoryImpl(context);
    }

    public long crearMuestraDetalle(MuestraDetalle muestraDetalle) {
        return muestrarDetalleRepository.crearMuestraDetalle(muestraDetalle);
    }

    public long crearMuestraDetalleTransaccion(Muestra muestra, ArrayList<MuestraDetalle> listaMuestraDetalle) {
        return muestrarDetalleRepository.crearMuestraDetalleTransaccion(muestra, listaMuestraDetalle);
    }

    public List<MuestraDetalle> obtenerTodasMuestraDetalle() {
        return muestrarDetalleRepository.obtenerTodasMuestraDetalle();
    }

}

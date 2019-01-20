package mx.com.sousystems.wbcscounter.controller;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import mx.com.sousystems.wbcscounter.domain.Muestra;
import mx.com.sousystems.wbcscounter.domain.MuestraDetalle;
import mx.com.sousystems.wbcscounter.repository.MuestraDetalleRepository;
import mx.com.sousystems.wbcscounter.repository.MuestraDetalleRepositoryImpl;

public class MuestraDetalleController {
    MuestraDetalleRepository muestraDetalleRepository;

    public MuestraDetalleController(Context context){
        muestraDetalleRepository = new MuestraDetalleRepositoryImpl(context);
    }

    public long crearMuestraDetalle(MuestraDetalle muestraDetalle) {
        return muestraDetalleRepository.crearMuestraDetalle(muestraDetalle);
    }

    public long crearMuestraDetalleTransaccion(Muestra muestra, ArrayList<MuestraDetalle> listaMuestraDetalle) {
        return muestraDetalleRepository.crearMuestraDetalleTransaccion(muestra, listaMuestraDetalle);
    }

    public List<MuestraDetalle> obtenerMuestraDetallePorMuestraId(Integer muestraId) {
        return muestraDetalleRepository.obtenerMuestraDetallePorMuestraId(muestraId);
    }

    public List<MuestraDetalle> obtenerTodasMuestraDetalle() {
        return muestraDetalleRepository.obtenerTodasMuestraDetalle();
    }

}

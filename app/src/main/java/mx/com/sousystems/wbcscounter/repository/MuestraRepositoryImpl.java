package mx.com.sousystems.wbcscounter.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import mx.com.sousystems.wbcscounter.config.DbConfigHelper;
import mx.com.sousystems.wbcscounter.domain.Muestra;
import mx.com.sousystems.wbcscounter.util.ConstanteTabla;

public class MuestraRepositoryImpl implements MuestraRepository {
    DbConfigHelper dbConfigHelper;
    public MuestraRepositoryImpl(Context context){
        dbConfigHelper = new DbConfigHelper(context);
    }
    @Override
    public long crearMuestra(Muestra muestra) {
        long band;
        if(muestra != null){
            SQLiteDatabase db = dbConfigHelper.getWritableDatabase();
            db.beginTransaction();
            ContentValues values = new ContentValues();
            muestra.setEstatus(ConstanteTabla.ESTADO_1);
            values.put(ConstanteTabla.PACIENTE_ID, muestra.getPacienteId());
            values.put(ConstanteTabla.CANTIDAD_INPUT, muestra.getCantidadInput());
            values.put(ConstanteTabla.FECHA, muestra.getFecha());
            values.put(ConstanteTabla.TOTAL_WBC_SNRBC, muestra.getTotalWbcSnrbc());
            values.put(ConstanteTabla.TOTAL_WBC_CNRBC, muestra.getTotalWbcCnrbc());
            values.put(ConstanteTabla.ESTATUS, muestra.getEstatus());
            long newRowId = db.insert(ConstanteTabla.MUESTRA, null, values);
            band = newRowId;
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        }else{
            band = -1;
        }
        return band;
    }

    @Override
    public boolean actualizarMuestra(Muestra muestra) {
        return false;
    }

    @Override
    public boolean eliminarMuestra(Muestra muestra) {
        return false;
    }

    @Override
    public List<Muestra> obtenerMuestras(Integer pacienteId, String fechaIni, String fechaFin) {
        return null;
    }

    @Override
    public List<Muestra> obtenerTodasMuestras() {
        List<Muestra> lista = new ArrayList<>();
        SQLiteDatabase db = dbConfigHelper.getReadableDatabase();

        String[] projection = {
                ConstanteTabla.ID,
                ConstanteTabla.PACIENTE_ID,
                ConstanteTabla.CANTIDAD_INPUT,
                ConstanteTabla.FECHA,
                ConstanteTabla.TOTAL_WBC_SNRBC,
                ConstanteTabla.TOTAL_WBC_CNRBC,
                ConstanteTabla.ESTATUS
        };
        String selection = ConstanteTabla.ESTATUS + " = 1";
        Cursor cursor = db.query(
                ConstanteTabla.MUESTRA,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                       // The sort order
        );
        if (cursor.moveToFirst()) {
            do {
                Muestra muestra = new Muestra();
                muestra.setId(cursor.getInt(0));
                muestra.setPacienteId(cursor.getInt(1));
                muestra.setCantidadInput(cursor.getInt(2));
                muestra.setFecha(cursor.getString(3));
                muestra.setTotalWbcSnrbc(cursor.getDouble(4));
                muestra.setTotalWbcCnrbc(cursor.getDouble(5));
                muestra.setEstatus(cursor.getInt(6));

                // Adding to list
                lista.add(muestra);
            } while (cursor.moveToNext());
        }
        db.close();
        return lista;
    }

    @Override
    public Muestra obtenerMuestraPorId(String id) {
        return null;
    }

    @Override
    public Muestra obtenerUltimaMuestra() {
        return null;
    }
}

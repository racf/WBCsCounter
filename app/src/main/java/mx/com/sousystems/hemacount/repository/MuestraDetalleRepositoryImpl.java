package mx.com.sousystems.hemacount.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import mx.com.sousystems.hemacount.config.DbConfigHelper;
import mx.com.sousystems.hemacount.domain.Muestra;
import mx.com.sousystems.hemacount.domain.MuestraDetalle;
import mx.com.sousystems.hemacount.util.ConstanteTabla;

public class MuestraDetalleRepositoryImpl implements MuestraDetalleRepository {
    DbConfigHelper dbConfigHelper;

    public MuestraDetalleRepositoryImpl(Context context){
        dbConfigHelper = new DbConfigHelper(context);
    }

    @Override
    public long crearMuestraDetalle(MuestraDetalle muestraDetalle) {
        long band;
        if(muestraDetalle != null){
            SQLiteDatabase db = dbConfigHelper.getWritableDatabase();
            db.beginTransaction();
            ContentValues values = new ContentValues();
            muestraDetalle.setEstatus(ConstanteTabla.ESTADO_1);
            values.put(ConstanteTabla.MUESTRA_ID, muestraDetalle.getMuestraId());
            values.put(ConstanteTabla.CELULA_ID, muestraDetalle.getCelulaId());
            values.put(ConstanteTabla.CANTIDAD, muestraDetalle.getCantidad());
            values.put(ConstanteTabla.ESTATUS, muestraDetalle.getEstatus());

            long newRowId = db.insert(ConstanteTabla.MUESTRA_DETALLE, null, values);
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
    public long crearMuestraDetalleTransaccion(Muestra muestra, ArrayList<MuestraDetalle> listaMuestraDetalle) {
        long band;
        long newRowId;
        long muestraId;
        if(muestra != null){
            SQLiteDatabase db = dbConfigHelper.getWritableDatabase();
            db.beginTransaction();
            ContentValues valuesMuestra = new ContentValues();
            muestra.setEstatus(ConstanteTabla.ESTADO_1);
            valuesMuestra.put(ConstanteTabla.PACIENTE_ID, muestra.getPacienteId());
            valuesMuestra.put(ConstanteTabla.CANTIDAD_INPUT, muestra.getCantidadInput());
            valuesMuestra.put(ConstanteTabla.CANTIDAD_TOTAL_CELULA, muestra.getCantidadTotalCelula());
            valuesMuestra.put(ConstanteTabla.FECHA, muestra.getFecha());
            valuesMuestra.put(ConstanteTabla.TOTAL_WBC_SNRBC, muestra.getTotalWbcSnrbc());
            valuesMuestra.put(ConstanteTabla.TOTAL_WBC_CNRBC, muestra.getTotalWbcCnrbc());
            valuesMuestra.put(ConstanteTabla.ESTATUS, muestra.getEstatus());
            muestraId = db.insert(ConstanteTabla.MUESTRA, null, valuesMuestra);
            band = muestraId;
            ContentValues valuesMuestraDetalle = new ContentValues();
            for (MuestraDetalle muestraDetalle:listaMuestraDetalle) {
                muestraDetalle.setEstatus(ConstanteTabla.ESTADO_1);
                valuesMuestraDetalle.put(ConstanteTabla.MUESTRA_ID, muestraId);
                valuesMuestraDetalle.put(ConstanteTabla.CELULA_ID, muestraDetalle.getCelulaId());
                valuesMuestraDetalle.put(ConstanteTabla.CANTIDAD, muestraDetalle.getCantidad());
                valuesMuestraDetalle.put(ConstanteTabla.ESTATUS, muestraDetalle.getEstatus());
                newRowId = db.insert(ConstanteTabla.MUESTRA_DETALLE, null, valuesMuestraDetalle);
                band = newRowId;
            }
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        }else{
            band = -1;
        }
        return band;
    }


    @Override
    public List<MuestraDetalle> obtenerTodasMuestraDetalle() {
        List<MuestraDetalle> lista = new ArrayList<>();
        SQLiteDatabase db = dbConfigHelper.getReadableDatabase();

        String[] projection = {
                ConstanteTabla.ID,
                ConstanteTabla.MUESTRA_ID,
                ConstanteTabla.CELULA_ID,
                ConstanteTabla.CANTIDAD,
                ConstanteTabla.ESTATUS
        };
        String selection = ConstanteTabla.ESTATUS + " = ?";
        String[] selectionArgs = {String.valueOf(1)};
        Cursor cursor = db.query(
                ConstanteTabla.MUESTRA_DETALLE,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                       // The sort order
        );
        if (cursor.moveToFirst()) {
            do {
                MuestraDetalle muestraDetalle = new MuestraDetalle();
                muestraDetalle.setId(cursor.getInt(0));
                muestraDetalle.setMuestraId(cursor.getLong(1));
                muestraDetalle.setCelulaId(cursor.getString(2));
                muestraDetalle.setCantidad(cursor.getInt(3));
                muestraDetalle.setEstatus(cursor.getInt(4));

                // Adding to list
                lista.add(muestraDetalle);
            } while (cursor.moveToNext());
        }
        db.close();
        return lista;
    }

    @Override
    public List<MuestraDetalle> obtenerMuestraDetallePorMuestraId(Integer muestraId) {
        List<MuestraDetalle> lista = new ArrayList<>();
        SQLiteDatabase db = dbConfigHelper.getReadableDatabase();

        String[] projection = {
                ConstanteTabla.ID,
                ConstanteTabla.MUESTRA_ID,
                ConstanteTabla.CELULA_ID,
                ConstanteTabla.CANTIDAD,
                ConstanteTabla.ESTATUS
        };
        String selection = ConstanteTabla.ESTATUS +ConstanteTabla.IGUAL+ConstanteTabla.SIGNO_INTERROGACION+ConstanteTabla.ESPACIO+ConstanteTabla.AND+ConstanteTabla.ESPACIO+ConstanteTabla.MUESTRA_ID+ConstanteTabla.IGUAL+ConstanteTabla.SIGNO_INTERROGACION;
        String[] selectionArgs = {String.valueOf(1), String.valueOf(muestraId)};
        Cursor cursor = db.query(
                ConstanteTabla.MUESTRA_DETALLE,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                       // The sort order
        );
        if (cursor.moveToFirst()) {
            do {
                MuestraDetalle muestraDetalle = new MuestraDetalle();
                muestraDetalle.setId(cursor.getInt(0));
                muestraDetalle.setMuestraId(cursor.getLong(1));
                muestraDetalle.setCelulaId(cursor.getString(2));
                muestraDetalle.setCantidad(cursor.getInt(3));
                muestraDetalle.setEstatus(cursor.getInt(4));

                // Adding to list
                lista.add(muestraDetalle);
            } while (cursor.moveToNext());
        }
        db.close();
        return lista;
    }
}

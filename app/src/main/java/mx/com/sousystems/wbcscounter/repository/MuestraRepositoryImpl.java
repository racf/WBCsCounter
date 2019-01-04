package mx.com.sousystems.wbcscounter.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import mx.com.sousystems.wbcscounter.config.DbConfigHelper;
import mx.com.sousystems.wbcscounter.domain.Muestra;
import mx.com.sousystems.wbcscounter.domain.Paciente;
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
    public boolean eliminarMuestraTransaccion(Muestra muestra) {
        boolean bandGral = false;
        boolean bandMuestra = false;
        boolean bandMuestraDetalle = false;
        if(muestra != null){
            SQLiteDatabase db = dbConfigHelper.getWritableDatabase();
            db.beginTransaction();
            // Define 'where' part of query.
            String selectionMuestra = ConstanteTabla.ID + " = ?";
            // Specify arguments in placeholder order.
            String[] selectionMuestraArgs = {String.valueOf(muestra.getId())};
            // Issue SQL statement.
            long countMuestra = db.delete(ConstanteTabla.MUESTRA, selectionMuestra, selectionMuestraArgs);
            if(countMuestra >= 1){
                bandMuestra = true;
            }else{
                bandMuestra = false;
            }

            String selectionMuestraDetalle = ConstanteTabla.MUESTRA_ID + " = ?";
            String[] selectionMuestraDetalleArgs = {String.valueOf(muestra.getId())};
            long countMuestraDetalle = db.delete(ConstanteTabla.MUESTRA_DETALLE, selectionMuestraDetalle, selectionMuestraDetalleArgs);
            if(countMuestraDetalle >= 1){
                bandMuestraDetalle = true;
            }else{
                bandMuestraDetalle = false;
            }
            if(bandMuestra && bandMuestraDetalle){
                bandGral = true;
            }
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        }
        return bandGral;
    }

    @Override
    public List<Muestra> obtenerMuestras(Integer pacienteId, String fechaIni, String fechaFin) {
        List<Muestra> lista = new ArrayList<>();
        String query = ConstanteTabla.SELECT.concat(ConstanteTabla.ESPACIO).concat(ConstanteTabla.MUESTRA).concat(ConstanteTabla.PUNTO).concat(ConstanteTabla.ID).concat(ConstanteTabla.COMA)
                +ConstanteTabla.ESPACIO.concat(ConstanteTabla.MUESTRA).concat(ConstanteTabla.PUNTO).concat(ConstanteTabla.PACIENTE_ID).concat(ConstanteTabla.COMA)
                +ConstanteTabla.ESPACIO.concat(ConstanteTabla.MUESTRA).concat(ConstanteTabla.PUNTO).concat(ConstanteTabla.CANTIDAD_INPUT).concat(ConstanteTabla.COMA)
                +ConstanteTabla.ESPACIO.concat(ConstanteTabla.MUESTRA).concat(ConstanteTabla.PUNTO).concat(ConstanteTabla.FECHA).concat(ConstanteTabla.COMA)
                +ConstanteTabla.ESPACIO.concat(ConstanteTabla.MUESTRA).concat(ConstanteTabla.PUNTO).concat(ConstanteTabla.TOTAL_WBC_SNRBC).concat(ConstanteTabla.COMA)
                +ConstanteTabla.ESPACIO.concat(ConstanteTabla.MUESTRA).concat(ConstanteTabla.PUNTO).concat(ConstanteTabla.TOTAL_WBC_CNRBC).concat(ConstanteTabla.COMA)
                +ConstanteTabla.ESPACIO.concat(ConstanteTabla.MUESTRA).concat(ConstanteTabla.PUNTO).concat(ConstanteTabla.ESTATUS).concat(ConstanteTabla.COMA)
                +ConstanteTabla.ESPACIO.concat(ConstanteTabla.PACIENTE).concat(ConstanteTabla.PUNTO).concat(ConstanteTabla.ID).concat(ConstanteTabla.COMA)
                +ConstanteTabla.ESPACIO.concat(ConstanteTabla.PACIENTE).concat(ConstanteTabla.PUNTO).concat(ConstanteTabla.NOMBRE).concat(ConstanteTabla.COMA)
                +ConstanteTabla.ESPACIO.concat(ConstanteTabla.PACIENTE).concat(ConstanteTabla.PUNTO).concat(ConstanteTabla.PRIMER_APELLIDO).concat(ConstanteTabla.COMA)
                +ConstanteTabla.ESPACIO.concat(ConstanteTabla.PACIENTE).concat(ConstanteTabla.PUNTO).concat(ConstanteTabla.SEGUNDO_APELLIDO).concat(ConstanteTabla.COMA)
                +ConstanteTabla.ESPACIO.concat(ConstanteTabla.PACIENTE).concat(ConstanteTabla.PUNTO).concat(ConstanteTabla.SEXO).concat(ConstanteTabla.COMA)
                +ConstanteTabla.ESPACIO.concat(ConstanteTabla.PACIENTE).concat(ConstanteTabla.PUNTO).concat(ConstanteTabla.TELEFONO)
                +ConstanteTabla.ESPACIO.concat(ConstanteTabla.FROM).concat(ConstanteTabla.ESPACIO).concat(ConstanteTabla.MUESTRA)
                +ConstanteTabla.ESPACIO.concat(ConstanteTabla.INNER).concat(ConstanteTabla.ESPACIO).concat(ConstanteTabla.JOIN).concat(ConstanteTabla.ESPACIO).concat(ConstanteTabla.PACIENTE).concat(ConstanteTabla.ESPACIO).concat(ConstanteTabla.ON).concat(ConstanteTabla.ESPACIO).concat(ConstanteTabla.MUESTRA).concat(ConstanteTabla.PUNTO).concat(ConstanteTabla.PACIENTE_ID).concat(ConstanteTabla.IGUAL).concat(ConstanteTabla.PACIENTE).concat(ConstanteTabla.PUNTO).concat(ConstanteTabla.ID)
                +ConstanteTabla.ESPACIO.concat(ConstanteTabla.WHERE).concat(ConstanteTabla.ESPACIO).concat(ConstanteTabla.MUESTRA).concat(ConstanteTabla.PUNTO).concat(ConstanteTabla.ESTATUS).concat(ConstanteTabla.ESPACIO).concat(ConstanteTabla.IGUAL).concat(ConstanteTabla.ESPACIO)+ConstanteTabla.ESTADO_1;
        if(pacienteId == 1){
            query += ConstanteTabla.ESPACIO.concat(ConstanteTabla.AND).concat(ConstanteTabla.ESPACIO).concat(ConstanteTabla.MUESTRA).concat(ConstanteTabla.PUNTO).concat(ConstanteTabla.FECHA).concat(ConstanteTabla.ESPACIO).concat(ConstanteTabla.BETWEEN).concat(ConstanteTabla.ESPACIO).concat(ConstanteTabla.COMILLA_SIMPLE).concat(fechaIni).concat(ConstanteTabla.COMILLA_SIMPLE).concat(ConstanteTabla.ESPACIO).concat(ConstanteTabla.AND).concat(ConstanteTabla.ESPACIO).concat(ConstanteTabla.COMILLA_SIMPLE).concat(fechaFin).concat(ConstanteTabla.COMILLA_SIMPLE);
        }else{
            query += ConstanteTabla.ESPACIO.concat(ConstanteTabla.AND).concat(ConstanteTabla.ESPACIO).concat(ConstanteTabla.MUESTRA).concat(ConstanteTabla.PUNTO).concat(ConstanteTabla.PACIENTE_ID).concat(ConstanteTabla.IGUAL).concat(""+pacienteId+"").concat(ConstanteTabla.ESPACIO).concat(ConstanteTabla.AND).concat(ConstanteTabla.ESPACIO).concat(ConstanteTabla.MUESTRA).concat(ConstanteTabla.PUNTO).concat(ConstanteTabla.FECHA).concat(ConstanteTabla.ESPACIO).concat(ConstanteTabla.BETWEEN).concat(ConstanteTabla.ESPACIO).concat(ConstanteTabla.COMILLA_SIMPLE).concat(fechaIni).concat(ConstanteTabla.COMILLA_SIMPLE).concat(ConstanteTabla.ESPACIO).concat(ConstanteTabla.AND).concat(ConstanteTabla.ESPACIO).concat(ConstanteTabla.COMILLA_SIMPLE).concat(fechaFin).concat(ConstanteTabla.COMILLA_SIMPLE);
        }
        SQLiteDatabase db = dbConfigHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
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

                Paciente paciente = new Paciente();
                paciente.setId(cursor.getInt(7));
                paciente.setNombre(cursor.getString(8));
                paciente.setPrimerApellido(cursor.getString(9));
                paciente.setSegundoApellido(cursor.getString(10));
                paciente.setSexo(cursor.getString(11));
                paciente.setTelefono(cursor.getString(12));
                muestra.setPaciente(paciente);
                lista.add(muestra);
            }while (cursor.moveToNext());
        }
        db.close();
        return lista;
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

}

package mx.com.sousystems.wbcscounter.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import mx.com.sousystems.wbcscounter.config.DbConfigHelper;
import mx.com.sousystems.wbcscounter.domain.Paciente;
import mx.com.sousystems.wbcscounter.util.ConstanteTabla;

public class PacienteRepositoryImpl  implements  PacienteRepository{
    DbConfigHelper dbConfigHelper;
    public PacienteRepositoryImpl(Context context){
        dbConfigHelper = new DbConfigHelper(context);
    }
    @Override
    public boolean crearPaciente(Paciente paciente) {
        boolean band;
        if(paciente != null){
            SQLiteDatabase db = dbConfigHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            paciente.setEstatus(ConstanteTabla.ESTADO_1);
            values.put(ConstanteTabla.NOMBRE, paciente.getNombre());
            values.put(ConstanteTabla.PRIMER_APELLIDO, paciente.getPrimerApellido());
            values.put(ConstanteTabla.SEGUNDO_APELLIDO, paciente.getSegundoApellido());
            values.put(ConstanteTabla.SEXO, paciente.getSexo());
            values.put(ConstanteTabla.TELEFONO, paciente.getTelefono());
            values.put(ConstanteTabla.ESTATUS, paciente.getEstatus());
            long newRowId = db.insert(ConstanteTabla.PACIENTE, null, values);
            band = newRowId != -1;
            db.close();
        }else{
            band = false;
        }
        return band;
    }

    @Override
    public boolean actualizarPaciente(Paciente paciente) {
        boolean band;
        SQLiteDatabase db = dbConfigHelper.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(ConstanteTabla.ID, paciente.getId());
        values.put(ConstanteTabla.NOMBRE, paciente.getNombre());
        values.put(ConstanteTabla.PRIMER_APELLIDO, paciente.getPrimerApellido());
        values.put(ConstanteTabla.SEGUNDO_APELLIDO, paciente.getSegundoApellido());
        values.put(ConstanteTabla.SEXO, paciente.getSexo());
        values.put(ConstanteTabla.TELEFONO, paciente.getTelefono());

        String selection = ConstanteTabla.ID + " = ?";
        String [] selectionArgs = {String.valueOf(paciente.getId())};
        int count = db.update(
                ConstanteTabla.PACIENTE,
                values,
                selection,
                selectionArgs);
        if(count >= 1){
            band = true;
        }else{
            band = false;
        }
        db.close();

        return band;
    }

    @Override
    public boolean eliminarPaciente(Paciente paciente) {
        boolean band;
        SQLiteDatabase db = dbConfigHelper.getReadableDatabase();

        try{
            String selection = ConstanteTabla.ID + " LIKE ?";
            String[] selectionArgs = {String.valueOf(paciente.getId())};
            int count = db.delete(ConstanteTabla.PACIENTE, selection, selectionArgs);
            if(count >= 1){
                band = true;
            }else{
                band = false;
            }
            db.close();
        }catch (Exception ex){
            band = false;
        }

        return band;
    }

    @Override
    public List<Paciente> obtenerTodosPacientes() {
        List<Paciente> lista = new ArrayList<>();
        String query = ConstanteTabla.SELECT+ConstanteTabla.ESPACIO+ConstanteTabla.ASTERISCO+ConstanteTabla.ESPACIO+ConstanteTabla.FROM+ConstanteTabla.ESPACIO+ConstanteTabla.PACIENTE
                +ConstanteTabla.ESPACIO+ConstanteTabla.WHERE+ConstanteTabla.ESPACIO+ConstanteTabla.ESTATUS+ConstanteTabla.ESPACIO+ConstanteTabla.IGUAL+ConstanteTabla.ESPACIO+ConstanteTabla.ESTADO_1;
        SQLiteDatabase db = dbConfigHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Paciente paciente = new Paciente();
                paciente.setId(cursor.getInt(0));
                paciente.setNombre(cursor.getString(1));
                paciente.setPrimerApellido(cursor.getString(2));
                paciente.setSegundoApellido(cursor.getString(3));
                paciente.setSexo(cursor.getString(4));
                paciente.setTelefono(cursor.getString(5));
                paciente.setEstatus(cursor.getInt(6));
                lista.add(paciente);
            }while (cursor.moveToNext());
        }
        db.close();
        return lista;
    }

    @Override
    public Paciente obtenerPacientePorId(String id) {
        Paciente paciente = new Paciente();

        SQLiteDatabase db = dbConfigHelper.getReadableDatabase();
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                ConstanteTabla.ID,
                ConstanteTabla.NOMBRE,
                ConstanteTabla.PRIMER_APELLIDO,
                ConstanteTabla.SEGUNDO_APELLIDO,
                ConstanteTabla.SEXO,
                ConstanteTabla.TELEFONO,
                ConstanteTabla.ESTATUS
        };

        String selection = ConstanteTabla.ID + " = ?";
        String[] selectionArgs = {id};
        Cursor cursor = db.query(
                ConstanteTabla.PACIENTE,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                       // The sort order
        );

        if(cursor != null) {
            if(cursor.moveToFirst()){
                paciente.setId(cursor.getInt(0));
                paciente.setNombre(cursor.getString(1));
                paciente.setPrimerApellido(cursor.getString(2));
                paciente.setSegundoApellido(cursor.getString(3));
                paciente.setSexo(cursor.getString(4));
                paciente.setTelefono(cursor.getString(5));
                paciente.setEstatus(cursor.getInt(6));
            }else{
                paciente = null;
            }
        }else{
            paciente = null;
        }
        db.close();
        return paciente;
    }
}

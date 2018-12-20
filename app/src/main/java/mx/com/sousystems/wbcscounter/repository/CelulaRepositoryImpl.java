package mx.com.sousystems.wbcscounter.repository;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import mx.com.sousystems.wbcscounter.config.DbConfigHelper;
import mx.com.sousystems.wbcscounter.domain.Celula;
import mx.com.sousystems.wbcscounter.util.ConstanteTabla;

public class CelulaRepositoryImpl implements CelulaRepository {
    DbConfigHelper dbConfigHelper;

    public CelulaRepositoryImpl(Context context){
        dbConfigHelper = new DbConfigHelper(context);
    }
    @Override
    public boolean crearCelula(Celula celula) {
        return false;
    }

    @Override
    public boolean actualizarCelula(Celula celula) {
        return false;
    }

    @Override
    public boolean eliminarCelula(Celula celula) {
        return false;
    }

    @Override
    public List<Celula> obtenerTodasCelulas() {
        List<Celula> lista = new ArrayList<>();
        SQLiteDatabase db = dbConfigHelper.getReadableDatabase();
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                ConstanteTabla.ID,
                ConstanteTabla.NOMBRE,
                ConstanteTabla.NOMBRE_ABREV,
                ConstanteTabla.DESCRIPCION,
                ConstanteTabla.ESTATUS
        };
        String selection = ConstanteTabla.ESTATUS + " = 1";
        Cursor cursor = db.query(
                ConstanteTabla.CELULA,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                       // The sort order
        );
        if (cursor.moveToFirst()) {
            do {
                Celula celula = new Celula();
                celula.setId(cursor.getString(0));
                celula.setNombre(cursor.getString(1));
                celula.setNombreAbrev(cursor.getString(2));
                celula.setDescripcion(cursor.getString(3));
                celula.setEstatus(cursor.getInt(4));
                // Adding to list
                lista.add(celula);
            } while (cursor.moveToNext());
        }
        db.close();
        return lista;

    }

    @Override
    public Celula obtenerCelulaPorId(String id) {
        return null;
    }
}

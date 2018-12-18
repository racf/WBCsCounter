package mx.com.sousystems.wbcscounter.config;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbConfigHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "counter.db";

    public DbConfigHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void deleteDatabase(Context context) {
        context.deleteDatabase(DATABASE_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(GeneralQuery.CREAR_TABLA_PACIENTE);
        db.execSQL(GeneralQuery.CREAR_TABLA_MUESTRA);
        db.execSQL(GeneralQuery.CREAR_TABLA_MUESTRA_DETALLE);
        db.execSQL(GeneralQuery.CREAR_TABLA_CELULA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Se elimina la versión anterior de la tabla
        db.execSQL(GeneralQuery.ELIMINAR_TABLA_PACIENTE);
        db.execSQL(GeneralQuery.ELIMINAR_TABLA_MUESTRA);
        db.execSQL(GeneralQuery.ELIMINAR_TABLA_MUESTRA_DETALLE);
        db.execSQL(GeneralQuery.ELIMINAR_TABLA_CELULA);

        //Se crea la nueva versión de las tablas
        onCreate(db);
    }
}

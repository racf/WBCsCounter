package mx.com.sousystems.wbcscounter.config;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DbConfigHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "counter.db";
    Context context;

    public DbConfigHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public void deleteDatabase(Context context) {
        context.deleteDatabase(DATABASE_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("---CARGA DB--", "POR PRIMERA VEZ");
        db.beginTransaction();
        try{
            db.execSQL(GeneralQuery.CREAR_TABLA_PACIENTE);
            db.execSQL(GeneralQuery.CREAR_TABLA_MUESTRA);
            db.execSQL(GeneralQuery.CREAR_TABLA_MUESTRA_DETALLE);
            db.execSQL(GeneralQuery.CREAR_TABLA_CELULA);
            readFromfile("import.sql", context, db);
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }

    }



    public String readFromfile(String fileName, Context context, SQLiteDatabase db) {
        StringBuilder returnString = new StringBuilder();
        InputStream fIn = null;
        InputStreamReader isr = null;
        BufferedReader input = null;
        try {
            fIn = context.getResources().getAssets()
                    .open(fileName, Context.MODE_WORLD_READABLE);
            isr = new InputStreamReader(fIn);
            input = new BufferedReader(isr);
            String line = "";
            while ((line = input.readLine()) != null) {
                db.execSQL(line);
                Log.i("---CARGA DB--", line);
                returnString.append(line);
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            try {
                if (isr != null)
                    isr.close();
                if (fIn != null)
                    fIn.close();
                if (input != null)
                    input.close();
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
        return returnString.toString();
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

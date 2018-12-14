package mx.com.sousystems.wbcscounter.util;

import android.provider.BaseColumns;

public class ConstanteTabla implements BaseColumns {

    private ConstanteTabla() {
        throw new IllegalStateException("ConstanteTabla class");
    }
    //Nombres de tablas
    public static final String PACIENTE = "paciente";
    public static final String CELULA = "celula";
    public static final String MUESTRA = "muestra";
    public static final String MUESTRA_DETALLE = "muestra_detalle";

    //Campos de tablas
    public static final String ID = "id";
    public static final String NOMBRE = "nombre";
    public static final String ESTATUS = "estatus";
    //Paciente
    public static final String PRIMER_APELLIDO = "primer_apellido";
    public static final String SEGUNDO_APELLIDO = "segundo_apellido";
    public static final String SEXO = "sexo";

    //muestra
    public static final String PACIENTE_ID = "paciente_id";
    public static final String CANTIDAD_INPUT = "cantidad_input";
    public static final String FECHA = "fecha";
    public static final String TOTAL_WBC_SNRBC = "total_wbc_snrbc";
    public static final String TOTAL_WBC_CNRBC = "total_wbc_cnrbc";

    //celula
    public static final String NOMBRE_ABREV = "nombre_abre";
    public static final String DESCRIPCION = "descipcion";

    //muestra
    public static final String MUESTRA_ID = "muestra_id";
    public static final String CELULA_ID = "celula_id";
    public static final String CANTIDAD = "cantidad";

    //Generales
    public static final String CREATE = "CREATE";
    public static final String TABLE = "TABLE";
    public static final String DROP = "DROP";
    public static final String IF = "IF";
    public static final String EXISTS = "EXISTS";
    public static final String SELECT = "SELECT";
    public static final String ASTERISCO = "*";
    public static final String PUNTO = ".";
    public static final String ESPACIO = " ";
    public static final String FROM = "FROM";
    public static final String WHERE = "WHERE";
    public static final String IGUAL = "=";
    public static final String COMA = ",";
    public static final String SIGNO_INTERROGACION = "?";
    public static final String AND = "AND";
    public static final String DISTINCT = "DISTINCT";
    public static final String IN = "IN";
    public static final String PARENTESIS_ABRIR = "(";
    public static final String PARENTESIS_CERRAR = ")";
    public static final String UNO_IGUAL_UNO = "1 = 1";
    public static final String TO_CHAR = "TO_CHAR";
    public static final String DATE_FORMAT = "YYYY-MM-DD";
    public static final String AS = "AS";
    public static final String COMILLA_SIMPLE = "'";
    public static final String PRIMARY_KEY = "PRIMARY KEY";
    public static final String AUTOINCREMENT = "AUTOINCREMENT";
    public static final String NOT_NULL = "NOT NULL";


    //tipos de datos
    public static final String INTEGER = "INTEGER";
    public static final String TEXT = "TEXT";
    public static final String VARCHAR = "VARCHAR";




}

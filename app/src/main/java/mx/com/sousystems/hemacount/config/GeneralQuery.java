package mx.com.sousystems.hemacount.config;

import mx.com.sousystems.hemacount.util.ConstanteTabla;

public class GeneralQuery {
    private static final String LONGITUD_45 = "45";
    private static final String LONGITUD_20 = "20";

    private GeneralQuery() {
        throw new IllegalStateException("GeneralQuery class");
    }
    public static final String CREAR_TABLA_PACIENTE = ConstanteTabla.CREATE+ConstanteTabla.ESPACIO+ConstanteTabla.TABLE+ConstanteTabla.ESPACIO+ConstanteTabla.PACIENTE
            +ConstanteTabla.ESPACIO+ConstanteTabla.PARENTESIS_ABRIR+ConstanteTabla.ID+ConstanteTabla.ESPACIO+ConstanteTabla.INTEGER+ConstanteTabla.ESPACIO+ConstanteTabla.PRIMARY_KEY+ConstanteTabla.ESPACIO+ConstanteTabla.AUTOINCREMENT+ConstanteTabla.ESPACIO+ConstanteTabla.NOT_NULL+ConstanteTabla.COMA
            +ConstanteTabla.ESPACIO+ConstanteTabla.NOMBRE+ConstanteTabla.ESPACIO+ConstanteTabla.VARCHAR+ConstanteTabla.PARENTESIS_ABRIR+LONGITUD_45+ConstanteTabla.PARENTESIS_CERRAR+ConstanteTabla.COMA
            +ConstanteTabla.ESPACIO+ConstanteTabla.PRIMER_APELLIDO+ConstanteTabla.ESPACIO+ConstanteTabla.VARCHAR+ConstanteTabla.PARENTESIS_ABRIR+LONGITUD_45+ConstanteTabla.PARENTESIS_CERRAR+ConstanteTabla.COMA
            +ConstanteTabla.ESPACIO+ConstanteTabla.SEGUNDO_APELLIDO+ConstanteTabla.ESPACIO+ConstanteTabla.VARCHAR+ConstanteTabla.PARENTESIS_ABRIR+LONGITUD_45+ConstanteTabla.PARENTESIS_CERRAR+ConstanteTabla.COMA
            +ConstanteTabla.ESPACIO+ConstanteTabla.SEXO+ConstanteTabla.ESPACIO+ConstanteTabla.VARCHAR+ConstanteTabla.PARENTESIS_ABRIR+LONGITUD_20+ConstanteTabla.PARENTESIS_CERRAR+ConstanteTabla.COMA
            +ConstanteTabla.ESPACIO+ConstanteTabla.TELEFONO+ConstanteTabla.ESPACIO+ConstanteTabla.VARCHAR+ConstanteTabla.PARENTESIS_ABRIR+LONGITUD_20+ConstanteTabla.PARENTESIS_CERRAR+ConstanteTabla.COMA
            +ConstanteTabla.ESPACIO+ConstanteTabla.ESTATUS+ConstanteTabla.ESPACIO+ConstanteTabla.INTEGER+ConstanteTabla.PARENTESIS_CERRAR;

    public static final String CREAR_TABLA_MUESTRA = ConstanteTabla.CREATE+ConstanteTabla.ESPACIO+ConstanteTabla.TABLE+ConstanteTabla.ESPACIO+ConstanteTabla.MUESTRA
            +ConstanteTabla.ESPACIO+ConstanteTabla.PARENTESIS_ABRIR+ConstanteTabla.ID+ConstanteTabla.ESPACIO+ConstanteTabla.INTEGER+ConstanteTabla.ESPACIO+ConstanteTabla.PRIMARY_KEY+ConstanteTabla.ESPACIO+ConstanteTabla.AUTOINCREMENT+ConstanteTabla.ESPACIO+ConstanteTabla.NOT_NULL+ConstanteTabla.COMA
            +ConstanteTabla.ESPACIO+ConstanteTabla.PACIENTE_ID+ConstanteTabla.ESPACIO+ConstanteTabla.INTEGER+ConstanteTabla.COMA
            +ConstanteTabla.ESPACIO+ConstanteTabla.CANTIDAD_INPUT+ConstanteTabla.ESPACIO+ConstanteTabla.REAL+ConstanteTabla.COMA
            +ConstanteTabla.ESPACIO+ConstanteTabla.CANTIDAD_TOTAL_CELULA+ConstanteTabla.ESPACIO+ConstanteTabla.INTEGER+ConstanteTabla.COMA
            +ConstanteTabla.ESPACIO+ConstanteTabla.FECHA+ConstanteTabla.ESPACIO+ConstanteTabla.TEXT+ConstanteTabla.COMA
            +ConstanteTabla.ESPACIO+ConstanteTabla.TOTAL_WBC_SNRBC+ConstanteTabla.ESPACIO+ConstanteTabla.REAL+ConstanteTabla.COMA
            +ConstanteTabla.ESPACIO+ConstanteTabla.TOTAL_WBC_CNRBC+ConstanteTabla.ESPACIO+ConstanteTabla.REAL+ConstanteTabla.COMA
            +ConstanteTabla.ESPACIO+ConstanteTabla.ESTATUS+ConstanteTabla.ESPACIO+ConstanteTabla.INTEGER+ConstanteTabla.PARENTESIS_CERRAR;

    public static final String CREAR_TABLA_CELULA = ConstanteTabla.CREATE+ConstanteTabla.ESPACIO+ConstanteTabla.TABLE+ConstanteTabla.ESPACIO+ConstanteTabla.CELULA
            +ConstanteTabla.ESPACIO+ConstanteTabla.PARENTESIS_ABRIR+ConstanteTabla.ID+ConstanteTabla.ESPACIO+ConstanteTabla.VARCHAR+ConstanteTabla.PARENTESIS_ABRIR+LONGITUD_20+ConstanteTabla.PARENTESIS_CERRAR+ConstanteTabla.ESPACIO+ConstanteTabla.PRIMARY_KEY+ConstanteTabla.COMA
            +ConstanteTabla.ESPACIO+ConstanteTabla.NOMBRE+ConstanteTabla.ESPACIO+ConstanteTabla.VARCHAR+ConstanteTabla.PARENTESIS_ABRIR+LONGITUD_45+ConstanteTabla.PARENTESIS_CERRAR+ConstanteTabla.COMA
            +ConstanteTabla.ESPACIO+ConstanteTabla.NOMBRE_ABREV+ConstanteTabla.ESPACIO+ConstanteTabla.VARCHAR+ConstanteTabla.PARENTESIS_ABRIR+LONGITUD_20+ConstanteTabla.PARENTESIS_CERRAR+ConstanteTabla.COMA
            +ConstanteTabla.ESPACIO+ConstanteTabla.DESCRIPCION+ConstanteTabla.ESPACIO+ConstanteTabla.TEXT+ConstanteTabla.COMA
            +ConstanteTabla.ESPACIO+ConstanteTabla.ESTATUS+ConstanteTabla.ESPACIO+ConstanteTabla.INTEGER+ConstanteTabla.PARENTESIS_CERRAR;

    public static final String CREAR_TABLA_MUESTRA_DETALLE = ConstanteTabla.CREATE+ConstanteTabla.ESPACIO+ConstanteTabla.TABLE+ConstanteTabla.ESPACIO+ConstanteTabla.MUESTRA_DETALLE
            +ConstanteTabla.ESPACIO+ConstanteTabla.PARENTESIS_ABRIR+ConstanteTabla.ID+ConstanteTabla.ESPACIO+ConstanteTabla.INTEGER+ConstanteTabla.ESPACIO+ConstanteTabla.PRIMARY_KEY+ConstanteTabla.ESPACIO+ConstanteTabla.AUTOINCREMENT+ConstanteTabla.ESPACIO+ConstanteTabla.NOT_NULL+ConstanteTabla.COMA
            +ConstanteTabla.ESPACIO+ConstanteTabla.MUESTRA_ID+ConstanteTabla.ESPACIO+ConstanteTabla.INTEGER+ConstanteTabla.COMA
            +ConstanteTabla.ESPACIO+ConstanteTabla.CELULA_ID+ConstanteTabla.ESPACIO+ConstanteTabla.VARCHAR+ConstanteTabla.PARENTESIS_ABRIR+LONGITUD_20+ConstanteTabla.PARENTESIS_CERRAR+ConstanteTabla.COMA
            +ConstanteTabla.ESPACIO+ConstanteTabla.CANTIDAD+ConstanteTabla.ESPACIO+ConstanteTabla.INTEGER+ConstanteTabla.COMA
            +ConstanteTabla.ESPACIO+ConstanteTabla.ESTATUS+ConstanteTabla.ESPACIO+ConstanteTabla.INTEGER+ConstanteTabla.PARENTESIS_CERRAR;

    public static final String ELIMINAR_TABLA_PACIENTE = ConstanteTabla.DROP+ConstanteTabla.ESPACIO+ConstanteTabla.TABLE+ConstanteTabla.ESPACIO+ConstanteTabla.IF+ConstanteTabla.ESPACIO+ConstanteTabla.EXISTS+ConstanteTabla.ESPACIO+ConstanteTabla.PACIENTE;
    public static final String ELIMINAR_TABLA_MUESTRA = ConstanteTabla.DROP+ConstanteTabla.ESPACIO+ConstanteTabla.TABLE+ConstanteTabla.ESPACIO+ConstanteTabla.IF+ConstanteTabla.ESPACIO+ConstanteTabla.EXISTS+ConstanteTabla.ESPACIO+ConstanteTabla.MUESTRA;
    public static final String ELIMINAR_TABLA_CELULA = ConstanteTabla.DROP+ConstanteTabla.ESPACIO+ConstanteTabla.TABLE+ConstanteTabla.ESPACIO+ConstanteTabla.IF+ConstanteTabla.ESPACIO+ConstanteTabla.EXISTS+ConstanteTabla.ESPACIO+ConstanteTabla.CELULA;
    public static final String ELIMINAR_TABLA_MUESTRA_DETALLE = ConstanteTabla.DROP+ConstanteTabla.ESPACIO+ConstanteTabla.TABLE+ConstanteTabla.ESPACIO+ConstanteTabla.IF+ConstanteTabla.ESPACIO+ConstanteTabla.EXISTS+ConstanteTabla.ESPACIO+ConstanteTabla.MUESTRA_DETALLE;

}

package mx.com.sousystems.wbcscounter.util;

import android.content.Context;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class Util {

    private static final String DATE_FORMAT_PATTERN = "YYYY-MM-dd";
    private static final Integer PORCENTAJE = 100;

    private Util() {
        throw new IllegalStateException("Util class");
    }

    /**
     * Obtiene la fecha actual del dispositivo movil
     *
     * @return la fecha actual
     */
    public static String fecha() {
        String fecha = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate date = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN);
            fecha = formatter.format(date);
        } else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN);
            Calendar calendar = Calendar.getInstance();
            fecha = simpleDateFormat.format(calendar.getTime());
        }
        return fecha;
    }

    public static double numeroDosDecimales(double numero) {
        BigDecimal bd = new BigDecimal(Double.toString(numero));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static double calcularPorcentaje(Integer cantidadtTotalCelula, Integer cantidadCelula) {
        double porcentaje = ((cantidadCelula * PORCENTAJE) / cantidadtTotalCelula);
        return numeroDosDecimales(porcentaje);
    }

    public static double calcularUnidadMedida(double cantidadTotalWBC, double porcentaje) {
        double medida = (cantidadTotalWBC * porcentaje) / PORCENTAJE;
        return numeroDosDecimales(medida);
    }

    public static String getStringFromResourcesByName(Context context, String resourceName) {
        /*
            getPackageName()
                Return the name of this application's package.
        */
        // Get the application package name
        String packageName = context.getPackageName();

        /*
            getResources()
                Return a Resources instance for your application's package.
        */
        /*
            public int getIdentifier (String name, String defType, String defPackage)
                Return a resource identifier for the given resource name. A fully qualified resource
                name is of the form "package:type/entry". The first two components (package and type)
                are optional if defType and defPackage, respectively, are specified here.

                Note: use of this function is discouraged. It is much more efficient to retrieve
                resources by identifier than by name.

            Parameters
                name String: The name of the desired resource.

                defType String: Optional default resource type to find, if "type/" is
                    not included in the name. Can be null to require an explicit type.

                defPackage String: Optional default package to find, if "package:" is not
                    included in the name. Can be null to require an explicit package.
            Returns
                int : int The associated resource identifier. Returns 0 if no such resource
                    was found. (0 is not a valid resource ID.)
        */
        // Get the string resource id by name
        int resourceId = context.getResources().getIdentifier(resourceName.toLowerCase(), "string", packageName);

        // Return the string value
        return context.getString(resourceId);
    }
}

package mx.com.sousystems.wbcscounter.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class Util {

    private static final String DATE_FORMAT_PATTERN = "YYYY-MM-dd";

    private Util() {
        throw new IllegalStateException("Util class");
    }

    /**
     * Obtiene la fecha actual del dispositivo movil
     * @return la fecha actual
     */
    public static String fecha(){
        String fecha = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate date = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN);
            fecha = formatter.format(date);
        }else{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN);
            Calendar calendar = Calendar.getInstance();
            fecha = simpleDateFormat.format(calendar.getTime());
        }
        return fecha;
    }
}

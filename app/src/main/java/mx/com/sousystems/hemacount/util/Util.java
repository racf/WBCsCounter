package mx.com.sousystems.hemacount.util;


import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mx.com.sousystems.hemacount.R;
import mx.com.sousystems.hemacount.domain.Muestra;
import mx.com.sousystems.hemacount.domain.MuestraDetalle;
import mx.com.sousystems.hemacount.dto.ReporteDTO;
import mx.com.sousystems.hemacount.dto.ReporteEtiquetasDTO;

public class Util {

    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";
    private static final String DATE_FORMAT_PATTERN2 = "yyyy-MM-dd-HH-mm-ss";
    private static final String DATE_FORMAT_PATTERN3 = "yyyy-MM-dd HH:mm:ss";
    private static final Integer PORCENTAJE = 100;
    private static final String WBCS = "wbcs";

    private Util() {
        throw new IllegalStateException("Util class");
    }

    /**
     * Obtiene la fecha actual del dispositivo movil
     *
     * @return la fecha actual
     */

    public static void writeSharedPreference(Context context, int resourceId, String newValue){
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(resourceId), newValue);
        editor.apply();
    }

    public static String readSharedPreference(Context context, int resourceId){
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return sharedPref.getString(context.getString(resourceId), "");
    }

    public static void playSound(double frequency, int duration) {
         // AudioTrack definition
        int mBufferSize = AudioTrack.getMinBufferSize(44100,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_8BIT);

        AudioTrack mAudioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 44100,
                AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT,
                mBufferSize, AudioTrack.MODE_STREAM);

        // Sine wave
        double[] mSound = new double[4410];
        short[] mBuffer = new short[duration];
        for (int i = 0; i < mSound.length; i++) {
            mSound[i] = Math.sin((0.5*Math.PI * i/(44100/frequency)));
            mBuffer[i] = (short) (mSound[i]*Short.MAX_VALUE);
        }

        mAudioTrack.setStereoVolume(AudioTrack.getMaxVolume(), AudioTrack.getMaxVolume());
        mAudioTrack.play();

        mAudioTrack.write(mBuffer, 0, mSound.length);
        mAudioTrack.stop();
        mAudioTrack.release();

    }

    public static String fecha() {
        String fecha;
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

    public static String fechaActual(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN2);
        Calendar calendar = Calendar.getInstance();
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String fechaExportacion(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN3);
        Calendar calendar = Calendar.getInstance();
        return simpleDateFormat.format(calendar.getTime());
    }

    public static boolean compareDate(String fechaIni, String fechaFin){
        boolean band;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate localDateIni = LocalDate.parse(fechaIni);
            LocalDate localDateFinal = LocalDate.parse(fechaFin);
            if(localDateIni.equals(localDateFinal)){
                band = true;
            }else if(localDateFinal.isAfter(localDateIni)){
                band = true;
            }else{
                band = false;
            }
        }else{
           band = validateFecha(fechaIni, fechaFin);
        }
        return band;
    }

    private static boolean validateFecha(String fechaIni, String fechaFin){
        boolean band;
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_PATTERN);
        try {
            Date fechaInicial = format.parse(fechaIni);
            Date fechaFinal = format.parse(fechaFin);
            if(fechaInicial.equals(fechaFinal)){
                band = true;
            }else if(fechaFinal.after(fechaInicial)){
                band = true;
            }else{
                band = false;
            }
        } catch (ParseException e) {
            band = false;
            Log.e("ERRO: ", e.getMessage());
        }

        return band;
    }

    public static double numeroDosDecimales(double numero) {
        BigDecimal bd = new BigDecimal(Double.toString(numero));
        bd = bd.setScale(1, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


    public static double calcularPorcentaje(Integer cantidadTotalCelula, Integer cantidadCelula) {
        return ((double)(cantidadCelula * PORCENTAJE) / cantidadTotalCelula);
    }

    public static double calcularUnidadMedida(double cantidadTotalWBC, double porcentaje) {
        double medida = (cantidadTotalWBC * porcentaje) / PORCENTAJE;
        return numeroDosDecimales(medida);
    }

    public static int getDrawableResourceIDFromResourcesByName(Context context, String resourceName) {
        String packageName = context.getPackageName();
        int resourceId = context.getResources().getIdentifier(resourceName.toLowerCase(), "drawable", packageName);
        return resourceId;
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

    //FUNCIONES PARA LA GESTIÓN DE ARCHIVOS...

    /**
     * Checks if external storage is available for read and write
     * @return un valor verdadero o falso.
     */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
    /**
     * Checks if external storage is available to at least read
     * @return un valor verdadero o falso.
     */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }


    /**
     * @return verdadero si el directorio existe.
     */
    public static boolean directoryExists(){
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+WBCS;
        File dir = new File(path);
        if(dir.exists()){
            return true;
        }else{
            return false;
        }
    }

    public static boolean generarDirectorio(){
        boolean band;
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+WBCS;
        File folder = new File(path);
        if(!folder.exists()){
            if(!folder.mkdir()){
                band = false;
            }else{
                band = true;
            }
        }else{
            band = true;
        }
        return band;
    }

    private static ReporteEtiquetasDTO cargarEtiquetasReporte(Context context, int cantidad){
        ReporteEtiquetasDTO reporteEtiquetasDTO = new ReporteEtiquetasDTO();
        reporteEtiquetasDTO.setEtiquetaNombre(context.getString(R.string.etiqueta_nombre));
        reporteEtiquetasDTO.setEtiquetaSexo(context.getString(R.string.sexo).concat(":"));
        reporteEtiquetasDTO.setEtiquetaFecha(context.getString(R.string.etiqueta_fecha));
        reporteEtiquetasDTO.setEtiquetaTelefono(context.getString(R.string.etiqueta_telefono));
        reporteEtiquetasDTO.setEtiquetaCantidadTotalWbc(context.getString(R.string.etiqueta_cantidad_total_wbc));
        reporteEtiquetasDTO.setEtiquetaTotalWbc(context.getString(R.string.etiqueta_total_wbc));
        reporteEtiquetasDTO.setEtiquetaTotalConNrbc(context.getString(R.string.etiqueta_total_con_nrbc));
        reporteEtiquetasDTO.setEtiquetaTotalSinNrbc(context.getString(R.string.etiqueta_total_sin_nrbc));
        reporteEtiquetasDTO.setEtiquetaFechaGeneracion(context.getString(R.string.etiqueta_fecha_generacion));
        reporteEtiquetasDTO.setHeaderTipoWbc(context.getString(R.string.tabla_tipo));
        reporteEtiquetasDTO.setHeaderNombreWbc(context.getString(R.string.tabla_nombre));
        reporteEtiquetasDTO.setHeaderCantidad(context.getString(R.string.tabla_cantidad)+" "+cantidad);
        reporteEtiquetasDTO.setHeaderPorcentaje(context.getString(R.string.tabla_porcentaje));
        reporteEtiquetasDTO.setHeaderMedida(context.getString(R.string.tabla_medida));
        return reporteEtiquetasDTO;
    }

    public static ReporteDTO cargarReporteActual(Context context, ReporteDTO reporteDTO, int cantidad){
        reporteDTO.setReporteEtiquetasDTO(cargarEtiquetasReporte(context, cantidad));
        return reporteDTO;
    }

    public static ReporteDTO cargarReporteHistorico(Context context, Muestra muestra, List<MuestraDetalle> listaMuestraDetalle){
        ReporteDTO reporteDTO = new ReporteDTO();
        //Cargar encabezado del reporte
        reporteDTO.setFecha(muestra.getFecha());
        reporteDTO.setCantidadTotalWbc(muestra.getCantidadInput());
        reporteDTO.setCantidadTotalCelula(muestra.getCantidadTotalCelula());
        reporteDTO.setTotalConNrbc(muestra.getTotalWbcCnrbc());
        reporteDTO.setTotalSinNrbc(muestra.getTotalWbcSnrbc());
        reporteDTO.setNombre(muestra.getPaciente().getNombre()+" "+muestra.getPaciente().getPrimerApellido()+" "+muestra.getPaciente().getSegundoApellido());
        reporteDTO.setTelefono(muestra.getPaciente().getTelefono());
        reporteDTO.setSexo(muestra.getPaciente().getSexo());
        reporteDTO.setFechaGeneracion(fechaExportacion());
        reporteDTO.setReporteEtiquetasDTO(cargarEtiquetasReporte(context, reporteDTO.getCantidadTotalCelula()));

        reporteDTO.setListaMuestraDetalle(listaMuestraDetalle);
        return reporteDTO;
    }
}

package mx.com.sousystems.hemacount.service;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import mx.com.sousystems.hemacount.R;
import mx.com.sousystems.hemacount.dto.ReporteDTO;
import mx.com.sousystems.hemacount.util.Constante;
import mx.com.sousystems.hemacount.util.Exportacion;
import mx.com.sousystems.hemacount.util.Util;

public class ExportarServiceImpl implements ExportarService {

    /**
     * @param context    el contexto donde se ejecutará el método
     * @param reporteDTO el objeto con la información para el reporte
     * @param opcion     Si la opcion se obtiene de la vista calculo opcion es 0, si la opcion se obtiene desde el historial la opcion es 1
     * @return el objeto del reporte generado.
     */
    @Override
    public Workbook reporteExcel(Context context, ReporteDTO reporteDTO, int opcion) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + Constante.HEMACOUNT + "/";
        String fileName = reporteDTO.getNombre().concat("-").concat(Util.fechaActual()).concat(Constante.XLS);
        String titulo = context.getString(R.string.titulo_reporte);

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(Constante.HEMACOUNT);
        Exportacion.crearTitulo(wb, sheet, titulo);
        Exportacion.crearDescripcion(wb, sheet, reporteDTO);
        List<String> encabezado = Exportacion.crearEncabezado(sheet, reporteDTO);
        Exportacion.crearCuerpoReporte(context, sheet, reporteDTO, encabezado, opcion);
        Exportacion.footerTabla(context, sheet, reporteDTO);
        //Genera el archivo
        File file = new File(path, fileName);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            wb.write(fileOutputStream);
            generateNotification(context, Constante.MIME_TYPE_XLS, file);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (wb != null) {
                    wb.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return wb;
    }

    /**
     * @param context    el contexto donde se ejecutará el método
     * @param reporteDTO el objeto con la información para el reporte
     * @param opcion     Si la opcion se obtiene de la vista calculo opcion es 0, si la opcion se obtiene desde el historial la opcion es 1
     * @return el objeto del reporte generado.
     */
    @Override
    public Document reportePDF(Context context, ReporteDTO reporteDTO, int opcion) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + Constante.HEMACOUNT + "/";
        String fileName = reporteDTO.getNombre().concat("-").concat(Util.fechaActual()).concat(Constante.PDF);
        //String file = path + fileName;
        Document document = new Document();
        String titulo = context.getString(R.string.titulo_reporte);
        File file = new File(path, fileName);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            PdfWriter.getInstance(document, fileOutputStream);
            document.open();
            Exportacion.crearTituloPDF(document, titulo);
            Exportacion.crearTablaDescripcionPDF(document, reporteDTO);
            Exportacion.crearTablaPDF(context, document, reporteDTO, opcion);
            Exportacion.footerTablaPDF(context, document, reporteDTO);

            generateNotification(context, Constante.MIME_TYPE_PDF, file);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (document != null) {
                document.close();
            }
        }

        return document;
    }

    private void generateNotification(Context context, String mimeType, File fromFile) {
        Uri path;
        if (Build.VERSION.SDK_INT >=  Build.VERSION_CODES.N) {
            path = Uri.parse(fromFile.getPath());
        } else{
            path = Uri.fromFile(fromFile);
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //FLAG_ACTIVITY_CLEAR_TOP
        intent.setDataAndType(path, mimeType);
        if (Build.VERSION.SDK_INT >=  Build.VERSION_CODES.N) {
            intent.setDataAndType(path, mimeType);
        } else{
            intent.setDataAndType(path, mimeType);
        }
        Intent chooser = Intent.createChooser(intent, context.getResources().getString(R.string.open_with));
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, chooser, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, Constante.NOTIFICATION_CHANNEL_ID)
                .setContentTitle(context.getResources().getString(R.string.creacion_archivo))
                .setContentText(fromFile.getName())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setColor(context.getResources().getColor(R.color.colorPrimary))
                .setAutoCancel(true);
        switch (mimeType) {
            case Constante.MIME_TYPE_PDF:
                builder.setSmallIcon(R.drawable.ic_pdf);
                break;
            case Constante.MIME_TYPE_XLS:
                builder.setSmallIcon(R.drawable.ic_xls);
                break;
        }
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify((int) (System.currentTimeMillis() % Integer.MAX_VALUE), builder.build());

    }
}

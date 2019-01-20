package mx.com.sousystems.wbcscounter.service;

import android.content.Context;
import android.os.Environment;

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

import mx.com.sousystems.wbcscounter.R;
import mx.com.sousystems.wbcscounter.dto.ReporteDTO;
import mx.com.sousystems.wbcscounter.util.Exportacion;
import mx.com.sousystems.wbcscounter.util.Util;

public class ExportarServiceImpl implements ExportarService {
    private static final String WBCS = "wbcs";
    private static final String XLS = ".xls";
    private static final String PDF = ".pdf";

    /**
     * @param context el contexto donde se ejecutará el método
     * @param reporteDTO el objeto con la información para el reporte
     * @param opcion Si la opcion se obtiene de la vista calculo opcion es 0, si la opcion se obtiene desde el historial la opcion es 1
     * @return el objeto del reporte generado.
     */
    @Override
    public Workbook reporteExcel(Context context, ReporteDTO reporteDTO, int opcion) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+WBCS+"/";
        String fileName = reporteDTO.getNombre().concat("-").concat(Util.fechaActual()).concat(XLS);

        String titulo = context.getString(R.string.titulo_reporte);

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(WBCS);
        Exportacion.crearTitulo(wb, sheet, titulo);
        Exportacion.crearDescripcion(wb, sheet, reporteDTO);
        List<String> encabezado = Exportacion.crearEncabezado(sheet, reporteDTO);
        Exportacion.crearCuerpoReporte(context,sheet,reporteDTO, encabezado, opcion);
        Exportacion.footerTabla(context, sheet, reporteDTO);
        //Genera el archivo
        File file = new File(path,fileName);
        try {
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        wb.write(fileOutputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(wb != null){
                    wb.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return wb;
    }

    /**
     * @param context el contexto donde se ejecutará el método
     * @param reporteDTO el objeto con la información para el reporte
     * @param opcion Si la opcion se obtiene de la vista calculo opcion es 0, si la opcion se obtiene desde el historial la opcion es 1
     * @return el objeto del reporte generado.
     */
    @Override
    public Document reportePDF(Context context, ReporteDTO reporteDTO, int opcion) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+WBCS+"/";
        String fileName = reporteDTO.getNombre().concat("-").concat(Util.fechaActual()).concat(PDF);
        String file = path+fileName;
        Document document = new Document();
        String titulo = context.getString(R.string.titulo_reporte);
        try {
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            Exportacion.crearTituloPDF(document, titulo);
            Exportacion.crearTablaDescripcionPDF(document, reporteDTO);
            Exportacion.crearTablaPDF(context, document, reporteDTO, opcion);
            Exportacion.footerTablaPDF(context, document, reporteDTO);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if(document != null){
                document.close();
            }
        }
        return document;
    }
}

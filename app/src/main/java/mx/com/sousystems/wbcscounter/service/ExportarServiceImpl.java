package mx.com.sousystems.wbcscounter.service;

import android.content.Context;
import android.os.Environment;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;

import mx.com.sousystems.wbcscounter.domain.ReporteDTO;
import mx.com.sousystems.wbcscounter.domain.ReporteEtiquetasDTO;
import mx.com.sousystems.wbcscounter.util.Exportacion;
import mx.com.sousystems.wbcscounter.util.Util;

public class ExportarServiceImpl implements ExportarService {
    private static final String WBCS = "wbcs";

    @Override
    public Workbook reporteExcel(ReporteDTO reporteDTO, Context context) {
       /* HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        ReporteEtiquetasDTO reporteEtiquetasDTO = reporteDTO.getReporteEtiquetasDTO();
        String titulo = "Titulo del reporte";
        String subTitutlo = "Subtitulo";
        HSSFSheet sheet = hssfWorkbook.createSheet(WBC);
        Exportacion.crearTitulo(hssfWorkbook, sheet, titulo, subTitutlo);

        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/fiware/";
        String fileName = "MyExcel.xls";*/
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+WBCS+"/";
        String fileName = reporteDTO.getNombre().concat(Util.fechaActual()).concat(".xls");

        ReporteEtiquetasDTO reporteEtiquetasDTO = reporteDTO.getReporteEtiquetasDTO();
        String titulo = "Titulo del reporte";

        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet(WBCS);
        Exportacion.crearTitulo(wb, sheet, titulo);

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
        }
        return wb;
    }
}

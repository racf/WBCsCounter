package mx.com.sousystems.wbcscounter.service;

import android.content.Context;
import android.os.Environment;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import mx.com.sousystems.wbcscounter.R;
import mx.com.sousystems.wbcscounter.dto.ReporteDTO;
import mx.com.sousystems.wbcscounter.util.Exportacion;
import mx.com.sousystems.wbcscounter.util.Util;

public class ExportarServiceImpl implements ExportarService {
    private static final String WBCS = "wbcs";

    @Override
    public Workbook reporteExcel(ReporteDTO reporteDTO, Context context) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+WBCS+"/";
        String fileName = reporteDTO.getNombre().concat("-").concat(Util.fechaActual()).concat(".xls");

        String titulo = context.getString(R.string.titulo_reporte);

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(WBCS);
        Exportacion.crearTitulo(wb, sheet, titulo);
        Exportacion.crearDescripcion(wb, sheet, reporteDTO);
        List<String> encabezado = Exportacion.crearEncabezado(sheet, reporteDTO);
        Exportacion.crearCuerpoReporte(context,sheet,reporteDTO, encabezado);
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
        }
        return wb;
    }
}

package mx.com.sousystems.wbcscounter.util;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.HashMap;
import java.util.Map;

import mx.com.sousystems.wbcscounter.domain.ReporteDTO;
import mx.com.sousystems.wbcscounter.domain.ReporteEtiquetasDTO;

public class Exportacion {
    private static final String A = "A";
    private static final String S = "S";

    private Exportacion() {
        throw new IllegalStateException("Exportacion class");
    }

    public static HSSFWorkbook reporteExcel(ReporteDTO reporteDTO){
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        ReporteEtiquetasDTO reporteEtiquetasDTO = reporteDTO.getReporteEtiquetasDTO();

        return hssfWorkbook;
    }

    // Generamos el titulo y subtitulo del reporte
    public static void crearTitulo(Workbook wb, Sheet sheet, String titulo) {
        Row titleRow = sheet.createRow(0);
        titleRow.setHeightInPoints(22);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(titulo);
        sheet.addMergedRegion(CellRangeAddress.valueOf("$" + A + "$1:$" + S + "$1"));
    }
}

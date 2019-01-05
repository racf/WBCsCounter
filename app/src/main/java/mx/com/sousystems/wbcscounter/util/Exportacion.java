package mx.com.sousystems.wbcscounter.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import mx.com.sousystems.wbcscounter.domain.ReporteDTO;
import mx.com.sousystems.wbcscounter.domain.ReporteEtiquetasDTO;

public class Exportacion {

    private Exportacion() {
        throw new IllegalStateException("Exportacion class");
    }

    public static HSSFWorkbook reporteExcel(ReporteDTO reporteDTO){
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        ReporteEtiquetasDTO reporteEtiquetasDTO = reporteDTO.getReporteEtiquetasDTO();

        return hssfWorkbook;
    }
}

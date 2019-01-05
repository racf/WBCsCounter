package mx.com.sousystems.wbcscounter.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import mx.com.sousystems.wbcscounter.domain.ReporteDTO;
import mx.com.sousystems.wbcscounter.util.Exportacion;

public class ExportarServiceImpl implements Exportarservice{
    @Override
    public HSSFWorkbook reporteExcel(ReporteDTO reporteDTO) {
        return Exportacion.reporteExcel(reporteDTO);
    }
}

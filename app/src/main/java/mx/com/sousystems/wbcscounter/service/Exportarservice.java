package mx.com.sousystems.wbcscounter.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import mx.com.sousystems.wbcscounter.domain.ReporteDTO;

public interface Exportarservice {
    public HSSFWorkbook reporteExcel(ReporteDTO reporteDTO);
}

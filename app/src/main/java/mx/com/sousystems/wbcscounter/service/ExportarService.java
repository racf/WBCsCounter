package mx.com.sousystems.wbcscounter.service;

import android.content.Context;
import org.apache.poi.ss.usermodel.Workbook;

import mx.com.sousystems.wbcscounter.dto.ReporteDTO;

public interface ExportarService {
    public Workbook reporteExcel(ReporteDTO reporteDTO, Context context);
}

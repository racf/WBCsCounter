package mx.com.sousystems.wbcscounter.util;

import android.content.Context;
import android.util.Log;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.ArrayList;
import java.util.List;

import mx.com.sousystems.wbcscounter.R;
import mx.com.sousystems.wbcscounter.domain.MuestraDetalle;
import mx.com.sousystems.wbcscounter.dto.ReporteDTO;

public class Exportacion {
    private static final String A = "A";
    private static final String G = "G";

    private Exportacion() {
        throw new IllegalStateException("Exportacion class");
    }

    // Generamos el titulo y subtitulo del reporte
    public static void crearTitulo(HSSFWorkbook wb, HSSFSheet sheet, String titulo) {
        HSSFRow row = sheet.createRow(0);
        row.setHeightInPoints(22);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue(titulo);
        sheet.addMergedRegion(CellRangeAddress.valueOf("$" + A + "$1:$" + G + "$1"));
    }

    public static void crearDescripcion(HSSFWorkbook wb, HSSFSheet sheet, ReporteDTO reporteDTO){
        //Nombre
        HSSFRow row2 = sheet.createRow(2);
        HSSFCell cellEtiquetaNombre = row2.createCell(0);
        cellEtiquetaNombre.setCellValue(reporteDTO.getReporteEtiquetasDTO().getEtiquetaNombre());

        HSSFCell cellNombre = row2.createCell(1);
        cellNombre.setCellValue(reporteDTO.getNombre());

        //Sexo
        HSSFCell cellEtiquetaSexo = row2.createCell(4);
        cellEtiquetaSexo.setCellValue(reporteDTO.getReporteEtiquetasDTO().getEtiquetaSexo());

        HSSFCell cellSexo = row2.createCell(5);
        cellSexo.setCellValue(reporteDTO.getSexo());


        //Telefono
        HSSFRow row3 = sheet.createRow(3);
        HSSFCell cellEtiquetaTelefono = row3.createCell(0);
        cellEtiquetaTelefono.setCellValue(reporteDTO.getReporteEtiquetasDTO().getEtiquetaTelefono());

        HSSFCell cellTelefono = row3.createCell(1);
        cellTelefono.setCellValue(reporteDTO.getTelefono());

        //Fecha
        HSSFCell cellEtiquetaFecha = row3.createCell(4);
        cellEtiquetaFecha.setCellValue(reporteDTO.getReporteEtiquetasDTO().getEtiquetaFecha());

        HSSFCell cellFecha = row3.createCell(5);
        cellFecha.setCellValue(reporteDTO.getFecha());

        //Cantidad total
        HSSFRow row5 = sheet.createRow(5);
        HSSFCell cellEtiquetaCantidadTotal = row5.createCell(0);
        cellEtiquetaCantidadTotal.setCellValue(reporteDTO.getReporteEtiquetasDTO().getEtiquetaCantidadTotalWbc());

        HSSFCell cellCantidadTotal = row5.createCell(1);
        cellCantidadTotal.setCellValue(reporteDTO.getCantidadTotalWbc());
        cellCantidadTotal.setCellType(CellType.NUMERIC);

    }

    public static List<String> crearEncabezado(HSSFSheet sheet, ReporteDTO reporteDTO){
        List<String> encabezado = new ArrayList<>();
        encabezado.add(reporteDTO.getReporteEtiquetasDTO().getHeaderTipoWbc());
        encabezado.add((reporteDTO.getReporteEtiquetasDTO().getHeaderNombreWbc()));
        encabezado.add(reporteDTO.getReporteEtiquetasDTO().getHeaderCantidad());
        encabezado.add(reporteDTO.getReporteEtiquetasDTO().getHeaderPorcentaje());
        encabezado.add(reporteDTO.getReporteEtiquetasDTO().getHeaderMedida());

        HSSFRow rowEncabezado = sheet.createRow(6);
        rowEncabezado.setHeightInPoints(14);
        for (int i = 0; i < encabezado.size(); i++){
            HSSFCell cellEncabezado = rowEncabezado.createCell(i);
            cellEncabezado.setCellValue(encabezado.get(i));
        }

        return encabezado;
    }

    public static void crearCuerpoReporte(Context context, HSSFSheet sheet, ReporteDTO reporteDTO, List<String> encabezado, int opcion){
        Object[][] matrix = addMatrixObjFormato(context, reporteDTO, encabezado, opcion);

        int indiceFila = 7;
        for (int i = 0; i < matrix.length; i++) {
            HSSFRow row = sheet.createRow(indiceFila++);
            for (int j = 0; j < matrix[i].length; j++) {
                HSSFCell cell = row.createCell(j);
                if (matrix[i][j] instanceof String) {
                    cell.setCellValue((String) matrix[i][j]);
                }else if(matrix[i][j] instanceof Integer){
                    cell.setCellValue((Integer) matrix[i][j]);
                    cell.setCellType(CellType.NUMERIC);
                }else{
                    cell.setCellValue((Double) matrix[i][j]);
                    cell.setCellType(CellType.NUMERIC);
                }
            }
        }
    }

    private static Object[][] addMatrixObjFormato(Context context, ReporteDTO reporteDTO, List<String> encabezado, int opcion) {
        List<MuestraDetalle> listaMuestraDetalle = reporteDTO.getListaMuestraDetalle();
        int matrixRow = listaMuestraDetalle.size();
        int matrixCol = encabezado.size();
        Object[][] matrix = new Object[matrixRow][matrixCol];
        for (int i = 0; i < matrixRow; i++) {
            if(opcion == 0){
                matrix[i][0] = listaMuestraDetalle.get(i).getCelula().getNombre();
            }else{
                matrix[i][0] = Util.getStringFromResourcesByName(context, listaMuestraDetalle.get(i).getCelulaId());
            }

            matrix[i][1] = Util.getStringFromResourcesByName(context, listaMuestraDetalle.get(i).getCelulaId()+"_des");
            matrix[i][2] = listaMuestraDetalle.get(i).getCantidad();
            double porcentaje = Util.calcularPorcentaje(reporteDTO.getCantidadTotalCelula(), listaMuestraDetalle.get(i).getCantidad());
            matrix[i][3] = Util.numeroDosDecimales(porcentaje);
            matrix[i][4] = Util.numeroDosDecimales(Util.calcularUnidadMedida(reporteDTO.getCantidadTotalWbc(), porcentaje));
        }

        return matrix;
    }

    public static void footerTabla(Context context, HSSFSheet sheet, ReporteDTO reporteDTO){
        int fila = reporteDTO.getListaMuestraDetalle().size()+8;
        //Encabezado del header footer
        HSSFRow rowHeaderFooter = sheet.createRow(fila);
        HSSFCell cellHeaderConNrbc = rowHeaderFooter.createCell(1);
        cellHeaderConNrbc.setCellValue(context.getString(R.string.tabla_header_con_nrbc));

        HSSFCell cellHeaderSinNrbc = rowHeaderFooter.createCell(2);
        cellHeaderSinNrbc.setCellValue(context.getString(R.string.tabla_header_sin_nrbc));

        //Muestra el resultado
        HSSFRow rowHeaderResultado = sheet.createRow(fila+1);
        HSSFCell cellHeaderTotalWbc = rowHeaderResultado.createCell(0);
        cellHeaderTotalWbc.setCellValue(context.getString(R.string.tabla_total_wbc));

        HSSFCell cellHeaderTotalConNrbc = rowHeaderResultado.createCell(1);
        cellHeaderTotalConNrbc.setCellValue(Util.numeroDosDecimales(reporteDTO.getTotalConNrbc())+""+context.getString(R.string.tabla_medida));

        HSSFCell cellHeaderTotalSinNrbc = rowHeaderResultado.createCell(2);
        cellHeaderTotalSinNrbc.setCellValue(Util.numeroDosDecimales(reporteDTO.getTotalSinNrbc())+""+context.getString(R.string.tabla_medida));

        //Fecha generación del reporte
        //Muestra el resultado
        HSSFRow rowFecha = sheet.createRow(fila+3);
        HSSFCell cellFecha = rowFecha.createCell(0);
        cellFecha.setCellValue(Util.fechaExportacion());

    }
}

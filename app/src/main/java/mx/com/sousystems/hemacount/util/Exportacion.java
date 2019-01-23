package mx.com.sousystems.hemacount.util;

import android.content.Context;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.ArrayList;
import java.util.List;

import mx.com.sousystems.hemacount.R;
import mx.com.sousystems.hemacount.domain.MuestraDetalle;
import mx.com.sousystems.hemacount.dto.ReporteDTO;

public class Exportacion {
    private static final String A = "A";
    private static final String G = "G";

    //Pdf
    private static Font FONT_TITULO = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);

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
        List<String> encabezado = datosEncaezado(reporteDTO);
        HSSFRow rowEncabezado = sheet.createRow(6);
        rowEncabezado.setHeightInPoints(14);
        for (int i = 0; i < encabezado.size(); i++){
            HSSFCell cellEncabezado = rowEncabezado.createCell(i);
            cellEncabezado.setCellValue(encabezado.get(i));
        }

        return encabezado;
    }

    private static List<String> datosEncaezado(ReporteDTO reporteDTO){
        List<String> encabezado = new ArrayList<>();
        encabezado.add(reporteDTO.getReporteEtiquetasDTO().getHeaderTipoWbc());
        encabezado.add((reporteDTO.getReporteEtiquetasDTO().getHeaderNombreWbc()));
        encabezado.add(reporteDTO.getReporteEtiquetasDTO().getHeaderCantidad());
        encabezado.add(reporteDTO.getReporteEtiquetasDTO().getHeaderPorcentaje());
        encabezado.add(reporteDTO.getReporteEtiquetasDTO().getHeaderMedida());

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


    public static void crearTituloPDF(Document document, String titulo){
        Paragraph preface = new Paragraph();
        try {
            // We add one empty line
            addEmptyLine(preface, 1);
            // Lets write a big header
            preface.add(new Paragraph(titulo, FONT_TITULO));
            preface.setAlignment(Element.ALIGN_CENTER);

            addEmptyLine(preface, 1);
            document.add(preface);

        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public static void crearTablaDescripcionPDF(Document document, ReporteDTO reporteDTO){
        PdfPTable tablaDescripcion = new PdfPTable(4);

        PdfPTable tablaCatidatWbc = new PdfPTable(2);
        try {
            tablaDescripcion.setWidths(new int[]{1, 3, 1, 2});
            tablaCatidatWbc.setWidths(new int[]{2, 2});

            tablaDescripcion.addCell(reporteDTO.getReporteEtiquetasDTO().getEtiquetaNombre());
            tablaDescripcion.addCell(reporteDTO.getNombre());

            tablaDescripcion.addCell(reporteDTO.getReporteEtiquetasDTO().getEtiquetaSexo());
            tablaDescripcion.addCell(reporteDTO.getSexo());

            tablaDescripcion.addCell(reporteDTO.getReporteEtiquetasDTO().getEtiquetaTelefono());
            tablaDescripcion.addCell(reporteDTO.getTelefono());

            tablaDescripcion.addCell(reporteDTO.getReporteEtiquetasDTO().getEtiquetaFecha());
            tablaDescripcion.addCell(reporteDTO.getFecha());
            document.add(tablaDescripcion);

            tablaCatidatWbc.setSpacingBefore(10f);
            tablaCatidatWbc.addCell(reporteDTO.getReporteEtiquetasDTO().getEtiquetaCantidadTotalWbc());
            tablaCatidatWbc.addCell(String.valueOf(reporteDTO.getCantidadTotalWbc()));

            document.add(tablaCatidatWbc);

        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }

    public static void crearTablaPDF(Context context, Document document, ReporteDTO reporteDTO, int opcion){
        List<String> encabezado = datosEncaezado(reporteDTO);
        try {
            PdfPTable tabla = new PdfPTable(encabezado.size());
            tabla.setSpacingBefore(12f);
            for(int i = 0; i <encabezado.size(); i++){
                PdfPCell cellHeader = new PdfPCell(new Phrase(encabezado.get(i)));
                cellHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellHeader.setUseBorderPadding(true);
                cellHeader.setBorderWidthBottom(1f);
                tabla.addCell(cellHeader);
            }

            //Cuerpo de la tabla
            Object[][] matrix = addMatrixObjFormato(context, reporteDTO, encabezado, opcion);
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    tabla.addCell(String.valueOf(matrix[i][j]));
                }
            }
            document.add(tabla);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public static void footerTablaPDF(Context context, Document document, ReporteDTO reporteDTO){
        PdfPTable tabla = new PdfPTable(3);
        tabla.setSpacingBefore(15f);
        try {
            PdfPCell cellHeaderBlanco = new PdfPCell(new Phrase(""));
            cellHeaderBlanco.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellHeaderBlanco.setUseBorderPadding(true);
            cellHeaderBlanco.setBorderWidthBottom(1f);
            tabla.addCell(cellHeaderBlanco);

            PdfPCell cellHeaderConNrbc = new PdfPCell(new Phrase(context.getString(R.string.tabla_header_con_nrbc)));
            cellHeaderConNrbc.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellHeaderConNrbc.setUseBorderPadding(true);
            cellHeaderConNrbc.setBorderWidthBottom(1f);
            tabla.addCell(cellHeaderConNrbc);

            PdfPCell cellHeaderSinNrbc = new PdfPCell(new Phrase(context.getString(R.string.tabla_header_sin_nrbc)));
            cellHeaderSinNrbc.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellHeaderSinNrbc.setUseBorderPadding(true);
            cellHeaderSinNrbc.setBorderWidthBottom(1f);
            tabla.addCell(cellHeaderSinNrbc);

            tabla.addCell(context.getString(R.string.tabla_total_wbc));
            tabla.addCell(Util.numeroDosDecimales(reporteDTO.getTotalConNrbc())+""+context.getString(R.string.tabla_medida));
            tabla.addCell(Util.numeroDosDecimales(reporteDTO.getTotalSinNrbc())+""+context.getString(R.string.tabla_medida));

            document.add(tabla);

            Paragraph paragraph = new Paragraph(Util.fechaExportacion());
            paragraph.setAlignment(Element.ALIGN_LEFT);
            paragraph.setSpacingBefore(20f);
            document.add(paragraph);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }


    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}
